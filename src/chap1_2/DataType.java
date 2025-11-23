package chap1_2;

public class DataType {

    public static void main(String[] args) {

        // 정적 타이핑 언어

        // 숫자
        int age = 20;
        // 실수
        double pi = 3.14;
        // 한글자
        char c = 'A';
        // 문자
        String greeting = "안녕하세요";
        // 논리값
        boolean flag = true;

        // 정수 타입 ( byte: 1, short: 2, int: 4, long: 8 )

        // 1byte == 8bit -> 10진수 정수 8 => 0 0001000
        // -128 ~ 127
        byte b = 127;

        // 2byte == 16bit -> 10진수 정수 100 => 1100100
        // -32768 ~ 32767
        short s = 32767;

        int i = 2147483647;
        long l = 9223372036854775807L;


        // 실수 타입

        float f1 = 3.123123111F; // float 4byte
        double d1 = 3.123123111; // double 8byte
        System.out.println("f1 = " + f1);
        System.out.println("d1 = " + d1);


        // 논리 타입
        boolean b1 = true;
        boolean b2 = false;
        System.out.println("b1 = " + b1);
        System.out.println("b2 = " + b2);


        // 문자 타입
        char c1 = 'A'; // 2byte
        char c2 = 44032;
        System.out.println("c2 = " + c2);

        char[] cArr = {'A', 'B', 'C'};
        String str = new String(cArr);

        System.out.println("str = " + str);

        String str2 = "안녕하세요";

        // 자바 17부터 지원
        String str3 = """
                안녕
                메롱
                잘가
                """;




    }
}
