package JavaCodeConfigureSample;

import SpringConfigFile.SpringConfigByJavaCode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Load Spring web application Java Code configuration
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(SpringConfigByJavaCode.class);
        annotationConfigApplicationContext.refresh();

        // Create and register the DispatcherServlet
        DispatcherServlet dispatcherServlet  = new DispatcherServlet((WebApplicationContext) annotationConfigApplicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("sukraApp", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/sukraApp/*");
    }

    /*@Override
    public void onStartup(ServletContext container) {
        // Load Spring web application XML configuration
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

        // Create and register the DispatcherServlet
        DispatcherServlet dispatcherServlet  = new DispatcherServlet(appContext);
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }*/
}
