package wsdfhjxc.taponium.game;

public class SlotContentDistributor { // GameRules에 설정된 비율로 콘텐트를 분배하는 클래스
    private final SlotContent hamsterContent, bunnyContent, emptyContent; //슬롯 콘텐트 변수
    private SlotContent mostCommonContent, lessCommonContent, leastCommonContent;//각 콘텐트의 발생 확률에 따라 슬롯 콘텐트를 저장하는 변수

    public SlotContentDistributor(SlotContent hamsterContent, SlotContent bunnyContent, SlotContent emptyContent) {
        this.hamsterContent = hamsterContent;
        this.bunnyContent = bunnyContent;
        this.emptyContent = emptyContent;

        update();
    } //각 타입의 슬롯 콘텐트들을 입력받아 위에서 선언한 변수들을 초기화하는 생성자

    public final void update() {//발생확률에 따라 콘텐트의 순위를 매겨 각 변수에 맞게 저장하는 메소드
        double hamsterOccurrenceChance = hamsterContent.getOccurrenceChance();
        double bunnyOccurrenceChance = bunnyContent.getOccurrenceChance();
        double emptyOccurrenceChance = emptyContent.getOccurrenceChance();

        if (hamsterOccurrenceChance < bunnyOccurrenceChance) {
            if (hamsterOccurrenceChance < emptyOccurrenceChance) {
                leastCommonContent = hamsterContent;

                if (bunnyOccurrenceChance < emptyOccurrenceChance) {
                    lessCommonContent = bunnyContent;
                    mostCommonContent = emptyContent;
                } else {
                    lessCommonContent = emptyContent;
                    mostCommonContent = bunnyContent;
                }
            } else {
                leastCommonContent = emptyContent;
                lessCommonContent = hamsterContent;
                mostCommonContent = bunnyContent;
            }
        } else if (bunnyOccurrenceChance < emptyOccurrenceChance) {
            leastCommonContent = bunnyContent;
            lessCommonContent = hamsterContent;
            mostCommonContent = emptyContent;
        } else {
            leastCommonContent = emptyContent;
            lessCommonContent = bunnyContent;
            mostCommonContent = hamsterContent;
        }
    }

    private double randomizeContentDuration(SlotContent slotContent) {
        double minDuration = slotContent.getMinDuration();
        double maxDuration = slotContent.getMaxDuration();
        return minDuration + Math.random() * (maxDuration - minDuration);
    }//콘텐트의 지속시간을 랜덤화하는 메소드

    public void distributeContent(Slot slot) {
        SlotContent contentToDistribute;

        double random = Math.random();
        if (random > leastCommonContent.getOccurrenceThreshold()) {
            contentToDistribute = leastCommonContent;
        } else if (random > lessCommonContent.getOccurrenceThreshold()) {
            contentToDistribute = lessCommonContent;
        } else {
            contentToDistribute = mostCommonContent;
        }

        slot.set(contentToDistribute.getType(), randomizeContentDuration(contentToDistribute));
    } // 랜덤으로 한 콘텐트 타입을 뽑아 슬롯에 콘텐트를 지정하는 메소드
}