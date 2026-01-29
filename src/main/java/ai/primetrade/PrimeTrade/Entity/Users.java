package ai.primetrade.PrimeTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document (collection ="Users")
@AllArgsConstructor
@NoArgsConstructor

public class Users {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;

    List<String> roles = new ArrayList<>();

    @DBRef
    List<Shares> sharesList = new ArrayList<>();
}
