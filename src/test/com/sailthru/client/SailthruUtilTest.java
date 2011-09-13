package com.sailthru.client;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Prajwal Tuladhar <praj@sailthru.com> <praj@sailthru.com>
 */
public class SailthruUtilTest extends TestCase {
    public void testMd5() {
        String plainText1 = "hello_world";
        String hash1 = SailthruUtil.md5(plainText1);
        String expectedHash1 = "99b1ff8f11781541f7f89f9bd41c4a17";
        assertEquals(hash1, expectedHash1);
    }
    
    public void testArrayListToCSV() {
        java.util.List<String> list1 = new ArrayList<String>();
        list1.add("windows");
        list1.add("linux");
        list1.add("BSD");
        String expectedList1 = "windows,linux,BSD";
        assertEquals(expectedList1, SailthruUtil.arrayListToCSV(list1));
        
        java.util.List<String> list2 = new ArrayList<String>();
        list2.add("one_item");
        String expectedList2 = "one_item";
        assertEquals(expectedList2, SailthruUtil.arrayListToCSV(list2));
    }
}
