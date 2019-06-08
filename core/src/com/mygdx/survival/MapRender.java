package com.mygdx.survival;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapRender extends OrthogonalTiledMapRenderer {
    public MapRender(TiledMap map, Batch batch) {
        super(map, batch);
    }
}
