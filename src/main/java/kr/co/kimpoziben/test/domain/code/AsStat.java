package kr.co.kimpoziben.test.domain.code;

public enum AsStat {
    A("접수"),
    B("진행중"),
    C("완료");

    final private String statName;

    private AsStat(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

}


