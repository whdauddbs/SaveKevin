package wsdfhjxc.taponium.scenes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;

import wsdfhjxc.taponium.MainActivity;
import wsdfhjxc.taponium.engine.Flex;
import wsdfhjxc.taponium.engine.FlexConfig;
import wsdfhjxc.taponium.engine.ResourceKeeper;
import wsdfhjxc.taponium.engine.Scene;
import wsdfhjxc.taponium.engine.SceneKeeper;

public class MainMenuScene extends Scene {
    private Bitmap titleTextBitmap; // 타이틀 텍스트 비트맵 객체
    private Rect titleTextRect; // 타이틀 텍스트를 둘러싼 사각형 객체
    private Flex titleTextFlex; // 타이틀 텍스트 flex 객체

    private Bitmap menuPanelBitmap; // 메뉴 패널 비트맵 객체
    private Rect menuPanelRect; // 메뉴 패널을 둘러싼 사각형 객체
    private Flex menuPanelFlex; // 메뉴 패널 flex 객체

    //
    private Bitmap speakerBitmap; // 스피커 이미지 비트맵 객체
    private Rect speakerRect; // 스피커 이미지를 둘러싼 사각형 객체
    private Flex speakerFlex; // 스피커 이미지 flex 객체

    private Flex playButtonFlex; // play 버튼 flex 객체
    private Flex quitButtonFlex; // 종료 버튼 flex 객체
    private Flex introductionButtonFlex;
    private Flex highScoreButtonFlex; // 하이스코어 버튼 flex 객체

    private boolean isClicked = false;

    // 메인 메뉴 Scene 생성자
    public MainMenuScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 1, 1); // Scene 클래스 생성자
    }

    @Override
    public void load() {

        // 타이틀 패널 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정

        titleTextBitmap = resourceKeeper.getBitmap("title_text"); // 타이틀 텍스트 이미지 로드
        // 타이틀 텍스트 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        titleTextRect = new Rect(0, 0, titleTextBitmap.getWidth(), titleTextBitmap.getHeight());
        // 타이틀 텍스트 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        titleTextFlex = new Flex(new PointF(0.5f, 0.3f), false,
                new PointF(titleTextBitmap.getWidth(), titleTextBitmap.getHeight()), true,
                new Point(-titleTextBitmap.getWidth() / 2, -titleTextBitmap.getHeight() * 2), flexConfig);

        menuPanelBitmap = resourceKeeper.getBitmap("menu_panel"); // 타이틀 패널 이미지 로드
        // 타이틀 패널 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        menuPanelRect = new Rect(0, 0, menuPanelBitmap.getWidth(), menuPanelBitmap.getHeight());
        // 타이틀 패널 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        menuPanelFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(menuPanelBitmap.getWidth(), menuPanelBitmap.getHeight()), true,
                new Point(-menuPanelBitmap.getWidth() / 2, -menuPanelBitmap.getHeight()),
                flexConfig);

        speakerBitmap = resourceKeeper.getBitmap("speaker_on"); // 타이틀 패널 이미지 로드
        // 스피커 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        speakerRect = new Rect(0, 0, speakerBitmap.getWidth(), speakerBitmap.getHeight());
        // 스피커 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        speakerFlex = new Flex(new PointF(0.1f, 0.45f), false,
                new PointF(speakerBitmap.getWidth(), speakerBitmap.getHeight()), true,
                new Point(-speakerBitmap.getWidth() / 2, -speakerBitmap.getHeight()*4),
                flexConfig);


        playButtonFlex = new Flex(new PointF(0.5f, 1f), false, // play 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -950), flexConfig);

        introductionButtonFlex = new Flex(new PointF(0.5f, 1f), false, // quit 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -750), flexConfig);

        highScoreButtonFlex = new Flex(new PointF(0.5f, 1f), false, // quit 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -540), flexConfig);

        // 종료 버튼 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        quitButtonFlex = new Flex(new PointF(0.5f, 1f), false, // quit 버튼 좌표 설정
                new PointF(700f, 170f), true,
                new Point(-700 / 2, -320), flexConfig);

    }

    @Override // unload함수(잠금해제) 오버라이딩
    public void unload() {}

    @Override // backPressed함수 오버라이딩
    public void backPressed() {
        sceneKeeper.removeAllScenes(); // 뒤로가기 버튼이 눌리면 게임이 종료되며 모든 Scene을 제거한다.
    }

    @Override // handleInput함수(터치 입력) 오버라이딩
    public void handleInput(MotionEvent motionEvent) { // 해당 포인트가 눌린다면 발생
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
            if (playButtonFlex.getRect().contains((int) motionEvent.getX(),
                    (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                sceneKeeper.removeScene(this); // 현재 Scene을 제거
                sceneKeeper.addScene(new SelectDifficultyScene(sceneKeeper, resourceKeeper, flexConfig)); // 게임 장면으로 돌아간다.
            } else if(introductionButtonFlex.getRect().contains((int) motionEvent.getX(),
                    (int) motionEvent.getY())) { // 시작 버튼의 범위에 마우스 커서가 들어있다면
                sceneKeeper.removeScene(this); // 현재 Scene을 제거
                sceneKeeper.addScene(new IntroductionScene(sceneKeeper, resourceKeeper, flexConfig)); // 소개 장면으로 돌아간다.
            }  else if (highScoreButtonFlex.getRect().contains((int) motionEvent.getX(), // 하이스코어 버튼의 범위에 마우스 커서가 들어있다면
                    (int) motionEvent.getY())) {
                sceneKeeper.removeScene(this); // 게임이 종료되며 모든 Scene을 제거한다.
                sceneKeeper.addScene(new HighScoreScene(sceneKeeper, resourceKeeper, flexConfig));
            } else if (quitButtonFlex.getRect().contains((int) motionEvent.getX(), // 종료 버튼의 범위에 마우스 커서가 들어있다면
                    (int) motionEvent.getY())) {
                sceneKeeper.removeAllScenes(); // 게임이 종료되며 모든 Scene을 제거한다.
            }
            //스피커 버튼을 클릭했을때 함수
            else if (speakerFlex.getRect().contains((int) motionEvent.getX(), // 종료 버튼의 범위에 마우스 커서가 들어있다면
                    (int) motionEvent.getY()) && isClicked == true) {
                //musicSwitch가 true면 메인엑티비티 클래스의 미디어플레이어객체를 정지시키도록
                if (MainActivity.musicSwitch == true) {
                    speakerBitmap = resourceKeeper.getBitmap("speaker_off");
                    MainActivity.mediaPlayer.pause();
                    MainActivity.musicSwitch = false;
                } else {//musicSwitch가 false면 메인엑티비티 클래스의 미디어플레이어객체를 다시 재생시키도록
                    speakerBitmap = resourceKeeper.getBitmap("speaker_on");
                    MainActivity.mediaPlayer.start();
                    MainActivity.musicSwitch = true;
                }
                isClicked = false;
            }
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            isClicked = true;
        }
    }

    @Override // handleUpdate함수(터치 입력) 오버라이딩
    public void handleUpdate ( double deltaTime){} // 핸들러 갱신(딜레이만큼 기다렸다가)

    @Override // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint,double alpha){ // 핸들러 렌더링
        canvas.drawBitmap(titleTextBitmap, titleTextRect, titleTextFlex.getRect(), paint); // 타이틀 텍스트 이미지를 그린다.
        canvas.drawBitmap(menuPanelBitmap, menuPanelRect, menuPanelFlex.getRect(), paint); // 메뉴 패널을 그린다.
        canvas.drawBitmap(speakerBitmap, speakerRect, speakerFlex.getRect(), paint); //스피커 이미지를 그린다.
    }
}
