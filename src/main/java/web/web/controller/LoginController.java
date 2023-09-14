package web.web.controller;

import web.exceptions.InvalidUserCredentialsException;
import web.model.User;
import web.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authService) {
        this.authenticationService = authService;
    }

    @GetMapping
    public String getLoginPage(){
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model){
        User user = null;
        try{
            user = this.authenticationService.login(request.getParameter("username"),request.getParameter("password"));
            request.getSession().setAttribute("user",user);
            return "redirect:/balloons";
        } catch (InvalidUserCredentialsException e) {
            model.addAttribute("hasError",true);
            model.addAttribute("error",e.getMessage());
            return "login";

        }
    }
}