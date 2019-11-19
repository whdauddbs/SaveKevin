package wsdfhjxc.taponium.game;

public class Slot { // 게임에 사용되는 슬롯을 다루는 클래스
    private SlotContentType contentType; // 슬롯에 나타날 컨텐트의 타입을 저장하는 변수
    private double totalDuration = 0.0; // 컨텐트가 올라와 있을 지속시간을 저장하는 변수
    private double currentDuration = 0.0; // 슬롯에 있는 콘텐트의 현재 지속시간
    private double _currentDuration = 0.0; // 선형 보간법에 사용되는 이전의 값을 저장하는 변수

    private final Board board; //board를 저장하는 변수

    public Slot(Board board) {
        this.board = board;
    }//board 객체를 받아와서 저장하는 생성자

    public void set(SlotContentType contentType, double totalDuration) {
        setContentType(contentType);
        setTotalDuration(totalDuration);
        this.currentDuration = 0.0;
        this._currentDuration = 0.0;
    }// 선언한 클래스의 멤버 변수들을 초기화하는 메소드

    public final void setContentType(SlotContentType contentType) {
        this.contentType = contentType;
    }//콘텐트 타입을 저장하는 메소드

    public final void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    } // 총 지속시간을 저장하는 메소드

    public void scaleDuration(double durationScale) {
        this.totalDuration *= durationScale;
        this.currentDuration *= durationScale;
        this._currentDuration *= durationScale;
    }//클릭된 콘텐트가 떨어지는 속도를 계산하는 메소드

    public void update(double deltaTime) {
        if (contentType != null) {
            _currentDuration = currentDuration;
            currentDuration += deltaTime;

            if (currentDuration >= totalDuration) {
                board.slotTotalDurationPassed(this);

                contentType = null;
                currentDuration = 0.0;
                _currentDuration = 0.0;
            }
        }
    }// 프레임이 넘어갈때마다 지속 시간을 갱신하고, 지속 시간이 총 지속시간 이상이 될 경우 contentType과 지속시간을 초기화한다.

    public boolean isFree() {
        return contentType == null;
    }
    //콘텐트 종류가 지정되어있는지 확인하는 메소드

    public SlotContentType getContentType() {
        return contentType;
    }
    //콘텐트 타입을 반환해주는 메소드

    public double getDurationRatio() {
        return currentDuration / totalDuration;
    }
    //지속시간과 총 지속시간의 비율을 반환하는 메소드

    public double getInterpolatedDurationRatio(double alpha) {
        double interpolatedDuration = (1.0 - alpha) * _currentDuration + alpha * currentDuration;
        return interpolatedDuration / totalDuration;
    } // 콘텐트의 움직임을 표현하기 위해 interpolatedDuration과 totalDuration의 비율을 반환 (보간법 이용)
}