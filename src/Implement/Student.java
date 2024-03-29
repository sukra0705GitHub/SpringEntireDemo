package Implement;

import Interface.Book;
import Interface.StudentIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class Student implements StudentIF {
    private String name;

    private Book mathBook;

    private EnglishBook englishBook;

    public Student(){}

    public Student(Book mathBook) {
        this.mathBook = mathBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Book getMathBook() {
        return mathBook;
    }

    public EnglishBook getEnglishBook() {
        return englishBook;
    }*/

    @Autowired
    public void setMathBook(Book mathBook) {
        this.mathBook = mathBook;
    }

    @Autowired
    public void setEnglishBook(EnglishBook englishBook) {
        this.englishBook = englishBook;
    }

    @Override
    public void readBook() {
        this.mathBook.read();
        this.englishBook.read();
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