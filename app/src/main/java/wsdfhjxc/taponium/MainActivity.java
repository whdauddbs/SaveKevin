package wsdfhjxc.taponium;

import android.app.*;
import android.media.MediaPlayer;   //음악을 담을 미디어 임포트
import android.os.*;

import wsdfhjxc.taponium.engine.*;

public class MainActivity extends Activity {
    private EngineRunner engineRunner;

    //미디어플레이어 객체 선언
    public static MediaPlayer mediaPlayer; //static으로 선언하여 MainMenuScene에서 접근 가능하도록함
    public static boolean musicSwitch; //마찬가지로 static으로 선언해 MainMenuScene에서 접근이 가능하도록 하고
    //MainMenuScene에서 버튼을 클릭했을때 값을 변경해서 메인엑티비티의 음악을 끌지말지 결정


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //raw폴더 밑에 intro.mp3 파일을 미디어 파일에 담음
        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.intro2);
        musicSwitch = true;
        if(musicSwitch){
            //음악 실행
            mediaPlayer.start();
        }


        engineRunner = new EngineRunner(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        engineRunner.start();
    }

    @Override
    protected void onStop() {
        engineRunner.pause();
        mediaPlayer.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        engineRunner.stop();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        // override default back key press behavior
        engineRunner.backPressed();
    }
}