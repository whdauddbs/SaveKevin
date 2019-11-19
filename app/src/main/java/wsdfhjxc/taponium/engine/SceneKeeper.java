package wsdfhjxc.taponium.engine;

import java.util.*;

public class SceneKeeper {    // 씬을 저장하는 클래스
    //씬클래스 형태로 이루어진 arrayList들 선언
    public final List<Scene> scenes;
    private final List<Scene> scenesToLoad;
    private final List<Scene> scenesToAdd;
    private final List<Scene> scenesToRemove;
    private final List<Scene> scenesToUnload;

    public SceneKeeper() {      //씬키퍼 생성자
        //arraylist변수들 생성
        scenes = new ArrayList();
        scenesToLoad = new ArrayList();
        scenesToAdd = new ArrayList();
        scenesToRemove = new ArrayList();
        scenesToUnload = new ArrayList();
    }

    //씬 정렬 메서드
    private void sort() {
        Collections.sort(scenes);
    } //리스트' 자료구조를 정렬할 때 사용하는 Collections 클래스의 메소드.
                                                        //기본적으로 오름차순 정렬

    //씬 추가 메서드
    public void addScene(Scene scene) {
        if (!scenesToAdd.contains(scene) && !scenesToRemove.contains(scene)) {
            //contain() 메서드는 특정 객체가 리스트 안에 포함되었는지 확인할 때 사용
            //scenesToAdd,scenesToRemove 리스트 안에 씬객체가 없을때만 씬을 추가
            scenesToAdd.add(scene);
        }
    }

    //씬 제거메서드
    public void removeScene(Scene scene) {
        if (!scenesToRemove.contains(scene)) {
            scenesToRemove.add(scene);
        }
    }

    //모든 씬 제거 메서드
    public void removeAllScenes() {
        for (Scene scene : scenes) {
            //씬 리스트의 모든 씬객체들을 하나씩 삭제
            scenesToRemove.add(scene);
        }
    }

    //씽크로나이즈 사용법 잘 모르겠음..
    synchronized private void loadScenes() {
        for (Scene scene : scenesToLoad) {
            scene.load();
            scenes.add(scene);
        }

        scenesToLoad.clear();
    }

    synchronized private void unloadScenes() {
        for (Scene scene : scenesToUnload) {
            scenes.remove(scene);
            scene.unload();
        }

        scenesToUnload.clear();
    }

    private boolean addScenes() {
        boolean hasSomethingBeenAdded = !scenesToAdd.isEmpty();
        //isEmpty() 메서드는 인스턴스가 없을때 true를 반환

        for (Scene scene : scenesToAdd) {
            scenesToLoad.add(scene);
        }

        scenesToAdd.clear();
        loadScenes();

        return hasSomethingBeenAdded;
    }

    private void removeScenes() {
        for (Scene scene : scenesToRemove) {
            scenesToUnload.add(scene);
        }

        scenesToRemove.clear();
        unloadScenes();
    }

    public void poll() {
        removeScenes();

        if (addScenes()) {
            sort();
        }
    }

    public boolean hasScenes() {
        return !(scenes.isEmpty() && scenesToAdd.isEmpty() && scenesToLoad.isEmpty());
    }
}