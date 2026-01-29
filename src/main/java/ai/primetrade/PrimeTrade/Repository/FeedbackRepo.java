package ai.primetrade.PrimeTrade.Repository;

import ai.primetrade.PrimeTrade.Entity.Feedback;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepo extends MongoRepository<Feedback, ObjectId> {

}
