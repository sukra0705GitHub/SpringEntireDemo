package JavaCodeConfigureSample;

import SpringConfigFile.SpringConfigByJavaCode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Load Spring web application configuration
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(SpringConfigByJavaCode.class);
        annotationConfigApplicationContext.refresh();

        // Create and register the DispatcherServlet
        
    }
}
