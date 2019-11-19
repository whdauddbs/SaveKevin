package wsdfhjxc.taponium.engine;

import android.app.*;
import android.graphics.*;
import android.util.*;

public class FlexConfig { // 디스플레이에 따른 게임화면 크기 설정을 위한 클래스
    private final Point displaySize; // 디스플레이의 사이즈를 저장하는 변수
    private final float scale; //

    public FlexConfig(Activity activity, int baseDisplayWidth) { // FlexConfig의 생성자, 폰의 해상도를 받아와 저장하고, 비율을 설정한다.
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        displaySize = new Point(metrics.widthPixels, metrics.heightPixels);
        scale = (float) displaySize.x / baseDisplayWidth;
    }

    public int getDisplayWidth() {
        return displaySize.x;
    } // 화면너비를 반환

    public int getDisplayHeight() {
        return displaySize.y;
    } // 화면높이를 반환

    public float getScale() {
        return scale;
    } // 원래화면과 기준화면(1080)의 비율을 반환
}