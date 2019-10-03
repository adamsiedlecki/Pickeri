package pl.adamsiedlecki.Pickeri.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:languages/polish.properties", encoding = "Windows-1250")
public class Config {
}
