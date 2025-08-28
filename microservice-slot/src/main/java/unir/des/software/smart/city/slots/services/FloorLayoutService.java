package unir.des.software.smart.city.slots.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unir.des.software.smart.city.slots.dto.CellDTO;
import unir.des.software.smart.city.slots.dto.FloorLayoutDTO;
import unir.des.software.smart.city.slots.dto.LayoutUpsertRequest;
import unir.des.software.smart.city.slots.enums.CellType;
import unir.des.software.smart.city.slots.enums.SlotType;
import unir.des.software.smart.city.slots.exception.BusinessException;
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

    private final FloorRepository floorRepository;
    private final FloorLayoutRepository layoutRepository;

    @Transactional
    public FloorLayoutDTO upsertLayout(String floorId, LayoutUpsertRequest req) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new BusinessException("Floor not found"));

        // index current slots by code for quick lookup
        Map<String, Slot> byCode = new HashMap<>();
        for (Slot s : floor.getSlots()) {
            byCode.put(s.getCode().toUpperCase(Locale.ROOT), s);
        }

        // build layout cells and add slots if needed
        List<FloorLayoutCell> cells = new ArrayList<>();
        for (var c : req.cells()) {
            CellType cellType = mapCellType(c.type());
            FloorLayoutCell cell = FloorLayoutCell.builder()
                    .row(c.row())
                    .col(c.col())
                    .type(cellType)
                    .build();

            if (isParkingCell(cellType)) {
                String code = "R" + c.row() + "C" + c.col();
                Slot slot = byCode.get(code.toUpperCase(Locale.ROOT));
                if (slot == null) {
                    // create a new slot with inferred type
                    SlotType slotType = mapSlotType(cellType);
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

        return toDTO(floor, layout);
    }

    public FloorLayoutDTO getLayout(String floorId) {
        Floor floor = floorRepository.findById(floorId)
                .orElseThrow(() -> new NoSuchElementException("Floor not found"));
        FloorLayout layout = layoutRepository.findByFloorId(floorId)
                .orElseThrow(() -> new NoSuchElementException("Layout not found"));
        return toDTO(floor, layout);
    }

    private boolean isParkingCell(CellType t) {
        return switch (t) {
            case PARKING_SLOT, DISABLED -> true;
            default -> false;
        };
    }

    private CellType mapCellType(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "parking-slot" -> CellType.PARKING_SLOT;
            case "disabled" -> CellType.DISABLED;
            case "drive-lane" -> CellType.DRIVE_LANE;
            case "entrance-down" -> CellType.ENTRANCE_DOWN;
            case "entrance-left" -> CellType.ENTRANCE_LEFT;
            case "entrance-right" -> CellType.ENTRANCE_RIGHT;
            case "exit-right" -> CellType.EXIT_RIGHT;
            case "exit-left" -> CellType.EXIT_LEFT;
            case "exit-up" -> CellType.EXIT_UP;
            case "empty-space", "" -> CellType.EMPTY_SPACE;
            default -> throw new IllegalArgumentException("Unknown cell type: " + type);
        };
    }

    private SlotType mapSlotType(CellType t) {
        return switch (t) {
            case DISABLED -> SlotType.DISABLED;
            default -> SlotType.NORMAL;
        };
    }

    private FloorLayoutDTO toDTO(Floor floor, FloorLayout layout) {
        // index slots by id for occupancy status
        Map<String, Slot> byId = new HashMap<>();
        for (Slot s : floor.getSlots()) byId.put(s.getId(), s);

        List<CellDTO> cells = layout.getCells().stream().map(c -> {
            Slot s = c.getSlotId() != null ? byId.get(c.getSlotId()) : null;
            Boolean occupied = s != null ? s.isOccupied() : null;
            return new CellDTO(
                    c.getRow(), c.getCol(),
                    c.getType().name().toLowerCase(Locale.ROOT),
                    c.getSlotId(), c.getSlotCode(), occupied
            );
        }).toList();

        return new FloorLayoutDTO(floor.getId(), cells);
    }
}
