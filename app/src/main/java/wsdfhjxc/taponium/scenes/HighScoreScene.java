package wsdfhjxc.taponium.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;

import wsdfhjxc.taponium.engine.EngineRunner;
import wsdfhjxc.taponium.engine.Flex;
import wsdfhjxc.taponium.engine.FlexConfig;
import wsdfhjxc.taponium.engine.ResourceKeeper;
import wsdfhjxc.taponium.engine.Scene;
import wsdfhjxc.taponium.engine.SceneKeeper;
import wsdfhjxc.taponium.engine.UpdateHandler;
import wsdfhjxc.taponium.game.HighScoreCounterRenderer;
import wsdfhjxc.taponium.game.HighScoreList;
import wsdfhjxc.taponium.game.ScoreCounter;
import wsdfhjxc.taponium.game.ScoreCounterRenderer;

public class HighScoreScene extends Scene {


    private Bitmap backSignBitmap; // 뒤로가기 버튼(게임 내) 비트맵 객체
    private Rect backSignRect; // 뒤로가기 버튼(게임 내) 비트맵을 둘러싼 사각형 객체
    private Flex backSignFlex; // 뒤로가기 버튼(게임 내) 비트맵 flex 객체
    // touch input will be unlocked after half a second to avoid accidental click
    // 실수로 클릭하는 것을 방지하기 위해서 0.5초 후에 잠금이 해제됨
    // 게임 오버시 게임 오버 장면을 보여주지 못하고 넘어가는 것을 방지하기 위해 설정하는 변수들
    private double unlockTotalDuration = 0.5;
    private double unlockCurrentDuration = 0.0;
    // GameOverScene 생성자 생성

    private HighScoreCounterRenderer highScoreCounterRenderer; // 점수 카운터 렌더러 객체



    //하이스코어 씬 생성자
    public HighScoreScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 6, 1); // Scene 클래스 생성자
    }





    @Override // load함수 오버라이딩
    public void load() {

        backSignBitmap = resourceKeeper.getBitmap("back_sign"); // 뒤로가기 버튼 객체 이미지 객체 생성

        // 뒤로가기 버튼 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        backSignRect = new Rect(0, 0, backSignBitmap.getWidth(), backSignBitmap.getHeight());

        // 뒤로가기 버튼을 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        backSignFlex = new Flex(new PointF(1f, 0f), false,
                new PointF(backSignBitmap.getWidth(), backSignBitmap.getHeight()), true,
                new Point(-backSignBitmap.getWidth() - 20, 20),
                flexConfig);

        //=============================================================================================================
        //점수를 담은 리스트 (5개)
        long scoreList[][] = EngineRunner.highScoreList.getStList();
        //=============================================================================================================

        highScoreCounterRenderer = new HighScoreCounterRenderer(scoreList, resourceKeeper, flexConfig); // 점수 카운터 렌더러 객체 생성

    }






    @Override // unload함수(잠금해제) 오버라이딩
    public void unload() {}




    @Override // backPressed함수 오버라이딩
    // 뒤로가기 버튼이 눌리면 현재 Scene을 제거하고 메인메뉴 Scene 추가(뒤로가기 버튼이 눌리면 메인메뉴 장면으로 이동한다.)
    public void backPressed() {
        sceneKeeper.removeScene(this); // 현재 Scene 제거
        sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인메뉴 Scene 추가
    }




    @Override // handleInput함수(터치 입력) 오버라이딩
    public void handleInput(MotionEvent motionEvent) {
        Log.d("click", Integer.toString(motionEvent.getAction()));
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
            if (backSignFlex.getRect().contains((int) motionEvent.getX(),
                    (int) motionEvent.getY())) { // 뒤로가기 버튼(게임 내)의 범위에 마우스 커서가 들어있다면
                sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
                sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인 메뉴로 돌아간다.
            }
        }
    }




    @Override // handleUpdate함수(터치 입력) 오버라이딩
    public void handleUpdate(double deltaTime) { // 잠금이 해제된 기간 < 잠금이 해제된 총 기간인 경우
        if (unlockCurrentDuration < unlockTotalDuration) {
            unlockCurrentDuration += deltaTime; // 현재 잠금이 해제된 기간에 전 프레임과 현재 프레임사이의 시간을 더해준다.
        }
    }




    @Override // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint, double alpha) {
        canvas.drawBitmap(backSignBitmap, backSignRect, backSignFlex.getRect(), paint); // 뒤로가기 버튼(게임 내)을 그린다.
        highScoreCounterRenderer.render(canvas, paint); // 점수 카운터를 그린다.
    }
}
