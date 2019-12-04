package wsdfhjxc.taponium.game;

import android.graphics.*;

import wsdfhjxc.taponium.engine.*;

public class TimeCounterRenderer { // 남은 시간 표시해주는 클래스
    private final TimeCounter timeCounter;
    // 점수판 이미지 설정
    private final Typeface typeface;
    private final Flex timeTextFlex;
    private final FlexConfig flexConfig;

    public TimeCounterRenderer(TimeCounter timeCounter, ResourceKeeper resourceKeeper, FlexConfig flexConfig) {
        this.timeCounter = timeCounter;
        typeface = resourceKeeper.getTypeface("LongHair");
        timeTextFlex = new Flex(new PointF(0.1f, 0.2f), false,
                new PointF(0f, 90f), true,
                new Point(0, -45), flexConfig);
        this.flexConfig = flexConfig;
    }
    //색깔 및 사이즈, 비율 등 점수판 설정
    public void render(Canvas canvas, Paint paint) {
        paint.setTextSize(timeTextFlex.getSize().y);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(typeface);
        canvas.drawText(String.valueOf(timeCounter.getCurrent()),
                timeTextFlex.getPosition().x, timeTextFlex.getPosition().y, paint);
    }
}
