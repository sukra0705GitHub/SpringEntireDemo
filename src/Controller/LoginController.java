package Controller;

import Implement.Student;
import Interface.StudentIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
@Controller
@RequestMapping("/")
public class LoginController {
    //@Autowired
    //HttpServletRequest httpServletRequest;

    @GetMapping("/mvindex")
    public ModelAndView index(ModelAndView mv){
        System.out.println("Triggle Index!");
        mv.setViewName("index");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mv.addObject("student", personMap);
        mv.addObject("name", "Sukra");

        return mv;
    }

    @GetMapping("/mv1index")
    public ModelAndView index(){
        System.out.println("Triggle Index!");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mv.addObject("student", personMap);
        mv.addObject("name", "Sukra");

        return mv;
    }

    @GetMapping("/index")
    public String index(ModelMap mm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("Triggle Index!");
        System.out.println("RequestMethod:" + httpServletRequest.getMethod());
        httpServletResponse.setStatus(200);

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        mm.addAttribute("student", personMap);
        mm.addAttribute("name", "Sukra");

        return "index";
    }

    /**
     *  This method transfer the object failed to ftl.  We can't transfer object to front page by new ModelMap directly but can define ModelMap on method parameter.
     * @return
     */
    @GetMapping("/mmindex")
    public String index1(){
        System.out.println("Triggle Index!");

        Map<String, String> personMap = new HashMap<>();
        personMap.put("name", "sukra");
        personMap.put("age", "30");

        ModelMap mm = new ModelMap();
        mm.addAttribute("student", personMap);
        mm.addAttribute("name", "Sukra");

        return "index";
    }

    @GetMapping("/login/{userId}")
    @ResponseBody
    public String login(@PathVariable String userId, @RequestParam("username") String username){
        System.out.println("Triggle Login controller!");
        System.out.println("UserId:" + userId + "_UserName:" + username);
        return "Login";
    }
}
