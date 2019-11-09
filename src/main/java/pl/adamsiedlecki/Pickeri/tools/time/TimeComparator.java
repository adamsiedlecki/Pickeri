package pl.adamsiedlecki.Pickeri.tools.time;

import org.apache.commons.lang3.math.NumberUtils;

public class TimeComparator {

    public static int compare(String val1, String val2){
        long hours1 = getHours(val1);
        long minutes1 = getMinutes(val1);
        long hours2 = getHours(val2);
        long minutes2 = getMinutes(val2);

        if(hours1>hours2){
            return 1;
        }else if(hours1==hours2){
            return Long.compare(minutes1, minutes2);
        }else{
            return -1;
        }

    }

    private static long getHours(String value){
        String[] tab = value.split(" ");
        if(NumberUtils.isDigits(tab[0])){
            return Long.parseLong(tab[0]);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    private static long getMinutes(String value){
        String[] tab = value.split(" ");
        if(NumberUtils.isDigits(tab[2])){
            return Long.parseLong(tab[2]);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

}
