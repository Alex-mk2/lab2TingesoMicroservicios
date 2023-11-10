package lab2TingesoM2S.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Rest{
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

