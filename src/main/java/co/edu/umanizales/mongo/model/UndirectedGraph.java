package co.edu.umanizales.mongo.model;

import java.util.ArrayList;
import java.util.List;

public class UndirectedGraph extends Graph{

    @Override
    public boolean validateExistingEdge(Edge edge) {
        for(Edge ari:getEdges())
        {
            if(ari.getOrigin()==edge.getOrigin() &&
                    ari.getDestino()==edge.getDestino() &&
                    ari.getPeso() == edge.getPeso())
            {
                return true;
            }
            if(ari.getOrigin()==edge.getDestino()&&
                    ari.getDestino()==edge.getOrigin() &&
                    ari.getPeso() == edge.getPeso())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Edge> getAdjacencies(int origin) {
        List<Edge> listado= new ArrayList<>();
        for(Edge ari: getEdges())
        {
            if(ari.getOrigin()==origin || ari.getDestino()==origin)
            {
                listado.add(ari);
            }
        }

        return listado;

    }
}