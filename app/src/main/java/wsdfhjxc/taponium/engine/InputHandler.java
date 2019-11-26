package wsdfhjxc.taponium.engine;

import android.view.*;
import java.util.concurrent.*;

//입력을 다루는 핸들러
public class InputHandler extends TimedHandler implements ApplicationView.OnTouchListener {
    private final SceneKeeper sceneKeeper;
    private final BlockingQueue<MotionEvent> motionEvents;

    //생성자
    public InputHandler(ApplicationView applicationView, SceneKeeper sceneKeeper) {
        super(1000, true); // 1000 input catches per second should be more than enough
        this.sceneKeeper = sceneKeeper;
        motionEvents = new LinkedBlockingQueue();
        applicationView.setOnTouchListener(this);
    }

    @Override
    public void handle() {
        while (!motionEvents.isEmpty()) {
            MotionEvent motionEvent = motionEvents.poll();
            if (motionEvent != null) {
                for (Scene scene : sceneKeeper.scenes) {
                    scene.handleInput(motionEvent);
                }
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvents.offer(motionEvent);
        return true;
    }
}

