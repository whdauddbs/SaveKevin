package wsdfhjxc.taponium.scenes;

import android.graphics.*;
import android.util.Log;
import android.view.*;

import wsdfhjxc.taponium.engine.*;
import wsdfhjxc.taponium.game.*;

public class GameScene extends Scene {
    private ScoreCounter scoreCounter; // 점수 카운터 객체
    private ScoreCounterRenderer scoreCounterRenderer; // 점수 카운터 렌더러 객체

    private Bitmap currentScoreTextBitmap; // 현재 점수 텍스트 비트맵 객체
    private Rect currentScoreTextRect; // 현재 점수 텍스트를 둘러싼 사각형 객체
    private Flex currentScoreTextFlex; // 현재 점수 텍스트 flex 객체

    private Board board; // 게임 보드 객체
    private BoardRenderer boardRenderer; // 게임 보드 렌더러 객체

    private Flex boardAreaFlex; // 게임 보드 범위 flex 객체
    private Flex boardSlotFlex; // 게임 보드 슬롯 flex 객체
    private Flex boardSlotSpacerFlex; // 게임 보드 공간 flex 객체

    private Bitmap backSignBitmap; // 뒤로가기 버튼(게임 내) 비트맵 객체
    private Rect backSignRect; // 뒤로가기 버튼(게임 내) 비트맵을 둘러싼 사각형 객체
    private Flex backSignFlex; // 뒤로가기 버튼(게임 내) 비트맵 flex 객체

    private Bitmap pauseBitmap; // 일시정지 버튼(게임 내) 비트맵 객체
    private Rect pauseRect; // 일시정지 버튼(게임 내) 비트맵을 둘러싼 사각형 객체
    private Flex pauseFlex; // 일시정지 버튼(게임 내) 비트맵 flex 객체

    private  boolean isClicked = false;

    public GameScene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        super(sceneKeeper, resourceKeeper, flexConfig, 2, 1); // Scene 클래스 생성자
    }

    @Override
    public void load() {

        // 스코어 카운터 객체 생성
        scoreCounter = new ScoreCounter();
        // 스코어 카운터 렌더러 객체 생성
        scoreCounterRenderer = new ScoreCounterRenderer(scoreCounter, resourceKeeper, flexConfig);


        board = new Board(scoreCounter); // 보드에 스코어 카운터가 들어간 보드 객체 생성
        // 보드 위치를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        boardAreaFlex = new Flex(new PointF(0.5f, 1f), false,
                new PointF(814f, 714f), true,
                new Point(-814 / 2, -993), flexConfig);
        // // 보드 슬롯의 위치를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        boardSlotFlex = new Flex(new PointF(0f, 0f), true,
                new PointF(183f, 156f), true,
                new Point(), flexConfig);
        // // 보드 공간의 위치를 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        boardSlotSpacerFlex = new Flex(new PointF(0f, 0f), true,
                new PointF(135f, 125f), true,
                new Point(), flexConfig);
        // 보드 렌더러 객체 생성
        boardRenderer = new BoardRenderer(board, resourceKeeper, flexConfig,
                boardAreaFlex, boardSlotFlex, boardSlotSpacerFlex);


        // 현재 스코어 이미지 로드
        currentScoreTextBitmap = resourceKeeper.getBitmap("current_score_text");
        // 현재 스코어 이미지의 너비와 높이만큼의 둘러싼 사각형 설정
        currentScoreTextRect = new Rect(0, 0, currentScoreTextBitmap.getWidth(),
                currentScoreTextBitmap.getHeight());
        // 현재 점수 텍스트 위치 객체 생성
        currentScoreTextFlex = new Flex(new PointF(0.5f, 0.18f), false,
                new PointF(currentScoreTextBitmap.getWidth(),
                        currentScoreTextBitmap.getHeight()), true,
                new Point(-currentScoreTextBitmap.getWidth() / 2,
                        -currentScoreTextBitmap.getHeight() - 50),
                flexConfig);

        backSignBitmap = resourceKeeper.getBitmap("back_sign"); // 뒤로가기 버튼 객체 이미지 객체 생성

        // 뒤로가기 버튼 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        backSignRect = new Rect(0, 0, backSignBitmap.getWidth(), backSignBitmap.getHeight());

        // 뒤로가기 버튼을 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        backSignFlex = new Flex(new PointF(1f, 0f), false,
                new PointF(backSignBitmap.getWidth(), backSignBitmap.getHeight()), true,
                new Point(-backSignBitmap.getWidth() - 20, 20),
                flexConfig);
        pauseBitmap = resourceKeeper.getBitmap("pause"); // 뒤로가기 버튼 객체 이미지 객체 생성

        // 뒤로가기 버튼 이미지를 둘러싼 사각형을 너비와 높이만큼 설정
        pauseRect = new Rect(0, 0, pauseBitmap.getWidth(), pauseBitmap.getHeight());

        // 뒤로가기 버튼을 스마트폰 크기에 따라 비트맵의 실제 크기와 위치를 조정
        pauseFlex = new Flex(new PointF(0f, 0f), false,
                new PointF(0.15f, 0.1f), false,
                new Point(0, 0),
                flexConfig);
    }

    @Override // unload함수(잠금해제) 오버라이딩
    public void unload() {}

    @Override // backPressed함수 오버라이딩
    // 뒤로가기 버튼이 눌리면 현재 Scene을 제거하고 메인메뉴 Scene 추가(뒤로가기 버튼이 눌리면 메인메뉴 장면으로 이동한다
    public void backPressed() {
        sceneKeeper.removeScene(this); // 현재 Scene 제거
        sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인메뉴 Scene 추가
    }

    private void handleBoardAreaInput(MotionEvent motionEvent) { // handleBoardAreaInput함수(보드 내 터치 입력)
        //Todo : 누르는 위치에 따른 이미지 변경
        int relativeX = (int)motionEvent.getX() - boardAreaFlex.getPosition().x; // 보드내 입력된 상대적 위치 좌표 x
        int relativeY = (int)motionEvent.getY() - boardAreaFlex.getPosition().y; // 보드내 입력된 상대적 위치 좌표 y

        int slotX = relativeX / (boardSlotFlex.getSize().x + boardSlotSpacerFlex.getSize().x); // 보드 슬롯내 입력된 위치 좌표 x

        int slotY = relativeY / (boardSlotFlex.getSize().y + boardSlotSpacerFlex.getSize().y); // 보드 슬롯내 입력된 위치 좌표 y

        Slot slot = board.getSlot(slotX, slotY);
        // 해당 슬롯을 클릭했을 때 햄스터라면 죽은 햄스터로 변경 후 점수가 증가
        if (slot.getContentType() == SlotContentType.HAMSTER) {
            slot.setContentType(SlotContentType.DEAD_HAMSTER);
            slot.scaleDuration(GameRules.TAPPED_CONTENT_DURATION_SCALING_FACTOR);
            //스코어카운터 객체의 max스토어에 변화를줌
            scoreCounter.add(GameRules.HAMSTER_CONTENT_TAPPED_POINTS);
        }
        // 해당 슬롯을 클릭했을 때 토끼라면 죽은 토끼로 변경 후 점수 감소
        else if (slot.getContentType() == SlotContentType.BUNNY) {
            slot.setContentType(SlotContentType.DEAD_BUNNY);
            slot.scaleDuration(GameRules.TAPPED_CONTENT_DURATION_SCALING_FACTOR);
            scoreCounter.add(GameRules.BUNNY_CONTENT_TAPPED_POINTS);
        }
    }

    @Override // handleInput함수(터치 입력) 오버라이딩
    public void handleInput(MotionEvent motionEvent) {
        Log.d("click", Integer.toString(motionEvent.getAction()));
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            isClicked = true;
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) { // 버튼 누른 손가락을 땟을 경우
            if(UpdateHandler.isPause == false) {
                if (boardAreaFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 보드 내의 범위에 마우스 커서가 들어있다면
                    handleBoardAreaInput(motionEvent); // handleBoardAreaInput함수를 실행
                } else if (backSignFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 뒤로가기 버튼(게임 내)의 범위에 마우스 커서가 들어있다면
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
                    sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인 메뉴로 돌아간다.
                } else if (pauseFlex.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY()) && isClicked) {
                    UpdateHandler.isPause = true;
                    isClicked = false;
                }
            }
            else{
                if (pauseFlex.getRect().contains((int) motionEvent.getX(), (int) motionEvent.getY()) && isClicked) {
                    UpdateHandler.isPause = false;
                    isClicked = false;
                    Log.d("pause", "handleInput: " + UpdateHandler.isPause);
                }
                else if (backSignFlex.getRect().contains((int) motionEvent.getX(),
                        (int) motionEvent.getY())) { // 뒤로가기 버튼(게임 내)의 범위에 마우스 커서가 들어있다면
                    UpdateHandler.isPause = false;
                    sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
                    sceneKeeper.addScene(new MainMenuScene(sceneKeeper, resourceKeeper, flexConfig)); // 메인 메뉴로 돌아간다.
                }
            }
        }
    }

    @Override
    public void handleUpdate(double deltaTime) { // handleUpdate함수(터치 입력) 오버라이딩

        if(TimedHandler.levelCheck==1){
            deltaTime = deltaTime*1;
        }else if(TimedHandler.levelCheck==2){
            deltaTime = deltaTime*2;
        }else{
            deltaTime = deltaTime*3;
        }
        board.update(deltaTime); // 매초마다 보드를 갱신한다.

        //gameover씬으로 이동하는 구간
        if (scoreCounter.isNegative()) { // 스코어 카운터가 0보다 작다면
            sceneKeeper.removeScene(this); // 현재 Scene을 제거하고
            sceneKeeper.addScene(new GameOverScene(sceneKeeper, resourceKeeper, flexConfig,
                    scoreCounter.getMax())); // GameOver 장면으로 이동한다.
        }
    }

    @Override  // hendleRender함수(터치시 갱신한 화면 렌더링) 오버라이딩
    public void handleRender(Canvas canvas, Paint paint, double alpha) {
        boardRenderer.render(canvas, paint, alpha); // 게임 보드를 그린다.
        scoreCounterRenderer.render(canvas, paint, false); // 스코어 카운터를 그린다.

        canvas.drawBitmap(currentScoreTextBitmap, currentScoreTextRect,
                currentScoreTextFlex.getRect(), paint); // 현재 스코어 텍스트를 그린다.

        canvas.drawBitmap(backSignBitmap, backSignRect, backSignFlex.getRect(), paint); // 뒤로가기 버튼(게임 내)을 그린다.
        canvas.drawBitmap(pauseBitmap, pauseRect, pauseFlex.getRect(), paint); // 뒤로가기 버튼(게임 내)을 그린다.
    }
}
