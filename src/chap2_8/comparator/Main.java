package chap2_8.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) {


        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("홍길동",15,79));
        studentList.add(new Student("오로라핑",18,98));
        studentList.add(new Student("대길이",30,40));
        studentList.add(new Student("춘식이",7,20));

        // 이 리스트를 나이순으로 오름차 정렬 (나이 적은 순서)
        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getAge() - o1.getAge();
            }
        });

        studentList.forEach(System.out::println);


        System.out.println("=============================");

        // 이름 가나다순  정렬
        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        studentList.forEach(System.out::println);

        System.out.println("=============================");

        // 성적 오름차 순
        //studentList.sort(Comparator.comparing(student -> student.getScore()));

        // 성적 오름차 순
        studentList.sort(comparing(Student::getScore).reversed());

        studentList.forEach(System.out::println);

        // 이름 오름차 순
        studentList.sort(comparing(Student::getName));

        studentList.forEach(System.out::println);

    }
}
