package com.wsdfhjxc.taponium.engine;

import android.graphics.*;
import android.view.*;

public abstract class Scene implements Comparable<Scene> {
    protected final SceneKeeper sceneKeeper;
    protected final ResourceKeeper resourceKeeper;
    protected final FlexConfig flexConfig;
    protected int order = 0;

    public Scene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig, int order) {
        this.sceneKeeper = sceneKeeper;
        this.resourceKeeper = resourceKeeper;
        this.flexConfig = flexConfig;
        this.order = order;
    }

    public abstract void load();

    public abstract void unload();

    public abstract void backPressed();

    public abstract void handleInput(MotionEvent motionEvent);

    public abstract void handleUpdate(double deltaTime);

    public abstract void handleRender(Canvas canvas, Paint paint, double alpha);

    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(Scene anotherScene) {
        return order - anotherScene.getOrder();
    }
}