package wsdfhjxc.taponium.game;

import android.graphics.*;

import wsdfhjxc.taponium.engine.*;

public class ScoreCounterRenderer { //기록된 점수를 가져오는 클래스
    private final ScoreCounter scoreCounter;
    //점수판 이미지 설정
    private final Typeface typeface;
    private final Flex scoreTextFlex;
    private final int colorMaxScore = Color.argb(255, 255, 102, 0);
    private final int colorBelowMaxScore = Color.argb(255, 128, 128, 128);

    //기록된 점수를 가져오는 클래스
    public ScoreCounterRenderer(ScoreCounter scoreCounter, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        this.scoreCounter = scoreCounter;
        typeface = resourceKeeper.getTypeface("IndieFlower");
        scoreTextFlex = new Flex(new PointF(0.5f, 0.25f), false,
                                 new PointF(0f, 180f), true,
                                 new Point(), flexConfig);
    }
    //색깔 및 사이즈, 비율 등 점수판 설정
    public void render(Canvas canvas, Paint paint) {
        int color = scoreCounter.isBelowMax() ? colorBelowMaxScore : colorMaxScore;
        paint.setColor(color);
        paint.setTextSize(scoreTextFlex.getSize().y);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);
        canvas.drawText(String.valueOf(scoreCounter.getCurrent()),
                        scoreTextFlex.getPosition().x, scoreTextFlex.getPosition().y, paint);
    }
}
