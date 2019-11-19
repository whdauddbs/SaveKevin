package wsdfhjxc.taponium.engine;

import android.graphics.*;
import android.view.*;

public abstract class Scene implements Comparable<Scene> {
    /*
    "comparable<class명> 인터페이스" : 특정한 클래스 타입의 리스트 정렬시 사용
    정렬을 이용하기 위해서는  1) 해당클래스에 comparable 인터페이스를 구현(implements)
                              2) compareTo() 메서드를 오버라이드

    */
    protected final SceneKeeper sceneKeeper;
    protected final ResourceKeeper resourceKeeper;
    protected final FlexConfig flexConfig;

    private final int id;
    protected int order = 0;    //순서를 나타내는 변수

    //씬 생성자
    public Scene(SceneKeeper sceneKeeper, ResourceKeeper resourceKeeper, FlexConfig flexConfig, int id, int order) {
        this.sceneKeeper = sceneKeeper;
        this.resourceKeeper = resourceKeeper;
        this.flexConfig = flexConfig;
        this.id = id;
        this.order = order;
    }

    public abstract void load();

    public abstract void unload();

    public abstract void backPressed();

    public abstract void handleInput(MotionEvent motionEvent);

    public abstract void handleUpdate(double deltaTime);

    public abstract void handleRender(Canvas canvas, Paint paint, double alpha);

    //id리턴 메서드
    public int getId() {
        return id;
    }

    //order리턴 메서드
    public int getOrder() {
        return order;
    }

    //객체들을 정렬하는 메서드 ( 씬 객체의 순서를 리턴받아 그 순서에 대해 정렬)
    @Override
    public int compareTo(Scene anotherScene) {
        return order - anotherScene.getOrder();
    }


    @Override
    public boolean equals(Object object) {
        return (object instanceof Scene && ((Scene)object).getId() == id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}