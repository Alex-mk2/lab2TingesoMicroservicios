package lab2TingesoM3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class lab2TingesoM3{

	public static void main(String[] args) {
		SpringApplication.run(lab2TingesoM3.class, args);
	}

}
