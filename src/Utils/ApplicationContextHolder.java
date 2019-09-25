package Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private static ApplicationContext applicationContextStatic;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        applicationContextStatic = applicationContext;
    }

    public ApplicationContext getApplicationContext(){
        return this.applicationContext;
    }

    public static ApplicationContext applicationContext(){
        return applicationContextStatic;
    }
}
