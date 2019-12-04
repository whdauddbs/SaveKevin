package wsdfhjxc.taponium.scenes;

import android.graphics.*;
import android.view.*;

import wsdfhjxc.taponium.engine.*;

public class DefaultScene extends Scene {
    private final RenderHandler renderHandler;   // 렌더링 핸들러 객체

    private Bitmap backgroundBitmap;   // 배경 비트맵 객체 생성
    private Rect backgroundBitmapRect;   // 배경 비트맵 사각형 객체
    private Flex backgroundBitmapFlex;   // 배경 비트맵 flex 객체

    private int fpsCounterFontColor;    // fps 카운터 폰트 색 변수
    private int fpsCounterFontSize;    // fps 카운터 폰트 flex 변수


    // DefaultScene 생성자{
    public DefaultScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper,
                        RenderHandler renderHandler, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 0, 0); // Scene 클래스 생성자
        this.renderHandler = renderHandler;
    }

    @Override
    public void load() {
        // load all game required resources here
        //
        //resourceKeeper.loadBitmap("speaker");
        //
        resourceKeeper.loadBitmap("background"); // 배경
        resourceKeeper.loadBitmap("title_text"); // 타이틀 폰트
        resourceKeeper.loadBitmap("menu_panel"); // 메뉴 패널
        resourceKeeper.loadBitmap("speaker_on"); // 스피커 패널
        resourceKeeper.loadBitmap("speaker_off"); // 스피커 패널
        resourceKeeper.loadBitmap("board_panel"); // 보드 패널
        resourceKeeper.loadBitmap("hamster"); // 햄스터 이미지
        resourceKeeper.loadBitmap("bunny"); // 토끼 이미지
        resourceKeeper.loadBitmap("dead_hamster"); // 죽은 햄스터 이미지
        resourceKeeper.loadBitmap("dead_bunny"); // 죽은 토끼 이미지
        resourceKeeper.loadBitmap("over_panel"); // 게임 over 패널 이미지
        resourceKeeper.loadBitmap("current_score_text"); // 현재 점수 폰트
        //resourceKeeper.loadBitmap("game_over_text"); // 게임 오버 폰트
        resourceKeeper.loadBitmap("back_sign"); // 뒤로가기 버튼
        resourceKeeper.loadBitmap("pause"); // 일시정지 버튼
        resourceKeeper.loadBitmap("watch"); // 시계 아이콘
        resourceKeeper.loadBitmap("introduction_panel"); // 게임소개 패널
        resourceKeeper.loadBitmap("difficulty_panel"); // 난이도 선택 패널
        resourceKeeper.loadBitmap("highscore_panel"); // 난이도 선택 패널
        resourceKeeper.loadTypeface("LongHair", "TTF"); // 폰트는 indieFlower

        // get background image
        // 배경 이미지 불러오기
        backgroundBitmap = resourceKeeper.getBitmap("background"); // 배경이미지 로드
        backgroundBitmapRect = new Rect(0, 0, backgroundBitmap.getWidth(),
                                              backgroundBitmap.getHeight());
        // 배경 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
// 배경 이미지를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        backgroundBitmapFlex = new Flex(new PointF(0f, 0f), false,
                new PointF(1f, 1f), false,
                new Point(), flexConfig);

        fpsCounterFontColor = Color.BLACK; // fps카운터 폰트를 검은색으로 설정
        fpsCounterFontSize = 14; // fps카운터 폰트 크기를 14로 설정

        // add MainMenuScene scene after loading this
        // 메인메뉴 Scene에 추가
        sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig));
    }

    @Override // unload함수(잠금해제) 오버라이딩
    public void unload() {}

    @Override // backPressed함수(뒤로가기 버튼) 오버라이딩
    public void backPressed() {}

    @Override // handleInput함수(터치 입력) 오버라이딩
    public void handleInput(MotionEvent motionEvent) {}

    @Override // handleUpdate함수(터치시 갱신) 오버라이딩
    public void handleUpdate(double deltaTime) {}

    @Override // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint, double alpha) {
        canvas.drawBitmap(backgroundBitmap, backgroundBitmapRect,
                backgroundBitmapFlex.getRect(), paint); // 배경을 그린다.

        // 왼쪽 상단에 FPS 카운터 그리는 코드
        /*
        // draw an FPS counter in the top left corner
        paint.setColor(fpsCounterFontColor); // 폰트는 검은색으로
        paint.setTextSize(fpsCounterFontSize); // 폰트 크기는 14로
        paint.setTextAlign(Paint.Align.LEFT); // 텍스트를 왼쪽 정렬
        paint.setTypeface(null); // disable any previously set custom font
        canvas.drawText("FPS: " + Math.round(renderHandler.getFPS()), 2, fpsCounterFontSize, paint);
        */
    }
}