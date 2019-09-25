package Implement;

import Interface.Book;

public class MathBook implements Book {
    @Override
    public void read() {
        System.out.println("I love read math book!");
    }
}
