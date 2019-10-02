package Implement;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class DemoApplicationEvent extends ApplicationEvent {
    private String name;
    private String age;
    private boolean single;

    public DemoApplicationEvent(Object source) {
        super(source);
    }

    public DemoApplicationEvent(Object source, String name, String age, boolean single) {
        super(source);
        this.name = name;
        this.age = age;
        this.single = single;
    }

    public void testDemo(){
        System.out.println("Congratulations, You have catch the application event!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
