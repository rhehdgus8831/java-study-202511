package chap1_2.array;

import java.util.Arrays;

// 배열 데이터 맨 끝 제거
public class ArrayPop {

    public static void main(String[] args) {

        int[] numbers = {10, 20, 30, 40, 50, 60};

        // pop: 맨 끝 데이터를 자우면서 가져옴
        // 0. 지워질 데이터를 백업
        int delTarget = numbers[numbers.length -1];

        // 1. 기존 사이즈보다 1개 작은 배열 생성
        int[] temp = new int[numbers.length -1];

        // 2. 새 배열의 맨 끝 데이터만 뺴고 복사
        for (int i = 0; i < numbers.length -1; i++) {
            temp[i] = numbers[i];
        }
        // 3. 주소값 이전
        numbers = temp;
        temp = null;

        System.out.println(Arrays.toString(numbers));
        System.out.println("삭제된 데이터:" + delTarget);

    }
}
