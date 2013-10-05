package mrq.plugin.minecraft.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeStamp {
    public static String getTimeStamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss", Locale.KOREA);
        return sdf.format(new Date());
    }
    public static String getTimeStamp(boolean b) {
        String s = "HH�� mm�� ss��]";
        if (b) {
            s = "YYYY�� MM�� DD�� " + s;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("[" + s, Locale.KOREA);
        return sdf.format(new Date());
    }
}