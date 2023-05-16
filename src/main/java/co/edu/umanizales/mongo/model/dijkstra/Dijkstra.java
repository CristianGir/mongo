package co.edu.umanizales.mongo.model.dijkstra;

import co.edu.umanizales.mongo.model.*;
import co.edu.umanizales.mongo.model.exception.GraphException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Dijkstra implements Serializable{
    private short origin;
    private short destiny;
    private List<DijkstraVertex> DVertex;
    private int checks;
    private Graph graph;

    public Dijkstra(short origin, short destiny, Graph graph) {
        this.origin = origin;
        this.destiny = destiny;
        this.graph = graph;
        llenarVerticesDjikstra();
    }

    private void llenarVerticesDjikstra()
    {
        DVertex = new ArrayList<>();
        //Recorre todos los vertices del grafo
        for(Vertex vertGrafo: graph.getVertex())
        {
            //Parado en un vertice del grafo
            DijkstraVertex vertD= new DijkstraVertex(
                    vertGrafo.getCodigo(),null,(short)0);
            DVertex.add(vertD);
        }
    }

    public List<String> calcularDjikstra() throws GraphException
    {
        DijkstraVertex vertActual;
        //1. Pararme en el origen
        vertActual = obtenerVerticexCodigo(origin);
        // null y peso 0
        calcularDjikstra(vertActual);

        //Se si tengo o no ruta
        List<DijkstraVertex> ruta = new ArrayList<>();
        DijkstraVertex vertDestino = obtenerVerticexCodigo(destiny);
        while(vertDestino!=null)
        {
            ruta.add(vertDestino);
            vertDestino = vertDestino.getBefore();
        }
        if(ruta.size()<=1)
        {
            throw  new GraphException("No hay ruta ");
        }
        return obtenerNombresCiudades(ruta);
    }
    private void calcularDjikstra(DijkstraVertex vertActual)
    {
        /*
        2. Asigno de donde viene y peso acumulado  (Faltante)

        /*
        3. obtengo las adyancencias del vertice en el que estoy
        (Diferente si el grafo es dirigido o no dirigido
        */
        List<Edge> adyacencias = graph.getAdjacencies(vertActual.getCode());
        /*
        4. Visito todas las adyancencias
        5. Cada adyacencia actualizo su origen y peso acumulado
           cuando es menor
        */
        actualizarAdyacencias(vertActual, adyacencias);
        /*
        6.  a) Marco en el que estoy parado
             b)verificar si ya todos estan marcados (Finishing) voy al punto 8
            cuando todos vertcesD esten marcado - marcados = verticesD.size() (Faltante)
        */
        vertActual.setCheck(true);
        this.checks++;
        if(checks < graph.getVertex().size())
        {
            /*
            7. Salto a la adyacencia menor no marcada
            volver al 2
            */
            DijkstraVertex vertMenorNoVisitado=
                    obtenerAdyacenciaMenorNoVisitada(adyacencias, vertActual);
            calcularDjikstra(vertMenorNoVisitado);

        }

    }
    public List<String> obtenerNombresCiudades(List<DijkstraVertex> ruta) {
        List<String> nombresCiudades = new ArrayList<>();
        for (DijkstraVertex vertice : ruta) {
            int codigoCiudad = vertice.getCode();
            Vertex ciudad = graph.getVertex().stream()
                    .filter(v -> v.getCodigo() == codigoCiudad)
                    .findFirst()
                    .orElse(null);
            if (ciudad != null) {
                nombresCiudades.add(((City) ciudad.getDato()).getName());
            }
        }
        return nombresCiudades;
    }


    public DijkstraVertex obtenerVerticexCodigo(int codigo)
    {
        // Objetos son referencias en memoria
        for(DijkstraVertex vertD: DVertex)
        {
            if(vertD.getCode()==codigo)
            {
                return vertD;
            }
        }
        return null;
    }

    private void actualizarAdyacencias(DijkstraVertex vertActual, List<Edge> adyacencias)
    {
        //Obtener las adyacencias de verticesD
        //recorriendo las aristas del grafo
        // actualizo los pesos y anterior de las adyacencias
        // si esta nulo el anterior actualizo el anterior con el vertice
        //actual
        // si no esta nulo comparo si es menor el peso acumulado para
        //actualizar
        for(Edge ari:adyacencias)
        {
            DijkstraVertex visitado= obtenerVerticexCodigo(ari.getDestino());
            //Actualizarle su origen y peso
            if(visitado.getBefore()==null)
            {
                //NO ha sido visitado
                visitado.setBefore(vertActual);
                visitado.setWeight((short)(vertActual.getWeight()+ari.getPeso()));
            }
            else
            {
                short pesoAcumulado=(short)(vertActual.getWeight()+ari.getPeso());
                if(pesoAcumulado < visitado.getWeight())
                {
                    visitado.setBefore(vertActual);
                    visitado.setWeight(pesoAcumulado);
                }
            }
        }

    }


    private DijkstraVertex obtenerAdyacenciaMenorNoVisitada(List<Edge> adyacencias,
                                                             DijkstraVertex anterior)
    {
        //Menor peso acumulado
        // cuando dos vertices tienen el mismo salta a cualquiera
        short menor= Short.MAX_VALUE;
        DijkstraVertex verticeSalto =null;
        for(Edge ari: adyacencias)
        {
            int codigoVerticeAnalizar=0;
            //Si mi grafo es dirigido a no es dirigido
            if(graph instanceof UndirectedGraph)
            {
                //Siempre voy a obtener el vertice que voy a analizar con el destino
                codigoVerticeAnalizar= ari.getOrigin();
            }
            else
            {
                // Yo tengo que determinar si debo buscar el vertice a analizar con el
                // origen de la arista o con el destino
                codigoVerticeAnalizar= ari.getOrigin();
                if(ari.getOrigin()== anterior.getCode())
                {
                    codigoVerticeAnalizar= ari.getDestino();
                }

            }
            DijkstraVertex vertAdyacente = obtenerVerticexCodigo(codigoVerticeAnalizar);

            if(!vertAdyacente.isCheck()) //Me interesan los que no estén marcados
            {
                if(vertAdyacente.getWeight() < menor)
                {
                    //Actualizar el menor  y marcar ese vertice como al que debo
                    //saltar
                    menor= vertAdyacente.getWeight();
                    verticeSalto = vertAdyacente;
                }
            }

        }
        //Se puede presentar el bloqueo y hay que saltar a cualquiera
        if(verticeSalto == null)
        {
            //Un bloqueo salto a cualquiera no marcado
            for(DijkstraVertex vertD: DVertex)
            {
                if(!vertD.isCheck())
                {
                    return vertD;
                }
            }
        }
        return verticeSalto;
    }

}