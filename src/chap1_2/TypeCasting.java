package chap1_2;

public class TypeCasting {

    public static void main(String[] args) {

        // java는 타입이 다른 테이터의 연산을 지원하지 않음
        byte a =100;
        int b = a; // 암묵적 형 변환 (byte -> int) int가 더 크기 때문에 가능
        System.out.println(b);

        int c =1000;
        byte d = (byte) c; // int: 4 -> byte: 1
        // 명시적 형 변환 - 데이터 손실이 발생할 수 있음
        System.out.println(d);

        /**
         * 암묵적(묵시적) 형 변환 -> upcasting (promotion)
         * 명시적 형 변환 -> downcasting (demotion)
         */


        // 타입이 다른 데이터끼리의 연산은
        // 암묵적 형변환에 의해 작은 데이터가 큰 데이터로 변환됨

        int v = 91;
        double z = 6.6;
        double result = v + z;

        int result2 = 'A' + v;
        System.out.println("result2 = " + result2);

        int h =5;
        double result3 = 27 /(double) h;
        System.out.println("result3 = " + result3);

        // int보다 작은 데이터 (char, short, byte) 들은
        // 연산 결과가 무조건 int다
        byte b1 = 100;
        byte b2 = 70;
        int result4 = (b1 + b2);

        // char + char = int
        System.out.println('A'+'C');
    }
}
