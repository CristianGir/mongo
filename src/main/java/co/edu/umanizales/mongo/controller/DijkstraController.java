package co.edu.umanizales.mongo.controller;

import co.edu.umanizales.mongo.model.City;
import co.edu.umanizales.mongo.model.Dijkstra;
import co.edu.umanizales.mongo.model.Edge;
import org.springframework.web.client.RestTemplate;
import co.edu.umanizales.mongo.model.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class DijkstraController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Dijkstra dijkstra;

    @Autowired
    public DijkstraController(Dijkstra dijkstra) {
        this.dijkstra = dijkstra;
    }

    @PostMapping("/shortest-path/{startid}/{endid}")
    public ResponseEntity<ResponseDTO> calculateShortestPath(@PathVariable String startid,@PathVariable String endid, @RequestBody List<City> cities, @RequestBody List<Edge> edges) {
        try {
            City startCity = null, endCity = null;
            for (City city : cities) {
                if (city.getCityId().equals(startid)) {
                    startCity = city;
                }
                if (city.getCityId().equals(endid)) {
                    endCity = city;
                }
            }
            if (startCity == null || endCity == null) {
                return new ResponseEntity<>(new ResponseDTO(404, null, null), HttpStatus.OK);
            }
            List<City> shortestPath = dijkstra.calculateShortestPath(cities, edges, startCity, endCity);
            return new ResponseEntity<>(new ResponseDTO(200, shortestPath, null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO(500, null, null), HttpStatus.OK);
        }
    }

}
