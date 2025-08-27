package unir.des.software.smart.city.parking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@OpenAPIDefinition(
        info = @Info(
                title = "Smart City Parking API",
                version = "v1.0.0",
                description = "Gestión de aparcamientos en tiempo real"
        )
)
@SpringBootApplication
public class MicroserviceParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceParkingApplication.class, args);
    }

}
