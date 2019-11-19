package wsdfhjxc.taponium.game;

public class SlotContent { // 슬롯에 들어가는 컨텐트의 속성값들을 다루는 클래스
    private SlotContentType type; // 슬롯 컨텐트 종류를 저장할 변수 선언
    private double chance; // 확률을 저장할 변수 선언
    private double minDuration, maxDuration; //maxDuration,minDuration 변수 선언

    public SlotContent(SlotContentType type, double chance, double minDuration, double maxDuration) {
        set(type, chance, minDuration, maxDuration);
    }// 선언한 변수들의 값을 초기화하는 생성자

    public final void set(SlotContentType type, double chance, double minDuration, double maxDuration) {
        setType(type);
        setChance(chance);
        setMinDuration(minDuration);
        setMaxDuration(maxDuration);
    }//슬롯콘텐트 종류, 확률, minDuration, maxDuration에 값을 저장하기 위한 메소드

    public final void setType(SlotContentType type) {
        this.type = type;
    } //슬롯콘텐트 종류를 입력받아 저장

    public final void setChance(double chance) {
        this.chance = chance;
    } // 확률을 입력받아 저장

    public final void setMinDuration(double minDuration) { this.minDuration = minDuration;}
    // minDuration을 입력받아 저장

    public final void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }
    //maxDuration을 입력받아 저장

    public SlotContentType getType() {
        return type;
    } // 슬롯에 있는 컨텐츠 종류 반환

    public double getOccurrenceChance() {
        return chance;
    } // 컨텐트 발생 확률 반환

    public double getOccurrenceThreshold() {
        return 1.0 - chance;
    } //임계값 반환

    public double getMinDuration() {
        return minDuration;
    } //minDuration 값 반환

    public double getMaxDuration() {
        return maxDuration;
    } //maxDuration 값 반환
}