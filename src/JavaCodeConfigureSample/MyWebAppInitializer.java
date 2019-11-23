package JavaCodeConfigureSample;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;//new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {MyWebApplicationInitializer.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/sukraApp/*"};
    }
}
