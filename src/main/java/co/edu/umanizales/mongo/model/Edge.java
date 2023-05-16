package co.edu.umanizales.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "edges")
@Data
@AllArgsConstructor
public class Edge implements Serializable {
    private int origin;
    private int destino;
    private short peso;

    @Override
    public String toString() {
        return "Arista{" + "origen=" + origin + ", destino=" + destino + ", peso=" + peso + '}';
    }


}
