package unir.des.software.smart.city.parking.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "parkings")
@CompoundIndex(name = "type_code_idx", def = "{'type.code': 1}")
public class Parking {

    @Id
    private String id;

    @NotBlank
    private String name;
    @NotBlank
    private String address;

    @NotNull
    private Double lat;
    @NotNull
    private Double lng;

    // GeoJSON: [lng, lat]
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private List<Double> location; // = List.of(lng, lat)

    @NotNull
    private EmbeddedType type; // snapshot embebido (sin @DBRef)

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbeddedType {
        private String id;   // id del master
        private String name; // "MALL"
        private String description; // "Shopping Mall"
    }

}
