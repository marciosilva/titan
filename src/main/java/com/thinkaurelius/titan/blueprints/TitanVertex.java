package com.thinkaurelius.titan.blueprints;

import com.thinkaurelius.titan.blueprints.util.TitanEdgeSequence;
import com.thinkaurelius.titan.blueprints.util.TitanNeighborhoodSequence;
import com.thinkaurelius.titan.core.Direction;
import com.thinkaurelius.titan.core.Node;
import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.blueprints.pgm.impls.MultiIterable;

import java.util.ArrayList;
import java.util.List;

public class TitanVertex extends TitanElement<Node> implements Vertex {


    public TitanVertex(final Node element) {
        super(element);
    }

    private Iterable<Edge> getEdges(Direction d, String... labels) {
        if (labels.length==0) {
            return new TitanEdgeSequence(element.getRelationshipIterator(d));
        } else if (labels.length==1) {
            return new TitanEdgeSequence(element.getRelationshipIterator(labels[0],d));
        } else {
            final List<Iterable<Edge>> edges = new ArrayList<Iterable<Edge>>();
            for (final String label : labels) {
                edges.add(new TitanEdgeSequence(element.getRelationshipIterator(label,d)));
            }
            return new MultiIterable<Edge>(edges);
        }
    }

    @Override
    public Iterable<Edge> getOutEdges(String... labels) {
        return getEdges(Direction.Out,labels);
    }
    
    @Override
    public Iterable<Edge> getInEdges(String... labels) {
        return getEdges(Direction.In,labels);
    }
}