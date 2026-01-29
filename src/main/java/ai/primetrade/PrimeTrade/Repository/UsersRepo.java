package ai.primetrade.PrimeTrade.Repository;

import ai.primetrade.PrimeTrade.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UsersRepo extends MongoRepository<Users, ObjectId> {

    Optional<Users> findByEmail(String email);

    void deleteByEmail(String email);
}

