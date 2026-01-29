package ai.primetrade.PrimeTrade.Controller;

import ai.primetrade.PrimeTrade.Entity.Users;
import ai.primetrade.PrimeTrade.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

@RequestMapping
public class PublicController {

    @Autowired UsersService usersService;

    @GetMapping("/signup")
    public String showSignup(Model model){
        model.addAttribute("users",new Users());
        return "signup";
    }
    @GetMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @PostMapping("/signup")
    public String newUser(@ModelAttribute Users users , RedirectAttributes redirectAttributes){
        try{
            usersService.saveUsers(users);
            redirectAttributes.addFlashAttribute("signupMessage", "Successfully Account Created");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("signupError", "Something Went Wrong");
        }
        return "redirect:/signup";
    }
}
