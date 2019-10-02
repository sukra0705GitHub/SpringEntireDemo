package SpringConfigFile;

import Implement.EnglishBook;
import Implement.MathBook;
import Implement.Student;
import Interface.Book;
import Interface.StudentIF;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {"Implement", "Utils"})
public class SpringConfigByJavaCode {
    @Bean
    public StudentIF student(){
        Student student = new Student();
        student.setEnglishBook(englishBook());
        student.setMathBook(mathBook());
        return student;
    }

    @Bean
    public EnglishBook englishBook(){
        return new EnglishBook();
    }

    @Bean
    public Book mathBook(){
        return new MathBook();
    }
}
