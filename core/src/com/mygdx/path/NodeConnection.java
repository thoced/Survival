package com.mygdx.path;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class NodeConnection implements Connection<NodeGraph> {

    NodeGraph fromNode;
    NodeGraph toNode;
    float cost;

    public NodeConnection(NodeGraph fromNode, NodeGraph toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;



        this.cost = Vector2.dst(fromNode.x,fromNode.y,toNode.x,toNode.y);
    }

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public NodeGraph getFromNode() {
        return fromNode;
    }

    @Override
    public NodeGraph getToNode() {
        return toNode;
    }
}
