package Implement;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class DemoApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof DemoApplicationEvent){
            DemoApplicationEvent demoApplicationEvent = (DemoApplicationEvent)applicationEvent;
            demoApplicationEvent.testDemo();
        }else{
            System.out.println("You have catch else branch event!" + applicationEvent);
        }
    }
}
