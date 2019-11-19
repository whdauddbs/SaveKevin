package wsdfhjxc.taponium.engine;

import android.graphics.*;

public class Flex { // 스마트폰 크기에 따라 출력하는 비트맵의 크기를 조정하기위한 클래스
    private final FlexConfig flexConfig; // 디스플레이 및 비율을 저장하는 변수
    private final PointF position; // 그림을 출력할 위치
    private final PointF size; // 그림을 출력할 크기
    private final Point positionOffset; // 그림을 출력할 위치의 오프셋
    private final boolean absolutePosition; // 절대 위치인지 상대위치인지
    private final boolean absoluteSize; // 절대크기인지 상대크기인지

    private final Rect realRect; // 출력될 실제 영역
    private final Point realPosition; // 출력될 실제 위치
    private final Point realSize; // 츨력될 실제 크기

    public Flex(PointF position, boolean absolutePosition, PointF size, boolean absoluteSize,
                Point positionOffset, FlexConfig flexConfig) { // Flex의 생성자. 값을 입력받아 변수를 초기화한다. 그 후 그림이 출력될 실제 크기와 위치를 계산한다.
        this.flexConfig = flexConfig;
        this.position = position;
        this.size = size;
        this.positionOffset = positionOffset;
        this.absolutePosition = absolutePosition;
        this.absoluteSize = absoluteSize;

        realRect = new Rect();
        realPosition = new Point();
        realSize = new Point();

        recalculate();
    }

    private void recalculate() { // 실제 화면에 출력될 크기와 위치 계산
        if (absolutePosition) {
            realRect.left = (int) (position.x * flexConfig.getScale());
            realRect.top = (int) (position.y * flexConfig.getScale());
        } else {
            realRect.left = (int) (position.x * flexConfig.getDisplayWidth());
            realRect.top = (int) (position.y * flexConfig.getDisplayHeight());
        }

        realRect.left += positionOffset.x * flexConfig.getScale();
        realRect.top += positionOffset.y * flexConfig.getScale();

        realPosition.x = realRect.left;
        realPosition.y = realRect.top;

        if (absoluteSize) {
            realRect.right = (int) (size.x * flexConfig.getScale());
            realRect.bottom = (int) (size.y * flexConfig.getScale());
        } else {
            realRect.right = (int) (size.x * flexConfig.getDisplayWidth());
            realRect.bottom = (int) (size.y * flexConfig.getDisplayHeight());
        }

        realSize.x = realRect.right;
        realSize.y = realRect.bottom;

        realRect.right += realRect.left;
        realRect.bottom += realRect.top;
    }

    public Point getPosition() {
        return realPosition;
    } // 실제 위치 반환

    public Point getSize() {
        return realSize;
    } // 실제 사이즈 반환

    public Rect getRect() {
        return realRect;
    } // 실제 영역 반환
}