package unir.des.software.smart.city.slots.model;

import lombok.*;
import unir.des.software.smart.city.slots.enums.CellType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorLayoutCell {
    private int row;
    private int col;
    private CellType type;
    /** present only if it's a parking-type cell */
    private String slotId;   // links to Floor.slots[].id
    private String slotCode; // optional, handy for FE labels
}

