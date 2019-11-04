package pl.adamsiedlecki.Pickeri.configuration.environment;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:languages/polish.properties", encoding = "Windows-1250")
//@PropertySource(value = "classpath:languages/english.properties", encoding = "Windows-1250", factory = ReloadablePropertySourceFactory.class)
public class Config {

}
