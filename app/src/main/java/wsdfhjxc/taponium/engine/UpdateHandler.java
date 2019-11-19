package wsdfhjxc.taponium.engine;

public class UpdateHandler extends TimedHandler {
    private final SceneKeeper sceneKeeper;
    private final double deltaTime;

    public UpdateHandler(SceneKeeper sceneKeeper, int frequency) {
        super(frequency, true);
        this.sceneKeeper = sceneKeeper;
        deltaTime = getDeltaTime();
    } //UpdateHandler의 생성자

    @Override
    public void handle() {
        for (Scene scene : sceneKeeper.scenes) {
            scene.handleUpdate(deltaTime);
        }
    }// 각 장면들이 sceneKeeper에 저장된 장면 배열을 돌면서 deltaTime 인자를 받아서 scene의 handleUpdate 추상 함수를 실행한다.
}