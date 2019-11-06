import Implement.DemoApplicationEvent;
import Implement.Student;
import Interface.StudentIF;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateHashModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String args[]){
        System.out.println("Hello!");
        //ApplicationContext applicationContext = ApplicationContextHolder.applicationContext();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        StudentIF studentIF = applicationContext.getBean("student", Student.class);
        //StudentIF studentIF = (StudentIF)Class.forName("Implement.Student").newInstance();
        studentIF.studyAt("上海");
        String[] newArray = {"数学","语文"};
        studentIF.course(newArray);
        studentIF.readBook();
        DemoApplicationEvent demoApplicationEvent = new DemoApplicationEvent("test","sukra","30",false);
        applicationContext.publishEvent(demoApplicationEvent);
        //Process to page 550
    }
}
