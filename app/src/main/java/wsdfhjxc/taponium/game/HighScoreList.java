package wsdfhjxc.taponium.game;

import java.util.Collections;
import java.util.*;


public class HighScoreList {

    //현재 게임 점수
    private long currentScore;
    private int i=0;
    //가져온 데이터들로 만든 리스트(순위)
    private long[] stList;

    public HighScoreList(){
        stList = new long[5];
        for(int i=0;i<5;i++){
            stList[i]=0;
        }
    }

    //점수를 리스트에 추가하는 메서드
    public void addScore(){

        stList[i]=currentScore;
        Arrays.sort(stList);

/*
        long temp = 0;
        long temp2 =0;
        for(int i=0;i<5;i++){
            if(stList[i]<currentScore){
                temp = stList[i]; // previous value to temp
                stList[i]=currentScore; //current valut to stlist

                //stList[i+1]=temp

                for(int j=i+1;j<4;j++) {
                    //temp2 = stList[j]; // current value to temp2
                    //stList[j] = temp;
                    stList[j]=temp;
                    temp = stList[j+1];
                    //temp2 = 0;
                }
            }
        }*/
    }

    //현재게임 점수를 초기화하는 메서드
    public void setScore(long score){
        currentScore = score;
    }

    //게임순위 리스트를 반환하는 메서드
    public long[] getStList() {
        return stList;
    }
}
