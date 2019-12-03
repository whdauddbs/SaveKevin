package wsdfhjxc.taponium.game;

public class GameRules { // 게임의 규칙에 사용되는 상수값을 지정하는 클래스
    // 각 컨텐트(햄스터,토끼,빈칸)가 나타나는 비율, 각 확률의 합은 1.0임.
    public static final double HAMSTER_CONTENT_OCCURRENCE_CHANCE = 0.4;
    public static final double BUNNY_CONTENT_OCCURRENCE_CHANCE = 0.1;
    public static final double EMPTY_CONTENT_OCCURRENCE_CHANCE = 0.5;

    // 각 컨텐츠를 얼마나 오랫동안 보여줄 것인지 (최대시간, 최소시간)
    public static final double HAMSTER_CONTENT_MIN_DURATION = 3.0;
    public static final double HAMSTER_CONTENT_MAX_DURATION = 4.5;

    public static final double BUNNY_CONTENT_MIN_DURATION = 2.0;
    public static final double BUNNY_CONTENT_MAX_DURATION = 6.0;

    public static final double EMPTY_CONTENT_MIN_DURATION = 2.0;
    public static final double EMPTY_CONTENT_MAX_DURATION = 4.0;

    // 시간이 흐름에 따라 게임의 난이도가 어떻게 바뀌는지(빨라지는 간격 10초, 빨라지는 비율 0.95)
    public static final double CONTENT_DURATION_CHANGE_INTERVAL = 10.0;
    public static final double CONTENT_DURATION_SCALING_FACTOR = 0.95;

    // 게임 내의 스코어 계산 방식(햄스터 10, 햄스터 놓치면 -100, 토끼 -1000)
    public static final int HAMSTER_CONTENT_TAPPED_POINTS = 10;
    public static final int HAMSTER_CONTENT_MISSED_POINTS = -100;
    public static final int BUNNY_CONTENT_TAPPED_POINTS = -1000;

    // 죽은 토끼와 햄스터가 탭한 이후 얼마나 오래 화면에 남아있는지
    public static final double TAPPED_CONTENT_DURATION_SCALING_FACTOR = 0.2;

    public static final String[] DIFFICULTY = {"easy", "normal", "hard", "timeAttack"};

}