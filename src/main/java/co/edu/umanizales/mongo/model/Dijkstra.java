package co.edu.umanizales.mongo.model;

import co.edu.umanizales.mongo.model.City;
import co.edu.umanizales.mongo.model.Edge;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Dijkstra {

    public List<City> calculateShortestPath(List<City> cities, List<Edge> edges, City startCity, City endCity) {

        // Paso 1: Inicializar variables
        Map<City, Integer> distance = new HashMap<>();
        Map<City, City> previous = new HashMap<>();
        Set<City> unvisited = new HashSet<>();
        for (City city : cities) {
            distance.put(city, Integer.MAX_VALUE);
            previous.put(city, null);
            unvisited.add(city);
        }
        distance.put(startCity, 0);

        // Paso 2: Calcular distancias mínimas
        while (!unvisited.isEmpty()) {
            City currentCity = getCityWithMinDistance(unvisited, distance);
            unvisited.remove(currentCity);
            if (currentCity.equals(endCity)) {
                break;
            }
            for (Edge edge : getEdgesFromCity(edges, currentCity)) {
                City neighborCity = getNeighborCity(currentCity, edge);
                if (unvisited.contains(neighborCity)) {
                    int altDistance = distance.get(currentCity) + edge.getWeight();
                    if (altDistance < distance.get(neighborCity)) {
                        distance.put(neighborCity, altDistance);
                        previous.put(neighborCity, currentCity);
                    }
                }
            }
        }

        // Paso 3: Construir camino más corto
        List<City> shortestPath = new ArrayList<>();
        City currentCity = endCity;
        while (previous.get(currentCity) != null) {
            shortestPath.add(currentCity);
            currentCity = previous.get(currentCity);
        }
        shortestPath.add(currentCity);
        Collections.reverse(shortestPath);

        return shortestPath;
    }

    private City getCityWithMinDistance(Set<City> unvisited, Map<City, Integer> distance) {
        City minCity = null;
        int minDistance = Integer.MAX_VALUE;
        for (City city : unvisited) {
            if (distance.get(city) < minDistance) {
                minCity = city;
                minDistance = distance.get(city);
            }
        }
        return minCity;
    }

    private List<Edge> getEdgesFromCity(List<Edge> edges, City city) {
        List<Edge> edgesFromCity = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getOrigin().equals(city)) {
                edgesFromCity.add(edge);
            }
        }
        return edgesFromCity;
    }

    private City getNeighborCity(City city, Edge edge) {
        if (edge.getOrigin().equals(city)) {
            return edge.getDestiny();
        } else {
            return edge.getOrigin();
        }
    }

}
