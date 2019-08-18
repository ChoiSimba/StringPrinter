package io.simba.StringPrinter.service.impl;

import io.simba.StringPrinter.service.PrintService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrintServiceImplTests {
    @Autowired
    private PrintService printService;


    @Test
    public void testSortEnglish() {
        String test = "abcdeAABBCCDDEE";

        Assert.assertEquals("", "AAaBBbCCcDDdEEe", printService.sortEnglish(test));
    }

    @Test
    public void testSortNumber() {
        String test = "089765421";

        Assert.assertEquals("", "012456789", printService.sortNumber(test));
    }

    @Test
    public void testMergeString() {
        String str1 = "123456789";
        String str2 = "abcdefg";

        Assert.assertEquals("", "1a2b3c4d5e6f7g89", printService.mergeString(str1, str2));
    }

    @Test
    public void testRemoveHtmlTag() {
        String test = "<table><tr><td>text</td></tr></table>";

        Assert.assertEquals("", "text", printService.removeHtmlTag(test));
    }
}
