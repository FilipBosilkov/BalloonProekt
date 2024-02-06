package web.web.controller;

import web.exceptions.InvalidArgumentsException;
import web.exceptions.PasswordsDoNotMatchException;
import web.model.enumeration.Role;
import web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if (error!=null && !error.isEmpty()){
            model.addAttribute("hasError",true);
            model.addAttribute("error",error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,@RequestParam String password,@RequestParam String repeatPassword,@RequestParam String name,@RequestParam String surname, @RequestParam String dateOfBirth, @RequestParam Role role){
        try {
            this.userService.register(username, password, repeatPassword, name, surname, LocalDate.parse(dateOfBirth), role);
            return "redirect:/login";
        } catch (PasswordsDoNotMatchException | InvalidArgumentsException e) {
            return "redirect:/redirect?error="+e.getMessage();
        }
    }
}
