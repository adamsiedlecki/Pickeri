package pl.adamsiedlecki.Pickeri.tools.time;

import pl.adamsiedlecki.Pickeri.entity.WorkTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

public class TimeConverter {

    public static String getString(BigDecimal seconds) {
        BigDecimal workMinutes = seconds.divide(new BigDecimal(60), 2, RoundingMode.FLOOR);
        long workHours = workMinutes.longValue() / 60;
        long restMinutes = workMinutes.longValue() % 60;
        return format(workHours, restMinutes);
    }

    public static String getString(WorkTime workTime) {
        BigDecimal workMinutes = new BigDecimal(workTime.getDuration().getSeconds())
                .divide(new BigDecimal(60), 2, RoundingMode.FLOOR);
        long workHours = workMinutes.longValue() / 60;
        long restMinutes = workMinutes.longValue() % 60;
        return format(workHours, restMinutes);
    }

    public static String getString(Duration duration) {
        return format(duration.toHours(), duration.toMinutesPart());
    }

    private static String format(long workHours, long restMinutes) {
        if(restMinutes<10){
            return workHours + " h 0" + restMinutes+" min";
        }

        return workHours + " h " + restMinutes+" min ";
    }


}
