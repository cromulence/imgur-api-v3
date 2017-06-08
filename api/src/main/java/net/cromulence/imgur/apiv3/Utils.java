package net.cromulence.imgur.apiv3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Utils() {}

    public static String formatHHMM(long interval) {
        long milli = (interval - (0)) % Utils.SECOND;

        long sec = (interval - (milli)) % Utils.MINUTE;

        long min = (interval - (milli + sec)) % Utils.HOUR;

        long hour = (interval - (milli + sec + min));

        return String.format("%dh%02dm", (hour / Utils.HOUR), (min / Utils.MINUTE));
    }

    public static String formatUnixTime(long datetime) {
        return SDF.format(new Date(datetime * 1000L));
    }

    public static String formatUnixTime(String datetime) {
        return formatUnixTime(Long.parseLong(datetime));
    }

    public static String asCommaSeparatedList(String[] strings) {

        final StringBuilder sb = new StringBuilder();

        for (String s : strings) {
            sb.append(s).append(",");
        }

        if (strings.length > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
