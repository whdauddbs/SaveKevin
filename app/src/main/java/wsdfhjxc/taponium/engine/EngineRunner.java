package wsdfhjxc.taponium.engine;

import android.app.*;
import android.os.*;

import wsdfhjxc.taponium.game.HighScoreList;
import wsdfhjxc.taponium.scenes.*;

public class EngineRunner implements Runnable {
    private boolean running = false; // 게임이 실행되고 있는지
    private boolean paused = false;  // 액티비티가 멈춰있는지

    private final Thread runnerThread = new Thread(this);  // 게임을 실행시키는 쓰레드
    private final Activity activity;

    private final SceneKeeper sceneKeeper; // 씬 저장하는 변수
    private final ResourceKeeper resourceKeeper; // 리소스 저장하는 변수

    private final InputHandler inputHandler; // 입력을 다루는 핸들러
    private final UpdateHandler updateHandler;  // 상태 변화를 다루는 핸들러
    private final RenderHandler renderHandler;  // 렌더링을 다루는 핸들러

    public static HighScoreList highScoreList;

    //엔진러너 생성자
    public EngineRunner(Activity activity) {   // 생성자. 게임 화면을 띄울 액티비티를 받고, 게임 구동에 필요한 객체들을 생성한다.
        this.activity = activity;   //액티비티 초기화

        sceneKeeper = new SceneKeeper();    //씬키퍼 초기화
        resourceKeeper = new ResourceKeeper(activity);  //리소스키퍼 초기화

        updateHandler = new UpdateHandler(sceneKeeper, 15); // 15 updates per second

        ApplicationView applicationView = new ApplicationView(activity, sceneKeeper, updateHandler);
        activity.setContentView(applicationView);

        inputHandler = new InputHandler(applicationView, sceneKeeper);
        renderHandler = new RenderHandler(applicationView, 30); // 30 renders per second

        // flex config for resolution independence calculations
        FlexConfig flexConfig = new FlexConfig(activity, 1080); // base resolution is 1080p width

        // add initial default scene to processing list
        sceneKeeper.addScene(new DefaultScene(sceneKeeper, resourceKeeper, renderHandler, flexConfig));

        highScoreList = new HighScoreList();
    }

    @Override
    public void run() {   // 게임을 실행시키고 종료하는 쓰레드. 실질적으로 게임을 구동하는 부분.
        while (running && sceneKeeper.hasScenes()) {
            if (!paused) {
                sceneKeeper.poll();
                inputHandler.poll();
                updateHandler.poll();
                renderHandler.poll();
            }

            SystemClock.sleep(1); // prevent CPU from hogging and draining battery
        }

        sceneKeeper.removeAllScenes();
        resourceKeeper.unloadEverything();

        activity.finish();
    }

    public void start() {    // 앱의 화면이 켜질때 실행되는 부분. 위에서 선언한 쓰레드를 시작시킨다.
        if (!runnerThread.isAlive()) {
            running = true;
            runnerThread.start();
        } else {
            resume();
        }
    }

    public void stop() {
        running = false;
    } // 앱이 꺼질때 게임을 종료하는 역할을 함

    public void pause() {
        paused = true;
    }  //앱이 멈췄을때 게임을 일시 정지함.

    public void resume() {  // 앱이 멈췄다가 다시 시작할때 게임을 재개함.
        paused = false;
        updateHandler.resetDelay(); // ignore a big time difference after coming back from pause
    }

    public void backPressed() {   // 뒤로가기 버튼을 눌렀을때, 이전 씬을 불러오는 역할을 함.
        for (Scene scene : sceneKeeper.scenes) {
            scene.backPressed();
        }
    }
}