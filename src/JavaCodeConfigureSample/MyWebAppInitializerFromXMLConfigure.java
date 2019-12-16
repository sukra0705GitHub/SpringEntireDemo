package JavaCodeConfigureSample;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;

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

    @Override
    protected javax.servlet.Filter[] getServletFilters(){
        return new Filter[] {
                new HiddenHttpMethodFilter(), new CharacterEncodingFilter()
        };
    }
}
