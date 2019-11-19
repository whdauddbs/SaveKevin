package wsdfhjxc.taponium.engine;

public class RenderHandler extends TimedHandler {
    private final ApplicationView applicationView;

    public RenderHandler(ApplicationView applicationView, int frequency) {
        super(frequency, false);
        this.applicationView = applicationView;
    } //RenderHandler의 생성자

    @Override
    protected void handle() {
        applicationView.update();
    }
} //ApplicationView의 update함수를 불러오는 함수