package Implement;

import Interface.Book;
import org.springframework.stereotype.Component;

@Component
public class MathBook implements Book {
    @Override
    public void read() {
        System.out.println("I love read math book!");
    }
}
