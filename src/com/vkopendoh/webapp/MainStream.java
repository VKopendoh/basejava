package com.vkopendoh.webapp;

import java.util.*;
import java.util.function.IntBinaryOperator;

public class MainStream {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 3, 2, 3};
        int[] arr2 = {9, 8};
        int[] arr3 = {0};

        System.out.println("Min values: ");
        System.out.println(minValue(arr1));
        System.out.println(minValue(arr2));
        System.out.println(minValue(arr3));

        System.out.println("Odd or even: ");
        List<Integer> iList = Arrays.asList(2, 3, 4, 1, 5, 8, 7, 1, 2, 1, 7, 3);
        oddOrEven(iList).forEach(System.out::println);
    }

    private static List<Integer> oddOrEven(List<Integer> iList) {
        List<Integer> resOdd = new ArrayList<>();
        List<Integer> resEven = new ArrayList<>();
        int sum = iList.stream().mapToInt(value -> {
            if (value % 2 == 0) {
                resEven.add(value);
            } else {
                resOdd.add(value);
            }
            return value;
        }).sum();
        return sum % 2 == 0 ? resEven : resOdd;
    }

    private static int minValue(int[] intArr) {
        return intArr.length > 0 ? Arrays.stream(intArr).distinct().sorted().reduce((x, y) -> x * 10 + y).getAsInt() : 0;
    }
}