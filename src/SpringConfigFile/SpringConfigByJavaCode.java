package SpringConfigFile;

import Implement.Student;
import Interface.StudentIF;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"Implement", "Utils"})
public class SpringConfigByJavaCode {
    @Bean
    public StudentIF studentIF(){
        return new Student();
    }
}
