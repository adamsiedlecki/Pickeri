package pl.adamsiedlecki.Pickeri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.adamsiedlecki.Pickeri.entity.UserRole;
import pl.adamsiedlecki.Pickeri.service.PickeriUserDetailsService;

import java.util.List;

@SpringBootApplication
public class PickeriApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(PickeriApplication.class, args);


        PickeriUserDetailsService userDs = ctx.getBean(PickeriUserDetailsService.class);
        if (userDs.count() == 0) {
            userDs.addUser("user", "pass", List.of(new UserRole("ADMIN")));
        }
    }

}
