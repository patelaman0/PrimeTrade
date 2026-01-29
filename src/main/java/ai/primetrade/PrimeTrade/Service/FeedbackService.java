package ai.primetrade.PrimeTrade.Service;

import ai.primetrade.PrimeTrade.Entity.Feedback;
import ai.primetrade.PrimeTrade.Repository.FeedbackRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired FeedbackRepo feedbackRepo;


    public List<Feedback> getAllFeedback(){
        return feedbackRepo.findAll();
    }

    public void deleteFeedback(ObjectId id) {
        feedbackRepo.deleteById(id);
    }
}
