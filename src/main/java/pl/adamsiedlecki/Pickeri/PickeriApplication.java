package pl.adamsiedlecki.Pickeri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.adamsiedlecki.Pickeri.configuration.WebSecurityConfig;
import pl.adamsiedlecki.Pickeri.dao.UserRepository;
import pl.adamsiedlecki.Pickeri.entity.UserRole;
import pl.adamsiedlecki.Pickeri.service.PickeriUserDetailsService;

import java.util.List;

@SpringBootApplication
public class PickeriApplication {

	public static void main(String[] args) {
		SpringApplication.run(PickeriApplication.class, args);

	}

}
