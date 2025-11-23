package chap1_2;

import java.io.IOException;
import java.util.Scanner;

public class StandardIO {

    public static void main(String[] args) throws IOException {


        // 자바의 표준 출력
        System.out.println("안녕");
        System.out.print("메롱\n");

        int month= 6;
        int day = 6;
        System.out.printf("오늘은 %d월 %d일입니다.\n",month,day);
        System.out.println("하하호호");
        /*
            %d -> 10진수 정수
            %s -> 문자열
            %c -> 문자 하나
            %f -> 실수
         */

        // 자바 15 부터 가능
        String message = "오늘은 %d월 %d일 입니다.".formatted(month,day);
        System.out.println("message = " + message);

        // 자바 5부터 가능
        String message2= String.format("오늘은 %d월 %d일입니다.",month,day);



        // 표준 입력
        Scanner scanner = new Scanner(System.in);

        System.out.print("이름:");
        String name = scanner.nextLine(); // 입력커서 생성

        System.out.print("나이:");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("name = " + name);
        System.out.println("age = " + age);

    }
}
