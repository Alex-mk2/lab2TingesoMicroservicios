package lab2TingesoM2S;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class lab2TingesoM2Application {

	public static void main(String[] args) {
		SpringApplication.run(lab2TingesoM2Application.class, args);
	}

}
