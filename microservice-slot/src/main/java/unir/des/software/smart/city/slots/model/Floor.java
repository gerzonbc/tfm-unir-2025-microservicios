package unir.des.software.smart.city.slots.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "floors")
public class Floor {
    @Id
    private String id;

    /** Parent parking reference (from parking micro) */
    @Indexed
    private String parkingId;

    /** e.g., 0 = ground, 1 = first, etc. */
    private Integer number;

    /** Embedded slots */
    @Builder.Default
    private List<Slot> slots = new ArrayList<>();
}
