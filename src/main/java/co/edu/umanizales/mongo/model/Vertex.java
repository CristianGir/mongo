package co.edu.umanizales.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vertex {
    private City dato;
    private int codigo;

    @Override
    public String toString() {
        return "Vertice{" + "dato=" + dato + ", codigo=" + codigo + '}';
    }
}
