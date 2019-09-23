package springrestapi.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springrestapi.demo.models.Message;
import springrestapi.demo.models.User;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String getMainPage(ModelMap modelMap){

        return "index";
    }
}
