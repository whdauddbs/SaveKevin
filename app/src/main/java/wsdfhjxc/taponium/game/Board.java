package wsdfhjxc.taponium.game;

import wsdfhjxc.taponium.engine.TimedHandler;

public class Board { //보드판의 값을 지정하는 클래스
    //크기 3X3 게임보드 생성
    private final int width = 3;
    private final Slot[][] slots = new Slot[width][width];
    private final SlotContent hamsterContent, bunnyContent, emptyContent; //슬롯 콘텐트 변수
    private final SlotContentDistributor slotContentDistributor; //GameRules에 설정된 비율로 콘텐트를 분배하는 클래스

    private final ScoreCounter scoreCounter;
    private TimeCounter timeCounter;

    //콘텐트 변경 기간을 0.0으로 초기화
    private double currentChangeDuration = 0.0;

    public Board(ScoreCounter scoreCounter) {
        this.scoreCounter = scoreCounter;

        //햄스터를 포함하는 슬롯 생성
        hamsterContent = new SlotContent(SlotContentType.HAMSTER,
                GameRules.HAMSTER_CONTENT_OCCURRENCE_CHANCE,
                GameRules.HAMSTER_CONTENT_MIN_DURATION,
                GameRules.HAMSTER_CONTENT_MAX_DURATION);
        //토끼를 포함하는 슬롯 생성
        bunnyContent = new SlotContent(SlotContentType.BUNNY,
                GameRules.BUNNY_CONTENT_OCCURRENCE_CHANCE,
                GameRules.BUNNY_CONTENT_MIN_DURATION,
                GameRules.BUNNY_CONTENT_MAX_DURATION);
        //빈 슬롯 생성
        emptyContent = new SlotContent(SlotContentType.EMPTY,
                GameRules.EMPTY_CONTENT_OCCURRENCE_CHANCE,
                GameRules.EMPTY_CONTENT_MIN_DURATION,
                GameRules.EMPTY_CONTENT_MAX_DURATION);

        slotContentDistributor = new SlotContentDistributor(hamsterContent, bunnyContent, emptyContent);
        //새로운 slotContentDistributor 생성, 새로운 값들을 매개변수로 전달
        initializeSlots();
    }

    public Board(ScoreCounter scoreCounter, TimeCounter timeCounter) {
        this(scoreCounter);
        this.timeCounter = timeCounter;
    }
    //슬롯 초기화
    private void initializeSlots() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                slots[j][i] = new Slot(this);
            }
        }
    }
    //슬롯 업데이트
    private void updateSlots(double deltaTime) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Slot slot = slots[j][i];
                slot.update(deltaTime);
            }
        }
    }
    //슬롯 채우기
    private void fillSlots() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Slot slot = slots[j][i];
                if (slot.isFree()) {
                    slotContentDistributor.distributeContent(slot);
                }
            }
        }
    }
    //발생확률에 따라 콘텐트의 순위를 매겨 각 변수에 맞게 저장하는 메소드
    public void update(double deltaTime) {
        currentChangeDuration += deltaTime;
        //현재 진행 시간이 지정 값 이상이라면
        if (currentChangeDuration >= GameRules.CONTENT_DURATION_CHANGE_INTERVAL) {
            currentChangeDuration = 0.0; //진행 시간 다시 초기화

            //최대 지속 기간 및 최소 지속 기간 설정
            double hamsterMin = hamsterContent.getMinDuration();
            double hamsterMax = hamsterContent.getMaxDuration();
            hamsterContent.setMinDuration(hamsterMin * GameRules.CONTENT_DURATION_SCALING_FACTOR);
            hamsterContent.setMaxDuration(hamsterMax * GameRules.CONTENT_DURATION_SCALING_FACTOR);

            double bunnyMin = bunnyContent.getMinDuration();
            double bunnyMax = bunnyContent.getMaxDuration();
            bunnyContent.setMinDuration(bunnyMin * GameRules.CONTENT_DURATION_SCALING_FACTOR);
            bunnyContent.setMaxDuration(bunnyMax * GameRules.CONTENT_DURATION_SCALING_FACTOR);

            double emptyMin = emptyContent.getMinDuration();
            double emptyMax = emptyContent.getMaxDuration();
            emptyContent.setMinDuration(emptyMin * GameRules.CONTENT_DURATION_SCALING_FACTOR);
            emptyContent.setMaxDuration(emptyMax * GameRules.CONTENT_DURATION_SCALING_FACTOR);
        }
        //슬롯을 다시 업데이트
        updateSlots(deltaTime);
        fillSlots();
    }
    //슬롯의 값을 받아옴
    public Slot getSlot(int x, int y) {
        return slots[x][y];
    }
    //슬롯의 크기를 받아옴
    public int getWidth() {
        return width;
    }

    public void slotTotalDurationPassed(Slot slot) {
        if (slot.getContentType() == SlotContentType.HAMSTER) {
            if(TimedHandler.levelCheck != 4 && timeCounter == null)
                scoreCounter.add(GameRules.HAMSTER_CONTENT_MISSED_POINTS);
            else{
                timeCounter.decreaseTimer(GameRules.HAMSTER_CONTENT_MISSED_TIME);
            }
        }
    }
}
