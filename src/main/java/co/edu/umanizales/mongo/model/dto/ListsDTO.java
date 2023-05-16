package co.edu.umanizales.mongo.model.dto;

import co.edu.umanizales.mongo.model.City;
import co.edu.umanizales.mongo.model.Edge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListsDTO {
    private List<City> cities;
    private List<Edge> edges;
}
