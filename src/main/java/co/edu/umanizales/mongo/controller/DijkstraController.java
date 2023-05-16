package co.edu.umanizales.mongo.controller;

import co.edu.umanizales.mongo.model.*;
import co.edu.umanizales.mongo.model.dto.ListsDTO;
import co.edu.umanizales.mongo.model.dto.ResponseDTO;
import co.edu.umanizales.mongo.model.exception.GraphException;
import co.edu.umanizales.mongo.model.dijkstra.Dijkstra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class DijkstraController {

    @PostMapping("/dijkstra/{origin}/{destiny}")
    public ResponseEntity<ResponseDTO> dijkstra(@PathVariable short origin,
                                                @PathVariable short destiny,
                                                @RequestBody ListsDTO lists) throws GraphException {
        Graph graph = new UndirectedGraph();

        for (City city: lists.getCities()) {
            graph.addVertex(city);
        }
        for (Edge edge: lists.getEdges()) {
            graph.addEdge(edge);
        }
        Dijkstra dijkstra = new Dijkstra(origin, destiny, graph);
        return new ResponseEntity<>(new ResponseDTO(200, dijkstra.calcularDjikstra(), null), HttpStatus.OK);
    }
}
