package com.progark.emojimon.mapTiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TiledMapActor extends Actor {

    private TiledMap tiledMap;

    private TiledMapTileLayer tiledLayer;

    private TiledMapTileLayer.Cell cell;

    private int tileId;

    public TiledMapActor(TiledMap tiledMap, TiledMapTileLayer tiledLayer, TiledMapTileLayer.Cell cell, int id) {
        this.tiledMap = tiledMap;
        this.tiledLayer = tiledLayer;
        this.cell = cell;
        this.tileId = id;
    }

    public TiledMapTileLayer.Cell getCell(){
        return cell;
    }

    public int getTileId(){
        return tileId;
    }
}

