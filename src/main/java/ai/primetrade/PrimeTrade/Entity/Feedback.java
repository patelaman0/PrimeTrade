package ai.primetrade.PrimeTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;


@Data
@Document (collection ="feedBack")
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    private ObjectId id;
    private String fullName;
    @NonNull
    private String email;
    @NonNull
    private String message;

}
