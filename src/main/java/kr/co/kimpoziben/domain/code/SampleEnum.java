package kr.co.kimpoziben.domain.code;

public enum SampleEnum {
    A("접수"),
    B("진행중"),
    C("완료");

    final private String statName;

    private SampleEnum(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

}


