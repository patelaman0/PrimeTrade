package ai.primetrade.PrimeTrade.Controller;

import ai.primetrade.PrimeTrade.Entity.Users;
import ai.primetrade.PrimeTrade.Service.UsersService;
import ai.primetrade.PrimeTrade.Service.FeedbackService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller

@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        Users admin = usersService.getUserByEmail(principal.getName());

        model.addAttribute("adminName", admin.getName());
        model.addAttribute("user", admin);
        model.addAttribute("userList", usersService.getAllUsers());
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());

        return "adminDashboard";
    }

    @PostMapping("/users/{id}")
    public String deleteUser(@PathVariable String id, RedirectAttributes ra) {
        try {
            ObjectId objectId = new ObjectId(id);
            usersService.deleteUserById(objectId);
            ra.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add")
    public String addAdmin(@ModelAttribute Users admin, RedirectAttributes ra) {
        try {
            usersService.addAdmin(admin);
            ra.addFlashAttribute("successMessage", "Admin created successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Failed to create admin: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/profiles/update")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String emailId,
                                @RequestParam(required = false) String password,
                                Principal principal,
                                RedirectAttributes ra) {
        try {
            usersService.updateProfile(
                    principal.getName(),
                    fullName,
                    emailId,
                    password
            );
            ra.addFlashAttribute("successMessage", "Profile updated successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Profile update failed: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/profiles/delete")
    public String deleteProfile(Principal principal) {
        usersService.deleteByEmail(principal.getName());
        return "redirect:/logout";
    }

    @PostMapping("/dashboard/feedback/{id}/delete")
    public String deleteFeedback(@PathVariable ObjectId id, RedirectAttributes ra) {
        try {
            feedbackService.deleteFeedback(id);
            ra.addFlashAttribute("successMessage", "Feedback deleted successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Failed to delete feedback");
        }
        return "redirect:/admin/dashboard";
    }

}
