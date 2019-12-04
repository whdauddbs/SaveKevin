package wsdfhjxc.taponium.game;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class TimeCounter {
    public static long current; // 남은 시간
    private CountDownTimer timer;
    private boolean isOver;
    private long delay = 30 * 1000; // 제한 시간 30초

    public TimeCounter()
    {
        isOver = false;
    }

    public long getCurrent(){ return current / 1000; } // 현재 시간 반환

    public boolean isNegative() { // 남은 시간이 0이하가 된 경우 게임 오버 장면으로 넘어갈 수 있도록 판별하기 위해 필요한 함수
        return isOver;
    }

    public void update() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                timer = new CountDownTimer(delay, 100) { // 30초동안 1초 간격으로

                    @Override
                    public void onTick(long millisUntilFinished) {
                        current = millisUntilFinished;
                        Log.i("second: ", " " + millisUntilFinished);
                    }

                    @Override
                    public void onFinish() {
                        timer.cancel();
                        isOver = true;
                    }
                }.start();
            }
        });
    }

    public void pauseTimer()
    {
        if(timer != null) {
            delay = current;
            timer.cancel();
        }
    }

    public void cancelTimer()
    {
        if(timer != null)
            timer.cancel();
    }
}
