package unir.des.software.smart.city.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MicroserviceParkingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceParkingApplication.class, args);
    }

}
