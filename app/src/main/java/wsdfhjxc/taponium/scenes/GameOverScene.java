package wsdfhjxc.taponium.scenes;

import android.content.SharedPreferences;
import android.graphics.*;
import android.util.Log;
import android.view.*;

import java.sql.Time;

import wsdfhjxc.taponium.MainActivity;
import wsdfhjxc.taponium.engine.*;
import wsdfhjxc.taponium.game.*;

public class GameOverScene extends Scene {



    private Bitmap titleTextBitmap; // 타이틀 텍스트 비트맵 객체
    private Rect titleTextRect; // 타이틀 텍스트를 둘러싼 사각형 객체
    private Flex titleTextFlex; // 타이틀 텍스트 flex 객체
    /*
    private Bitmap gameOverTextBitmap; // GameOver 텍스트 비트맵 객체
    private Rect gameOverTextRect; // GameOver 텍스트 비트맵 사각형 객체
    private Flex gameOverTextFlex; // GameOver 텍스트 비트맵 flex 객체
    */
    private Bitmap overPanelBitmap; // GameOver 패널 비트맵 객체
    private Rect overPanelRect; // GameOver 패널 비트맵 사각형 객체
    private Flex overPanelFlex; // GameOver 패널 비트맵 flex 객체

    private Flex againButtonFlex; // 다시하기 버튼 flex 객체
    private Flex menuButtonFlex; // 메뉴로 돌아가기 버튼 flex 객체

    private final long maxScore; // 최고 점수 저장하는 상수(토끼 클릭해도 최고 점수는 유지될 수 있도록 저장)
    private ScoreCounterRenderer scoreCounterRenderer; // 점수 카운터 렌더러 객체


    // touch input will be unlocked after half a second to avoid accidental click
    // 실수로 클릭하는 것을 방지하기 위해서 0.5초 후에 잠금이 해제됨
    // 게임 오버시 게임 오버 장면을 보여주지 못하고 넘어가는 것을 방지하기 위해 설정하는 변수들
    private double unlockTotalDuration = 0.5;
    private double unlockCurrentDuration = 0.0;


    // GameOverScene 생성자 생성
    public GameOverScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper,
                         FlexConfig flexConfig, long maxScore) {
        super(sceneKeeper, resourceKeeper, flexConfig, 3, 1); // Scene 클래스 생성자
        this.maxScore = maxScore;
        //하이스코어 리스트에 최고 점수를 추가해줌
        int difficulty = TimedHandler.levelCheck-1;
        SharedPreferences.Editor editor = EngineRunner.sharedPreferences.edit();
        EngineRunner.highScoreList.addScore(difficulty, maxScore);
        long[][] list = EngineRunner.highScoreList.getStList();
        for(int i = 0; i<3;i++) {
            String key = GameRules.DIFFICULTY[difficulty] + i;
            editor.putLong(key, list[difficulty][3-i]);
        }
        editor.commit();
    }

    @Override // load함수 오버라이딩
    public void load() {
        titleTextBitmap = resourceKeeper.getBitmap("title_text"); // 타이틀 텍스트 이미지 로드
        // 타이틀 텍스트 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        titleTextRect = new Rect(0, 0, titleTextBitmap.getWidth(), titleTextBitmap.getHeight());
        // 타이틀 텍스트 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        titleTextFlex = new Flex(new PointF(0.5f, 0.3f), false,
                new PointF(titleTextBitmap.getWidth(), titleTextBitmap.getHeight()), true,
                new Point(-titleTextBitmap.getWidth() / 2, -titleTextBitmap.getHeight() * 2), flexConfig);

        overPanelBitmap = resourceKeeper.getBitmap("over_panel"); // GameOver 패널 이미지 로드
        // GameOver 패널 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        overPanelRect = new Rect(0, 0, overPanelBitmap.getWidth(), overPanelBitmap.getHeight());
        // GameOver 패널 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        overPanelFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(overPanelBitmap.getWidth(), overPanelBitmap.getHeight()), true,
                new Point(-overPanelBitmap.getWidth() / 2, -overPanelBitmap.getHeight()),
                flexConfig);
/*
        gameOverTextBitmap = resourceKeeper.getBitmap("game_over_text"); // GameOver 텍스트 이미지 로드
        // GameOver 텍스트 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        gameOverTextRect = new Rect(0, 0, gameOverTextBitmap.getWidth(),
                gameOverTextBitmap.getHeight());

        // GameOver 텍스트 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        gameOverTextFlex = new Flex(new PointF(0.5f, 0.25f), false,
                new PointF(gameOverTextBitmap.getWidth(), gameOverTextBitmap.getHeight()), true,
                new Point(-gameOverTextBitmap.getWidth() / 2, -gameOverTextBitmap.getHeight() - 130),
                flexConfig);

 */

        ScoreCounter scoreCounter = new ScoreCounter(); // 점수 카운터 객체 생성
        scoreCounterRenderer = new ScoreCounterRenderer(scoreCounter, resourceKeeper, flexConfig); // 점수 카운터 렌더러 객체 생성

        scoreCounter.add(maxScore); // 현재 최고 점수로 저장된 점수를 scoreCounter 객체에 삽입

        // 다시하기 버튼 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        againButtonFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(700f, 200f), true,
                new Point(-700 / 2, -550), flexConfig);

        // 메인메뉴 버튼 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        menuButtonFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(700f, 200f), true,
                new Point(-700 / 2, -350), flexConfig);
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
        if (unlockCurrentDuration >= unlockTotalDuration) { // 잠금이 해제된 기간 >= 잠금이 해제된 총 기간인 경우
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
                if (againButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 다시하기 버튼의 범위에 마우스 커서가 들어있다면
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거
                    if(TimedHandler.levelCheck != 4)
                        sceneKeeper.addScene(new GameScene(sceneKeeper, resourceKeeper, flexConfig)); // Game Scene으로 돌아간다.
                    else
                        sceneKeeper.addScene(new TimeAttackGameScene(sceneKeeper, resourceKeeper, flexConfig)); // Game Scene으로 돌아간다.
                } else if (menuButtonFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 메인메뉴 버튼의 범위에 마우스 커서가 들어있다면
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
                    sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인 메뉴로 돌아간다.
                }
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
        canvas.drawBitmap(titleTextBitmap, titleTextRect, titleTextFlex.getRect(), paint); // 타이틀 텍스트 이미지를 그린다.
        //canvas.drawBitmap(gameOverTextBitmap, gameOverTextRect, gameOverTextFlex.getRect(), paint); // GameOver 텍스트 이미지를 그린다.
        canvas.drawBitmap(overPanelBitmap, overPanelRect, overPanelFlex.getRect(), paint); // GameOver 패널 이미지를 그린다.
        scoreCounterRenderer.render(canvas, paint, true); // 점수 카운터를 그린다.
    }
}
