package wsdfhjxc.taponium.engine;

import android.util.Log;

import wsdfhjxc.taponium.MainActivity;

public class UpdateHandler extends TimedHandler {
    private final SceneKeeper sceneKeeper;
    private double deltaTime;
    public static boolean isPause = false;

    public UpdateHandler(SceneKeeper sceneKeeper, int frequency) {
        super(frequency, true);
        this.sceneKeeper = sceneKeeper;
            deltaTime = getDeltaTime();
    }

    // 각 장면들이 sceneKeeper에 저장된 장면 배열을 돌면서
    // deltaTime 인자를 받아서 scene의 handleUpdate 추상 함수를 실행한다.
    @Override
    public void handle() {
        for (Scene scene : sceneKeeper.scenes) {
            if(!isPause)
                scene.handleUpdate(deltaTime);
            else
                scene.handleUpdate(0);
        }
    }
}