package ai.primetrade.PrimeTrade.Repository;

import ai.primetrade.PrimeTrade.Entity.Shares;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SharesRepo extends MongoRepository<Shares , ObjectId> {
}
