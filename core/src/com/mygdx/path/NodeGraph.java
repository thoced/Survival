package com.mygdx.path;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.utils.Array;

public class NodeGraph{
    public float x;
    public float y;
    public boolean obstacle;

    public int index;

    /** The neighbours of this node. i.e to which node can we travel to from here. */
    Array<Connection<NodeGraph>> mConnections = new Array<Connection<NodeGraph>>();

    public NodeGraph(float x, float y, boolean obstacle) {
        this.x = x;
        this.y = y;
        this.obstacle = obstacle;
    }

    public void addNeighbour(NodeGraph aNode) {
        if (null != aNode) {
            mConnections.add(new DefaultConnection<NodeGraph>(this, aNode));
        }
    }

    @Override
    public boolean equals(Object obj) {
       if(((NodeGraph)obj).x == x && ((NodeGraph)obj).y == y)
           return true;
       else
           return false;
    }
}
