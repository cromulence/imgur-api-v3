package net.cromulence.imgur.apiv3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestUtils {

    @Test
    public void testCsl() {

        String[] values = new String[]{"111", "222", "333"};

        String s = Utils.asCommaSeparatedList(values);

        int expectedLength = values.length - 1; // commaa
        for(String v : values) { // parts
            expectedLength += v.length();
        }

        assertEquals("should be the total length of the parts + commas between", expectedLength, s.length());
    }

}
