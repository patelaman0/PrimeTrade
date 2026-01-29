package ai.primetrade.PrimeTrade.Controller;

import ai.primetrade.PrimeTrade.Entity.Feedback;
import ai.primetrade.PrimeTrade.Repository.FeedbackRepo;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

@RequestMapping
public class FeedbackController {

    @Autowired FeedbackRepo feedbackRepo;

    @GetMapping("/")
    public String showFeedback(Model model){
        model.addAttribute("feedback", new Feedback());
                return "index";
    }

    @PostMapping("/feedback")
   public String newFeedback(@ModelAttribute Feedback feedback, RedirectAttributes redirectAttributes){
        try{
            feedbackRepo.save(feedback);
          redirectAttributes.addFlashAttribute("feedbackMessage","Successfully form sent");

        }
        catch(Exception e){
            redirectAttributes.addFlashAttribute(
                    "feedbackError", "Something went wrong. Please try again.");
        }
        return "redirect:/#contact";
    }
}
