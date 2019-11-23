package JavaCodeConfigureSample;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class MyWebAppInitializerFromXMLConfigure extends AbstractDispatcherServletInitializer {
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext();
        xmlWebApplicationContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");
        return xmlWebApplicationContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/sukraApp/*"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
