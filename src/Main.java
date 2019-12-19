import Implement.DemoApplicationEvent;
import Implement.Student;
import Interface.StudentIF;
import Utils.ApplicationContextHolder;
import freemarker.cache.TemplateLoader;
import freemarker.template.TemplateHashModel;
import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

public class Main {
    public static void main(String args[]){
        System.out.println("Hello!");
        //ApplicationContext applicationContext = ApplicationContextHolder.applicationContext();
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Utils/applicationContext.xml");
        //ApplicationContext applicationContext = new FileSystemXmlApplicationContext("/web/WEB-INF/applicationContext.xml");
        StudentIF studentIF = applicationContext.getBean("student", Student.class);
        //StudentIF studentIF = (StudentIF)Class.forName("Implement.Student").newInstance();
        studentIF.studyAt("上海");
        String[] newArray = {"数学","语文"};
        studentIF.course(newArray);
        studentIF.readBook();
        DemoApplicationEvent demoApplicationEvent = new DemoApplicationEvent("test","sukra","30",false);
        applicationContext.publishEvent(demoApplicationEvent);
        //Process to page 550
        //RequestContextUtils.findWebApplicationContext(null);
    }
}
