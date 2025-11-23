package chap1_2.array;


import java.util.Arrays;

// 데이터 추가 알고리즘
public class ArrayPush {

    static int[] numbers = {10, 20, 30, 40};

    public static void main(String[] args) {




        int newNumber = 50;

        // 1. 기존 배열 사이즈보다 한 칸 더 큰 배열 생성
        int[] tempArray = new int[numbers.length + 1];

        // 2. 원본배열의 값들을 새 배열로 복사
        for (int i = 0; i <numbers.length ; i++) {
            tempArray[i] = numbers[i];
        }

        // 3. 마지막 인덱스에 새 값 저장
        tempArray[tempArray.length -1] = newNumber;

        // 4. 주소값 이전
        numbers = tempArray;
        tempArray = null;


        push(numbers, 90);
        push(numbers, 100);
        push(numbers, 110);


        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(tempArray));

    }

    static void push(int[] arr,int newData){
        arr = Arrays.copyOf(arr, arr.length +1);
        arr[arr.length -1] = newData;

        numbers = arr;
    }
}
