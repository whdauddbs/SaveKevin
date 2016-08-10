package com.wsdfhjxc.taponium.engine;

public class RenderHandler extends TimedHandler {
    private final ApplicationView applicationView;
    private final UpdateHandler updateHandler;

    public RenderHandler(ApplicationView applicationView, UpdateHandler updateHandler, int frequency) {
        super(frequency, false);
        this.applicationView = applicationView;
        this.updateHandler = updateHandler;
    }

    @Override
    protected void handle() {
        double alpha = updateHandler.getAlpha();
        applicationView.update(alpha);
    }
}