package wsdfhjxc.taponium.game;

import android.util.Log;

import java.util.*;


public class HighScoreList {

    //현재 게임 점수
    private int i=0;
    //가져온 데이터들로 만든 리스트(순위)
    private long[][] stList;

    public HighScoreList(long[][] list){
        stList = list;
    }

    //점수를 리스트에 추가하는 메서드
    public void addScore(int difficulty, long currentScore){
        stList[difficulty][0]= currentScore;
        Arrays.sort(stList[difficulty]);
    }

    //게임순위 리스트를 반환하는 메서드
    public long[][] getStList() {
        return stList;
    }
}
