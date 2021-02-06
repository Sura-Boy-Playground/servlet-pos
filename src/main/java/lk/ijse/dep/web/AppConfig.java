package lk.ijse.dep.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Configuration
@Import(HibernateConfig.class)
public class AppConfig {
}
