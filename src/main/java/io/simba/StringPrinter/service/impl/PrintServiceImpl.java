package io.simba.StringPrinter.service.impl;

import io.simba.StringPrinter.constant.ExtractType;
import io.simba.StringPrinter.dto.PrintCondition;
import io.simba.StringPrinter.dto.PrintResult;
import io.simba.StringPrinter.service.PrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Service
public class PrintServiceImpl implements PrintService {
    private RestTemplate restTemplate;


    @Autowired
    PrintServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public PrintResult request(PrintCondition printCondition) {
        String content = getContentFromUrl(printCondition.getUrl());

        if (ExtractType.EXCEPT_HTML.equals(printCondition.getExtractType())) {
            content = removeHtmlTag(content);
        }

        String english = content.replaceAll("[^A-Za-z]", "");
        String number = content.replaceAll("[^0-9]", "");

        String sortedEnglish = sortEnglish(english);
        String sortedNumber = sortNumber(number);
        String mergedString = mergeString(sortedEnglish, sortedNumber);

        int mergedStringLength = mergedString.length();
        int printBundleUnit = printCondition.getPrintBundleUnit();
        int remainder = mergedStringLength % printBundleUnit;
        int quotient = mergedStringLength - remainder;

        PrintResult printResult = new PrintResult();
        printResult.setQuotient(mergedString.substring(0, Math.max(quotient - 1, 0)));
        printResult.setRemainder(mergedString.substring(quotient));

        return printResult;
    }

    public String getContentFromUrl(String targetUrl) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(targetUrl, String.class);
        return responseEntity.getBody();
    }

    public String sortEnglish(String source) {
        Character[] characters =
                source.chars()
                        .mapToObj(ch -> (char) ch)
                        .toArray(Character[]::new);

//        Arrays.sort(characters, (c1, c2) -> {
//            int result = Character.compare(Character.toUpperCase(c1), Character.toUpperCase(c2));
//
//            if (result == 0) {
//                result = Character.compare(c1, c2);
//            }
//
//            return result;
//        });

        Arrays.sort(characters,
                Comparator.comparingInt((ToIntFunction<Character>) Character::toUpperCase)
                        .thenComparingInt(c -> c));

        return Arrays.stream(characters).map(ch -> ch.toString()).collect(Collectors.joining());
    }

    public String sortNumber(String source) {
        char[] chars = source.toCharArray();
        Arrays.sort(chars);

        return new String(chars);
    }

    public String mergeString(String str1, String str2) {
        int minLength = Math.min(str1.length(), str2.length());

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < minLength; i++) {
            result.append(str1.charAt(i)).append(str2.charAt(i));
        }

        try {
            result.append(str1.substring(minLength));
        } catch(StringIndexOutOfBoundsException e) {
            // Do nothing.
        }

        try {
            result.append(str2.substring(minLength));
        } catch(StringIndexOutOfBoundsException e) {
            // Do nothing.
        }

        return result.toString();
    }

    public String removeHtmlTag(String source) {
        return source.replaceAll("<[^>]*>", "");
    }
}
