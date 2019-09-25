package Implement;

import Interface.Book;
import Interface.StudentIF;
import org.springframework.stereotype.Component;

@Component
public class Student implements StudentIF {
    private Book mathBook;

    public Student(){}

    public Student(Book mathBook) {
        this.mathBook = mathBook;
    }

    public void setMathBook(Book mathBook) {
        this.mathBook = mathBook;
    }

    @Override
    public void readBook() {
        this.mathBook.read();
    }

    @Override
    public void studyAt(String location) {
        System.out.println("Study at" + location);
    }

    @Override
    public void course(String[] courseNameArray) {
        for (String courseName:courseNameArray) {
            System.out.println("Study " + courseName);
        }
    }

    @Override
    public void eat(String earThing) {

    }

    @Override
    public void drink(String drinkType) {

    }

    @Override
    public void sleep(String sleepStyle, int sleepTime) {

    }

    @Override
    public void goToSchool(String doWhat) {

    }
}