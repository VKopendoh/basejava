package com.vkopendoh.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStream {
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 3, 2, 3};
        int[] arr2 = {9, 8};
        System.out.println(minValue(arr1));
        System.out.println(minValue(arr2));

        List<Integer> iList = Arrays.asList(2, 3, 4, 1, 5, 8, 7, 1, 2);
        oddOrEven(iList).forEach(System.out::println);
    }

    private static List<Integer> oddOrEven(List<Integer> iList) {
        List<Integer> resOdd = new ArrayList<>();
        List<Integer> resEven = new ArrayList<>();
        int sum = iList.parallelStream().mapToInt(value -> {
            if (value % 2 == 0) {
                resEven.add(value);
            } else {
                resOdd.add(value);
            }
            return value;
        }).sum();
        return sum % 2 == 0 ? resEven : resOdd;
    }

    private static long minValue(int[] intArr) {
        double result = 0;
        int[] arr = Arrays.stream(intArr).parallel().distinct().sorted().toArray();
        for (int i = 0; i < arr.length; i++) {
            result = result + (arr[i] * Math.pow(10, arr.length - i - 1));
        }
        return (int) result;
    }
}