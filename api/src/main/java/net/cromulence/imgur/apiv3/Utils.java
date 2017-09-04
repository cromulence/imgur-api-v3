package net.cromulence.imgur.apiv3;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Utils {

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
