package io.simba.StringPrinter.service;

import io.simba.StringPrinter.dto.PrintCondition;
import io.simba.StringPrinter.dto.PrintResult;

public interface PrintService {
    PrintResult request(PrintCondition printCondition);
    String sortEnglish(String source);
    String sortNumber(String source);
    String mergeString(String str1, String str2);
    String removeHtmlTag(String source);
}
