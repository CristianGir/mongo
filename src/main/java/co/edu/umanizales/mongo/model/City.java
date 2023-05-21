package co.edu.umanizales.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
@Data
@AllArgsConstructor
public class City {
    @Id
    private int cityId;

    private String name;
}
