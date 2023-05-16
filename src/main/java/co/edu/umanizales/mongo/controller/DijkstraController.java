package co.edu.umanizales.mongo.controller;

import co.edu.umanizales.mongo.model.*;
import co.edu.umanizales.mongo.model.dto.ResponseDTO;
import co.edu.umanizales.mongo.model.exception.GraphException;
import co.edu.umanizales.mongo.model.dijkstra.Dijkstra;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class DijkstraController {

    @GetMapping("/dijkstra")
    public ResponseEntity<ResponseDTO> dijkstra() throws GraphException {
        Graph graph = new UndirectedGraph();

        // Agregar vértices al grafo
        City vertexA = new City("A", "Manizales");
        City vertexB = new City("B", "Pereira");
        City vertexC = new City("C", "Cartago");
        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);

        // Agregar aristas al grafo
        Edge edgeAB = new Edge(1, 2, (short) 5);
        Edge edgeAC = new Edge(1, 3, (short) 3);
        Edge edgeBC = new Edge(2, 3, (short) 2);
        graph.addEdge(edgeAB);
        graph.addEdge(edgeAC);
        graph.addEdge(edgeBC);

        // Crear objeto Dijkstra
        short origin = 1;
        short destiny = 3;
        Dijkstra dijkstra = new Dijkstra(origin, destiny, graph);
        return new ResponseEntity<>(new ResponseDTO(200, dijkstra.calcularDjikstra(), null), HttpStatus.OK);
    }
}
