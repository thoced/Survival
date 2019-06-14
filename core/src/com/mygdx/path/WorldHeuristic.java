package com.mygdx.path;

import com.badlogic.gdx.ai.pfa.Heuristic;
import com.badlogic.gdx.math.Vector2;

public class WorldHeuristic implements Heuristic<NodeGraph> {
    @Override
    public float estimate(NodeGraph currentNode, NodeGraph goalNode) {
        return Vector2.dst(currentNode.x, currentNode.y, goalNode.x, goalNode.y);
    }


}
