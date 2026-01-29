package ai.primetrade.PrimeTrade.Controller;
import ai.primetrade.PrimeTrade.Repository.SharesRepo;
import ai.primetrade.PrimeTrade.Repository.UsersRepo;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import ai.primetrade.PrimeTrade.Entity.Users;
import ai.primetrade.PrimeTrade.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ai.primetrade.PrimeTrade.Entity.Shares;
import java.security.Principal;
import java.util.List;

@Controller

@RequestMapping("/user")
public class UsersController {

    @Autowired UsersService usersService;
    @Autowired SharesRepo sharesRepo;
    @Autowired UsersRepo usersRepo;


    @GetMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Users user = usersRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Shares> allShares = sharesRepo.findAll();

            List<Shares> userShares = user.getSharesList();

            model.addAttribute("user", user);
            model.addAttribute("shares", allShares);
            model.addAttribute("userShares", userShares);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load dashboard: " + e.getMessage());
        }

        return "userDashboard";
    }


    @PostMapping("/profile/update")
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
            ra.addFlashAttribute("profileMessage", "Profile updated successfully");
        } catch (Exception e) {
            ra.addFlashAttribute("profileError", "Profile update failed: " + e.getMessage());
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(Principal principal, RedirectAttributes redirectAttributes) {
        try {
            String userEmail = principal.getName();
            Users user = usersRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getSharesList() != null && !user.getSharesList().isEmpty()) {
                for (Shares share : user.getSharesList()) {
                    sharesRepo.deleteById(share.getId());
                }
            }


            usersService.deleteByEmail(userEmail);

            redirectAttributes.addFlashAttribute("successMessage", "Account and all shares deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete account: " + e.getMessage());
        }

        return "redirect:/logout";
    }


    @PostMapping("/shares/add")
    public String addShare(@RequestParam String shareName,
                           @RequestParam String shareDescription,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {

        try {
            String userEmail = authentication.getName();
            Users user = usersService.getUserByEmail(userEmail);

            Shares share = new Shares();
            share.setShareName(shareName);
            share.setShareDescription(shareDescription);
            share.setUsername(user.getName());
            sharesRepo.save(share);

            user.getSharesList().add(share);
            usersRepo.save(user);

            redirectAttributes.addFlashAttribute("sharesMessage", "Share posted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("sharesError", "Failed to post share: " + e.getMessage());
        }

        return "redirect:/user/dashboard";
    }


    @PostMapping("/shares/delete/{id}")
    public String deleteShare(@PathVariable ObjectId id,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {

        try {
            String userEmail = authentication.getName();
            Users user = usersRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Shares share = sharesRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Share not found"));


            if (!share.getUsername().equals(user.getName())) {
                redirectAttributes.addFlashAttribute("errorMessage", "You can only delete your own shares!");
                return "redirect:/user/dashboard";
            }

            user.getSharesList().removeIf(s -> s.getId().equals(id));
            usersRepo.save(user);


            sharesRepo.deleteById(id);

            redirectAttributes.addFlashAttribute("successMessage", "Share deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete share: " + e.getMessage());
        }

        return "redirect:/user/dashboard";
    }



}
