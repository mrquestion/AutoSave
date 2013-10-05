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
        String s = "HH시 mm분 ss초]";
        if (b) {
            s = "YYYY년 MM월 DD일 " + s;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("[" + s, Locale.KOREA);
        return sdf.format(new Date());
    }
}