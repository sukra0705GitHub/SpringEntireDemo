package Controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Component
@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/index")
    public String index(){
        System.out.println("Triggle Index!");
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
