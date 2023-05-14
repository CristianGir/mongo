package co.edu.umanizales.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "edges")
@Data
@AllArgsConstructor
public class Edge {
    @Id
    private String edgeId;
    private City origin;
    private Integer weight;
    private City destiny;
}
