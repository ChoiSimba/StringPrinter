package io.simba.StringPrinter.dto;

import io.simba.StringPrinter.constant.ExtractType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrintCondition {
    private String url;
    private ExtractType extractType;
    private int printBundleUnit;
}
