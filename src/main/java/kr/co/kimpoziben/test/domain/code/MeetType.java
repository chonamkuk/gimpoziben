package kr.co.kimpoziben.test.domain.code;

public enum MeetType {
    A("업무회의"),
    B("식사");

    final private String statName;

    private MeetType(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

}


