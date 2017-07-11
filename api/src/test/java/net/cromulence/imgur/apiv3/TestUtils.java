package net.cromulence.imgur.apiv3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestUtils {

    @Test
    public void testCsl() {

        String[] values = new String[]{"1", "22", "333", "4444", "55555"};

        String s = Utils.asCommaSeparatedList(values);

        int expectedLength = values.length - 1; // commas
        for(String v : values) { // parts
            expectedLength += v.length();
            assertTrue("all parts should be there",
                s.matches("^" + v + ",.*") // beginning
                || s.matches(".*," + v + "$") // end
                || s.matches(".*," + v + ",.*")); // middle
        }

        assertEquals("should be the total length of the parts + commas between", expectedLength, s.length());
    }

}
