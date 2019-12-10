package wsdfhjxc.taponium.game;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class TimeCounter {
    public static long current; // 남은 시간
    public static long milliLeft; // 일시정지 시 남은 시간 저장

    private CountDownTimer timer;
    private boolean isOver; // 게임이 종료됬는지 판별하는 변수

    public TimeCounter() {
        current = 60;
        Log.i("Timer: ", "Start!");
        isOver = false;
    }

    public long getCurrent() {
        return current;
    } // 현재 시간 반환

    public boolean isNegative() { // 남은 시간이 0이하가 된 경우 게임 오버 장면으로 넘어갈 수 있도록 판별하기 위해 필요한 함수
        return isOver;
    }

    public void pauseTimer(){ // 일시정지 기능 활성화 시 남은 시간 저장
        Log.i("남은시간: ", " "+current);
        Log.i("저장시간: ", " "+milliLeft);
        timer.cancel();
    }

    public void resumeTimer(){
        current = milliLeft/1000;
        Log.i("남은시간: ", " "+current);
        update();

    }

    public void update()
    {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                timer = new CountDownTimer(current * 1000, 100) { // 30초동안 1초 간격으로

                    @Override
                    public void onTick(long millisUntilFinished) {
                        milliLeft = millisUntilFinished;
                        current = millisUntilFinished/1000;
                        Log.i("second: ", " "+millisUntilFinished/1000);
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

    public void cancelTimer()
    {
        Log.i("Timer: ", "End!");
        if(timer != null)
            timer.cancel();
    }

    public  void decreaseTimer(int time){
        pauseTimer();
        milliLeft += time * 1000;
        resumeTimer();
    }
}
