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
import wsdfhjxc.taponium.engine.TimedHandler;

public class SelectDifficultyScene extends Scene {

    private Bitmap titleTextBitmap; // 타이틀 텍스트 비트맵 객체
    private Rect titleTextRect; // 타이틀 텍스트를 둘러싼 사각형 객체
    private Flex titleTextFlex; // 타이틀 텍스트 flex 객체

    private Bitmap difficultyPanelBitmap; // 메뉴 패널 비트맵 객체
    private Rect difficultyPanelRect; // 메뉴 패널을 둘러싼 사각형 객체
    private Flex difficultyPanelFlex; // 메뉴 패널 flex 객체

    private Flex easyButtonFlex;      // 쉬움 버튼 flex 객체
    private Flex normalButtonFlex;   //중간 버튼 flex 객체
    private Flex hardButtonFlex;     //하드 버튼 flex 객체
    private Flex timeAttackButtonFlex;     //타임어택 버튼 flex 객체

    private double unlockTotalDuration = 0.5;
    private double unlockCurrentDuration = 0.0;

    // 메인 메뉴 Scene 생성자
    public SelectDifficultyScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 5, 1); // Scene 클래스 생성자
    }

    @Override
    public void load() {
        titleTextBitmap = resourceKeeper.getBitmap("title_text"); // 타이틀 텍스트 이미지 로드
        // 타이틀 텍스트 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        titleTextRect = new Rect(0, 0, titleTextBitmap.getWidth(), titleTextBitmap.getHeight());
        // 타이틀 텍스트 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        titleTextFlex = new Flex(new PointF(0.5f, 0.3f), false,
                new PointF(titleTextBitmap.getWidth(), titleTextBitmap.getHeight()), true,
                new Point(-titleTextBitmap.getWidth() / 2, -titleTextBitmap.getHeight() * 2), flexConfig);

        difficultyPanelBitmap = resourceKeeper.getBitmap("difficulty_panel"); // 타이틀 패널 이미지 로드
        // 타이틀 패널 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        difficultyPanelRect = new Rect(0, 0, difficultyPanelBitmap.getWidth(), difficultyPanelBitmap.getHeight());
        // 타이틀 패널 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        difficultyPanelFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(difficultyPanelBitmap.getWidth(), difficultyPanelBitmap.getHeight()), true,
                new Point(-difficultyPanelBitmap.getWidth() / 2, -difficultyPanelBitmap.getHeight()),
                flexConfig);

        easyButtonFlex = new Flex(new PointF(0.5f, 1f), false, // 이지 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -950), flexConfig);

        normalButtonFlex = new Flex(new PointF(0.5f, 1f), false, // 노말 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -750), flexConfig);

        hardButtonFlex = new Flex(new PointF(0.5f, 1f), false, // 하드 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -540), flexConfig);

        timeAttackButtonFlex = new Flex(new PointF(0.5f, 1f), false, // 하드 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -320), flexConfig);

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
        if (unlockCurrentDuration >= unlockTotalDuration) { // 잠금이 해제된 기간 >= 잠금이 해제된 총 기간인 경우
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
                if (easyButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                    TimedHandler.levelCheck = 1;
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거
                    sceneKeeper.addScene(new GameScene(sceneKeeper, resourceKeeper, flexConfig)); // 게임 장면으로 돌아간다.
                } else if (normalButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                    TimedHandler.levelCheck = 2;
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거
                    sceneKeeper.addScene(new GameScene(sceneKeeper, resourceKeeper, flexConfig)); // 소개 장면으로 돌아간다.
                } else if (hardButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                    TimedHandler.levelCheck = 3;
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거
                    sceneKeeper.addScene(new GameScene(sceneKeeper, resourceKeeper, flexConfig)); // 소개 장면으로 돌아간다.
                } else if (timeAttackButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                    TimedHandler.levelCheck = 4;
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거
                    sceneKeeper.addScene(new TimeAttackGameScene(sceneKeeper, resourceKeeper, flexConfig)); // 소개 장면으로 돌아간다.
                }
            }
        }
    }

    @Override // handleUpdate함수(터치 입력) 오버라이딩
    public void handleUpdate ( double deltaTime){
        if (unlockCurrentDuration < unlockTotalDuration) {
            unlockCurrentDuration += deltaTime; // 현재 잠금이 해제된 기간에 전 프레임과 현재 프레임사이의 시간을 더해준다.
        }
    } // 핸들러 갱신(딜레이만큼 기다렸다가)

    @Override // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint,double alpha){ // 핸들러 렌더링
        canvas.drawBitmap(titleTextBitmap, titleTextRect, titleTextFlex.getRect(), paint); // 타이틀 텍스트 이미지를 그린다.
        canvas.drawBitmap(difficultyPanelBitmap, difficultyPanelRect, difficultyPanelFlex.getRect(), paint); // 메뉴 패널을 그린다.
    }
}
