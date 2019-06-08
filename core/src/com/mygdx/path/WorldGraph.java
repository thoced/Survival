package com.mygdx.path;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.PathSmoother;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.ai.utils.Collision;
import com.badlogic.gdx.ai.utils.Ray;
import com.badlogic.gdx.ai.utils.RaycastCollisionDetector;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import javax.xml.soap.Node;

public class WorldGraph implements IndexedGraph<NodeGraph> {

    static int width;
    static int height;
    static WorldHeuristic worldHeuristic = new WorldHeuristic();
   // Array<NodeGraph> nodes = new Array<NodeGraph>();
    Array<NodeConnection> connections = new Array<NodeConnection>();

    ObjectMap<NodeGraph, Array<Connection<NodeGraph>>> connectionsMap = new ObjectMap<NodeGraph, Array<Connection<NodeGraph>>>();

    private static NodeGraph[][] nodeGraphs;

    private int lastNodeIndex = 0;

    private static WorldGraph ourInstance = new WorldGraph();

    public static WorldGraph getInstance() {
        return ourInstance;
    }

    private WorldGraph() {

    }

    public void prepareWorldGraph(int width,int height){
        this.width = width;
        this.height = height;
        nodeGraphs = new NodeGraph[width][height];

    }


    public void addNodeGraph(NodeGraph nodeGraph){
        nodeGraph.index = lastNodeIndex;
        lastNodeIndex++;

        if(nodeGraphs[(int) nodeGraph.x][(int) nodeGraph.y] == null)
            nodeGraphs[(int) nodeGraph.x][(int) nodeGraph.y] = nodeGraph;

    }

    public void connectNodes(){

        for(int y=0;y < height; y++) {
            for (int x = 0; x < width; x ++) {
                if(nodeGraphs[x][y] != null){
                    addNodeNeighbour(nodeGraphs[x][y], x - 1, y); // Node to left
                    addNodeNeighbour(nodeGraphs[x][y], x + 1, y); // Node to right
                    addNodeNeighbour(nodeGraphs[x][y], x, y - 1); // Node below
                    addNodeNeighbour(nodeGraphs[x][y], x, y + 1); // Node above
                }
            }
        }

    }

    private void addNodeNeighbour(NodeGraph aNode, int x, int y) {
        // Make sure that we are within our array bounds.
        if(x < 0 || x > width - 1 || y < 0 || y > height - 1)
                 return;
            else
        {
            aNode.addNeighbour(nodeGraphs[x][y]);
        }
    }

    private void addNodeConnection(NodeGraph fromNode, NodeGraph toNode) {
        NodeConnection nodeConnection = new NodeConnection(fromNode, toNode);
        if(!connectionsMap.containsKey(fromNode)){
            connectionsMap.put(fromNode, new Array<Connection<NodeGraph>>());
        }
        connectionsMap.get(fromNode).add(nodeConnection);
        connections.add(nodeConnection);
    }


    private boolean isFreeNode( int i, int j) {

        if(i < 0 || i > width - 1 || j < 0 || j > height - 1)
            return false;
        else
            return !nodeGraphs[i][j].obstacle;

    }

    public GraphPath<NodeGraph> findPath(NodeGraph startCity, NodeGraph goalCity){
        GraphPath<NodeGraph> nodePath = new DefaultGraphPath<NodeGraph>();
        IndexedAStarPathFinder<NodeGraph> indexedAStarPathFinder = new IndexedAStarPathFinder<NodeGraph>(WorldGraph.this);
        boolean ret = indexedAStarPathFinder.searchNodePath(startCity, goalCity, worldHeuristic, nodePath);



        return nodePath;
    }

    public  NodeGraph getNode(int x,int y){
        if(x>-1 && x < width && y >-1 && y < height)
            return nodeGraphs[x][y];
        else
            return null;
    }

    @Override
    public int getIndex(NodeGraph node) {
        return node.index;
    }

    @Override
    public int getNodeCount() {
        return lastNodeIndex;
    }

    @Override
    public Array<Connection<NodeGraph>> getConnections(NodeGraph fromNode) {

        if(nodeGraphs[(int) fromNode.x][(int) fromNode.y] != null){
            if(nodeGraphs[(int) fromNode.x][(int) fromNode.y].mConnections != null)
                return nodeGraphs[(int) fromNode.x][(int) fromNode.y].mConnections;

        }
        return new Array<Connection<NodeGraph>>(0);
    }


}
