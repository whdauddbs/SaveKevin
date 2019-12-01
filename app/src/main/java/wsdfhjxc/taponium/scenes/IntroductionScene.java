package wsdfhjxc.taponium.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

import wsdfhjxc.taponium.engine.Flex;
import wsdfhjxc.taponium.engine.FlexConfig;
import wsdfhjxc.taponium.engine.ResourceKeeper;
import wsdfhjxc.taponium.engine.Scene;
import wsdfhjxc.taponium.engine.SceneKeeper;

public class IntroductionScene extends Scene {
    private Bitmap backSignBitmap; // 뒤로가기 버튼(게임 내) 비트맵 객체
    private Rect backSignRect; // 뒤로가기 버튼(게임 내) 비트맵을 둘러싼 사각형 객체
    private Flex backSignFlex; // 뒤로가기 버튼(게임 내) 비트맵 flex 객체

    private Bitmap IntroductionPanelBitmap; // 메뉴 패널 비트맵 객체
    private Rect IntroductionPanelRect; // 메뉴 패널을 둘러싼 사각형 객체
    private Flex IntroductionPanelFlex; // 메뉴 패널 flex 객체

    // 메인 메뉴 Scene 생성자
    public IntroductionScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 4, 1); // Scene 클래스 생성자
    }

    @Override
    public void load() {


        IntroductionPanelBitmap = resourceKeeper.getBitmap("introduction_panel"); // 타이틀 패널 이미지 로드
        // 타이틀 패널 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        IntroductionPanelRect = new Rect(0, 0, IntroductionPanelBitmap.getWidth(), IntroductionPanelBitmap.getHeight());
        // 타이틀 패널 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        IntroductionPanelFlex = new Flex(new PointF(1f, 0.29f), false,
                new PointF(IntroductionPanelBitmap.getWidth(), IntroductionPanelBitmap.getHeight()), true,
                new Point(-IntroductionPanelBitmap.getWidth(), -IntroductionPanelBitmap.getWidth()/2),
                flexConfig);

        backSignBitmap = resourceKeeper.getBitmap("back_sign"); // 뒤로가기 버튼 객체 이미지 객체 생성

        // 뒤로가기 버튼 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        backSignRect = new Rect(0, 0, backSignBitmap.getWidth(), backSignBitmap.getHeight());

        // 뒤로가기 버튼을 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        backSignFlex = new Flex(new PointF(1f, 0f), false,
                new PointF(backSignBitmap.getWidth(), backSignBitmap.getHeight()), true,
                new Point(-backSignBitmap.getWidth() - 20, 20),
                flexConfig);
    }

    @Override // unload함수(잠금해제) 오버라이딩
    public void unload() {}

    @Override // backPressed함수 오버라이딩
    public void backPressed() {
        sceneKeeper.removeScene(this); // 현재 Scene을 제거
        sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 게임 장면으로 돌아간다.
    }

    @Override // handleInput함수(터치 입력) 오버라이딩
    public void handleInput(MotionEvent motionEvent) { // 해당 포인트가 눌린다면 발생
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
            if (backSignFlex.getRect().contains((int) motionEvent.getX(),
                    (int) motionEvent.getY())) { // 뒤로가기 버튼(게임 내)의 범위에 마우스 커서가 들어있다면
                sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
                sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인 메뉴로 돌아간다.
            }
        }
    }

    @Override // handleUpdate함수(터치 입력) 오버라이딩
    public void handleUpdate ( double deltaTime){} // 핸들러 갱신(딜레이만큼 기다렸다가)

    @Override // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint,double alpha){ // 핸들러 렌더링
        //canvas.drawBitmap(titleTextBitmap, titleTextRect, titleTextFlex.getRect(), paint); // 타이틀 텍스트 이미지를 그린다.
        canvas.drawBitmap(IntroductionPanelBitmap, IntroductionPanelRect, IntroductionPanelFlex.getRect(), paint); // 메뉴 패널을 그린다.
        canvas.drawBitmap(backSignBitmap, backSignRect, backSignFlex.getRect(), paint); // 뒤로가기 버튼(게임 내)을 그린다.
    }
}
