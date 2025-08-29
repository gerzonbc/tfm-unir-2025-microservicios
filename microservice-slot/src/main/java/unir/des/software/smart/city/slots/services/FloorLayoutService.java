package unir.des.software.smart.city.slots.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unir.des.software.smart.city.slots.dto.FloorLayoutDTO;
import unir.des.software.smart.city.slots.dto.LayoutCellUpsertDTO;
import unir.des.software.smart.city.slots.dto.LayoutUpsertRequest;
import unir.des.software.smart.city.slots.enums.CellType;
import unir.des.software.smart.city.slots.enums.SlotType;
import unir.des.software.smart.city.slots.exception.BusinessException;
import unir.des.software.smart.city.slots.mapper.FloorLayoutMapper;
import unir.des.software.smart.city.slots.model.Floor;
import unir.des.software.smart.city.slots.model.FloorLayout;
import unir.des.software.smart.city.slots.model.FloorLayoutCell;
import unir.des.software.smart.city.slots.model.Slot;
import unir.des.software.smart.city.slots.repository.FloorLayoutRepository;
import unir.des.software.smart.city.slots.repository.FloorRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FloorLayoutService {

    private final FloorLayoutMapper mapper;
    private final FloorRepository floorRepository;
    private final FloorLayoutRepository layoutRepository;

    @Transactional
    public FloorLayoutDTO upsertLayout(String floorId, LayoutUpsertRequest req) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));
        floor.setSlots(new ArrayList<>()); // reset slots, will be re-added if needed

        // index current slots by code for quick lookup
        Map<String, Slot> byCode = new HashMap<>();
        for (Slot slot : floor.getSlots()) {
            byCode.put(slot.getCode().toUpperCase(Locale.ROOT), slot);
        }

        // build layout cells and add slots if needed
        List<FloorLayoutCell> cells = new ArrayList<>();
        int numberSlots = 0;
        for (LayoutCellUpsertDTO c : req.cells()) {
            CellType cellType = mapper.mapCellType(c.type());
            FloorLayoutCell cell = FloorLayoutCell.builder()
                    .row(c.row())
                    .col(c.col())
                    .type(cellType)
                    .build();

            if (mapper.isParkingCell(cellType)) {
                numberSlots++;
                String code = "" + numberSlots;
                Slot slot = byCode.get(code.toUpperCase(Locale.ROOT));
                if (slot == null) {
                    // create a new slot with inferred type
                    SlotType slotType = mapper.mapSlotType(cellType);
                    slot = Slot.builder()
                            .id(UUID.randomUUID().toString())
                            .code(code)
                            .type(slotType)
                            .occupied(false)
                            .build();
                    floor.getSlots().add(slot);
                    byCode.put(code.toUpperCase(Locale.ROOT), slot);
                }
                cell.setSlotId(slot.getId());
                cell.setSlotCode(slot.getCode());
            }

            cells.add(cell);
        }

        // save floor (new slots)
        floorRepository.save(floor);

        // upsert layout
        FloorLayout layout = layoutRepository.findByFloorId(floorId)
                .orElse(FloorLayout.builder().floorId(floorId).build());
        layout.setCells(cells);
        layoutRepository.save(layout);

        return mapper.toDTO(floor, layout);
    }

    public FloorLayoutDTO getLayout(String floorId) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new NoSuchElementException("Floor not found"));
        FloorLayout layout = layoutRepository.findByFloorId(floorId)
                .orElseThrow(() -> new NoSuchElementException("Layout not found"));
        return mapper.toDTO(floor, layout);
    }

}
