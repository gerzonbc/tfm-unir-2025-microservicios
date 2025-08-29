package unir.des.software.smart.city.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroserviceSimulatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceSimulatorApplication.class, args);
    }

}
