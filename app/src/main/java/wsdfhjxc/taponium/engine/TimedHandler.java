
//"""TimedHandler는 FPS조정을 위해 필요한 부분이므로 그대로 냅두는 것이 나을 것 같음"""
package wsdfhjxc.taponium.engine;

public abstract class TimedHandler {
    private final long interval;
    private long lastTime;
    private long delay, _delay; // _delay is used to store last delay value for the FPS counter
    private boolean highPriority;

    public static int levelCheck=1;  //1이면 이지, 2이면 노말, 3이면 하드, 4면 타임어택

    public TimedHandler(int frequency, boolean highPriority) {
        interval = delay = 1000 / frequency; /* 몇 ms마다 프레임 한장을 갱신해야 하는지를 저장하는 변수.
        updatehandler의 경우 frequency가 15, renderhandler의 경우 frequency가 30*/
        this.highPriority = highPriority;
    } /*TimedHandler를 상속받는 RenderHandler와 UpdateHandler에서
    frequency와 highPriority의 값을 설정해준다. */

    protected abstract void handle();

    public void poll() {
        while (delay >= interval) {
            _delay = delay;
            delay = highPriority ? delay - interval : 0;
            lastTime = System.currentTimeMillis() - delay;

            handle();
        }

        delay = System.currentTimeMillis() - lastTime;
    } // *이 함수는 잘 모르겠습니다...프레임이 넘어가는 시간을 일정하게 맞춰주기 위한 함수인 것 같네요.


    public void resetDelay() {
        lastTime = System.currentTimeMillis();
        delay = 0;
    } //Delay를 초기화함. 앱을 나갔다가 다시 들어왔을 때 걸린 시간차를 없애기 위한 함수



    public double getDeltaTime() {
            return interval / 1000.0;

    } //UpdateHandler에서 사용될 deltaTime변수를 리턴하는 함수

    public double getAlpha() {
        return (double) delay / interval;
    } // *getAlpha함수도 잘 모르겠습니다. 왜 delay를 interval로 나눈 값을 반환하는지...

    public double getFPS() {
        return 1000.0 / _delay;
    } //초당 프레임을 리턴하는 함수.
}