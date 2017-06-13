package net.cromulence.imgur.apiv3;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Utils {

    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;

    private static final DateTimeFormatter DTF = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME).toFormatter();

    private Utils() {}

    public static String formatHHMM(long interval) {
        long milli = interval % Utils.SECOND;

        long sec = (interval - milli) % Utils.MINUTE;

        long min = (interval - (milli + sec)) % Utils.HOUR;

        long hour = (interval - (milli + sec + min));

        return String.format("%dh%02dm", (hour / Utils.HOUR), (min / Utils.MINUTE));
    }

    public static synchronized String formatUnixTime(long datetime) {
        Instant instant = Instant.ofEpochSecond(datetime);
        return DTF.format(instant);
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
