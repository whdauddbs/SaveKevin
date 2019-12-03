package wsdfhjxc.taponium.game;

import android.graphics.*;

import wsdfhjxc.taponium.engine.*;

public class ScoreCounterRenderer { //기록된 점수를 가져오는 클래스
    private final ScoreCounter scoreCounter;
    //점수판 이미지 설정
    private final Typeface typeface;
    private final Flex scoreTextFlex;
    private final FlexConfig flexConfig;
    private final int colorMaxScore = Color.argb(255, 227, 243, 227);
    private final int colorBelowMaxScore = Color.argb(255, 127, 143, 127);
    private final int colorGameOver = Color.argb(255, 56, 104, 48);

    public HighScoreList highScoreList;

    //기록된 점수를 가져오는 클래스
    public ScoreCounterRenderer(ScoreCounter scoreCounter, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        this.scoreCounter = scoreCounter;
        typeface = resourceKeeper.getTypeface("LongHair");
        scoreTextFlex = new Flex(new PointF(0.5f, 0.15f), false,
                new PointF(0f, 140f), true,
                new Point(), flexConfig);
        this.flexConfig = flexConfig;
    }

    //색깔 및 사이즈, 비율 등 점수판 설정
    public void render(Canvas canvas, Paint paint, boolean isGameOver) {
        paint.setTextSize(scoreTextFlex.getSize().y);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);
        if(!isGameOver) {
            int color = scoreCounter.isBelowMax() ? colorBelowMaxScore : colorMaxScore;
            paint.setColor(color);
            canvas.drawText(String.valueOf(scoreCounter.getCurrent()),
                    scoreTextFlex.getPosition().x, scoreTextFlex.getPosition().y, paint);
        }
        else{
            Flex textFlex = new Flex(new PointF(0.5f, 1f), false,
                    new PointF(0f, 140f), true,
                    new Point(0,-600), flexConfig);
            paint.setColor(colorGameOver);
            canvas.drawText(String.valueOf(scoreCounter.getCurrent()),
                    textFlex.getPosition().x, textFlex.getPosition().y, paint);
        }
    }
}
