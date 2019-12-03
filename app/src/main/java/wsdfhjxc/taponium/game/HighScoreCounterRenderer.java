package wsdfhjxc.taponium.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;

import java.util.List;

import wsdfhjxc.taponium.engine.Flex;
import wsdfhjxc.taponium.engine.FlexConfig;
import wsdfhjxc.taponium.engine.ResourceKeeper;

public class HighScoreCounterRenderer { //기록된 점수를 가져오는 클래스
    private final long scoreList[];
    //private final long st1;
    //private final long st2;
    //점수판 이미지 설정
    private final Typeface typeface;
    private final Flex scoreTextFlex;
    private final FlexConfig flexConfig;

    private final int colorMaxScore = Color.argb(255, 255, 102, 0);
    private final int colorBelowMaxScore = Color.argb(255, 128, 128, 128);
    private final int colorGameOver = Color.argb(255, 56, 104, 48);


    //HighScoreCounterRenderer highScoreCounterRenderer = new HighScoreCounterRenderer();

    //기록된 점수를 가져오는 클래스
  //  public HighScoreCounterRenderer(long st1,long st2, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
    public HighScoreCounterRenderer(long scoreList[], ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        //this.st1 = st1;
        //this.st2 = st2;

        this.scoreList = scoreList;
        typeface = resourceKeeper.getTypeface("LongHair");
        scoreTextFlex = new Flex(new PointF(0.5f, 0.25f), false,
                new PointF(0f, 180f), true,
                new Point(), flexConfig);

        this.flexConfig = flexConfig;
    }
    //색깔 및 사이즈, 비율 등 점수판 설정
    public void render(Canvas canvas, Paint paint) {
        //int color = scoreCounter.isBelowMax() ? colorBelowMaxScore : colorMaxScore;
        Flex textFlex3 = new Flex(new PointF(0.5f, 1f), false,
                new PointF(0f, 140f), true,
                new Point(0,-200), flexConfig);

        Flex textFlex2 = new Flex(new PointF(0.5f, 1f), false,
                new PointF(0f, 140f), true,
                new Point(0,-400), flexConfig);

        Flex textFlex1 = new Flex(new PointF(0.5f, 1f), false,
                new PointF(0f, 140f), true,
                new Point(0,-600), flexConfig);

        Flex textFlex0 = new Flex(new PointF(0.5f, 1f), false,
                new PointF(0f, 140f), true,
                new Point(0,-800), flexConfig);



        paint.setColor(colorGameOver);
        paint.setTextSize(scoreTextFlex.getSize().y);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);

        canvas.drawText(String.valueOf(scoreList[1]),
                textFlex3.getPosition().x, textFlex3.getPosition().y, paint);
        canvas.drawText(String.valueOf(scoreList[2]),
                textFlex2.getPosition().x, textFlex2.getPosition().y, paint);
        canvas.drawText(String.valueOf(scoreList[3]),
                textFlex1.getPosition().x, textFlex1.getPosition().y, paint);
        canvas.drawText(String.valueOf(scoreList[4]),
                textFlex0.getPosition().x, textFlex0.getPosition().y, paint);


    }
}
