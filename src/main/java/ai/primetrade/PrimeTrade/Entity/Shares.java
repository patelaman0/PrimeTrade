package ai.primetrade.PrimeTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Document (collection = "shares")

@AllArgsConstructor
@NoArgsConstructor
public class Shares {

    @Id
    private ObjectId id;
    @NonNull
    private String shareName;
    @NonNull
   private String shareDescription;
    @NonNull
    private String username;

}
