package pl.adamsiedlecki.Pickeri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class PickeriApplication {

    private static final Logger log = LoggerFactory.getLogger(PickeriApplication.class);

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = LocalDate.of(2020, 8, 1);
        if(now.isBefore(expirationDate)){
            SpringApplication.run(PickeriApplication.class, args);
        }else{
            log.info("APP VERSION EXPIRED ;(");
        }
    }

}
