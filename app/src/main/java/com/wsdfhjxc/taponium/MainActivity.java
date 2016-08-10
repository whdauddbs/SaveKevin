package com.wsdfhjxc.taponium;

import android.app.*;
import android.os.*;

import com.wsdfhjxc.taponium.engine.*;

public class MainActivity extends Activity {
    private EngineRunner engineRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        engineRunner = new EngineRunner(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        engineRunner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        engineRunner.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engineRunner.stop();
    }

    @Override
    public void onBackPressed() {
        // override default back key press behavior
        engineRunner.backPressed();
    }
}