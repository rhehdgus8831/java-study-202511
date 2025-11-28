package chap1_2.array;

import java.util.Arrays;

// 데이터 원하는 위치에 삽입하기
public class ArrayInsert {

    public static void main(String[] args) {

        int[] numbers = {10,50,90,100,150};

        int targetIndex = 2;
        int newNumber = 70;

        /*
            먼저 사이즈가 1개 큰 새 배열부터 생성
            [10,50,90,100,150,0]
            [10,50,90,100,150,150]
            [10,50,90,100,100,150]
            [10,50,90,90,100,150]
            [10,50,70,90,100,150]
         */

        // 1. 사이즈가 1칸 큰 배열 생성
        int[] temp = new int[numbers.length +1];

        // 2. 기존 데이터 새 배열로 복사
        for (int i = 0; i < numbers.length ; i++) {
            temp[i] = numbers[i];
        }
        for (int i = numbers.length; i > targetIndex; i--) {
            temp[i] = temp[i-1];
        }
        temp[targetIndex] = newNumber;
        numbers = temp;

        System.out.println(Arrays.toString(temp));
    }
}
