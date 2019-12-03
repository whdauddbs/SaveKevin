package wsdfhjxc.taponium.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.Log;

import java.util.List;

import wsdfhjxc.taponium.engine.Flex;
import wsdfhjxc.taponium.engine.FlexConfig;
import wsdfhjxc.taponium.engine.ResourceKeeper;

public class HighScoreCounterRenderer { //기록된 점수를 가져오는 클래스
    private final long scoreList[][];
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
    public HighScoreCounterRenderer(long scoreList[][], ResourceKeeper resourceKeeper, FlexConfig flexConfig) {

        Log.d("HighScoreRenderer", "load: " + scoreList[0][3]);
        this.scoreList = scoreList;
        typeface = resourceKeeper.getTypeface("LongHair");
        scoreTextFlex = new Flex(new PointF(0.5f, 0.25f), false,
                new PointF(0f, 180f), true,
                new Point(), flexConfig);

        this.flexConfig = flexConfig;
    }
    //색깔 및 사이즈, 비율 등 점수판 설정
    public void render(Canvas canvas, Paint paint) {
        Flex[][] textFlex = new Flex[4][3];
        for(int i = 0;i<4;i++){
            for(int j = 0;j<3; j++){
                textFlex[i][j] = new Flex(new PointF(0.5f, 0.1f + 0.25f * i), false,
                        new PointF(0f, 50f), true,
                        new Point(0, 60 * j), flexConfig);
            }
        }

        paint.setColor(colorGameOver);
        paint.setTextSize(textFlex[0][0].getSize().y);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);

        for(int i = 0;i<4;i++){
            for(int j = 0;j<3; j++){
                canvas.drawText(String.valueOf(scoreList[i][3-j]),
                        textFlex[i][j].getPosition().x, textFlex[i][j].getPosition().y, paint);
            }
        }
    }
}
