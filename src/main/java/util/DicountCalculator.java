package util;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DicountCalculator {
    public static double sellingPriceAfterDiscount(double mrp,double discount){
        double loss = (discount/100)*mrp;
        return mrp-loss;
    }
    public static List<String> searchList(String word,List<String> list){
        List<String> searchWord = Arrays.asList(word.trim().split(" "));
        return list.stream().filter(input->{
            return searchWord.stream().allMatch(words-> input.toLowerCase().contains(words.toLowerCase()));
        }).collect(Collectors.toList());
    }
}
