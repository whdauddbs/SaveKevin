package wsdfhjxc.taponium.engine;

import android.content.*;
import android.graphics.*;
import android.view.*;

public class ApplicationView extends View {
    private final SceneKeeper sceneKeeper;
    private final UpdateHandler updateHandler;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //표면 처리를 부드럽게 해준다.

    public ApplicationView(Context context, SceneKeeper sceneKeeper, UpdateHandler updateHandler) {
        super(context);
        this.sceneKeeper = sceneKeeper;
        this.updateHandler = updateHandler;
    } //ApplicationView의 생성자

    private void clearCanvas(Canvas canvas) {
        // clear the entire screen with a black color filling
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    } // 화면을 검정색으로 채운다.

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clearCanvas(canvas);

        // synchronized as this might be automatically called by OS from non-engine thread
        
        synchronized(sceneKeeper) { // 여러 스레드가 하나의 자원을 공유할 때 충돌을 방지하기 위한 처리
            // *색이 적절히 섞이도록 하는 투명도를 설정하는 Alpha값을 계속 업데이트함
            double alpha = updateHandler.getAlpha();
            for (Scene scene : sceneKeeper.scenes) {
                scene.handleRender(canvas, paint, alpha);
            }
        } //각 장면들이 sceneKeeper에 저장된 장면 배열을 돌면서 canvas, paint, alpha 인자를 받아서 scene의 handleRender 추상 함수를 실행한다.
    }

    public void update() {
        postInvalidate();
    } //*non-UI thread에서 호출되어야 한다.
}