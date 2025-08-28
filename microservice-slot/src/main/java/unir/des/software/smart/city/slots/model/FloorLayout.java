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
@Document(collection = "floor_layouts")
public class FloorLayout {
    @Id
    private String id;

    @Indexed
    private String floorId;

    @Builder.Default
    private List<FloorLayoutCell> cells = new ArrayList<>();
}
