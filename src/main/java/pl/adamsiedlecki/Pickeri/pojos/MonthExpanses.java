package pl.adamsiedlecki.Pickeri.pojos;

import java.math.BigDecimal;
import java.util.Objects;

public class MonthExpanses {

    private BigDecimal expansesSum;
    private String monthName;

    public MonthExpanses(BigDecimal expansesSum, String monthName) {
        this.expansesSum = expansesSum;
        this.monthName = monthName;
    }

    public BigDecimal getExpansesSum() {
        return expansesSum;
    }

    public void setExpansesSum(BigDecimal expansesSum) {
        this.expansesSum = expansesSum;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    @Override
    public String toString() {
        return "MonthExpanses{" +
                "expansesSum=" + expansesSum +
                ", monthName='" + monthName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonthExpanses)) return false;
        MonthExpanses that = (MonthExpanses) o;
        return Objects.equals(getExpansesSum(), that.getExpansesSum()) &&
                Objects.equals(getMonthName(), that.getMonthName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExpansesSum(), getMonthName());
    }
}
