package kr.co.kimpoziben.domain.code;

public enum OrderStat {
    A("접수"),
    B("취소"),
    C("완료");

    final private String statName;

    private OrderStat(String statName) {
        this.statName = statName;
    }

    public String getStatName() {
        return statName;
    }

}


