package Implement;

import Interface.Book;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ComponentScan
@Component
public class EnglishBook implements Book {
    @Override
    public void read() {
        System.out.println("You are reading a english book!");
    }
}
