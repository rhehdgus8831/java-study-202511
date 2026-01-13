
# 📘 Java 정렬(Sorting) 심화 학습 노트: Comparator와 Stream

## 1. 개요 (Overview)

Java에서 객체 리스트를 정렬하는 방법은 크게 두 가지 흐름으로 나뉩니다.

1. **List 자체 정렬 (`List.sort`)**: 원본 리스트의 순서를 변경합니다. (`Comparator` 활용)
2. **Stream 정렬 (`Stream.sorted`)**: 원본은 그대로 두고, 정렬된 새로운 결과(List 등)를 생성합니다.

---

## 2. 객체 모델 (Data Model)

정렬 예제에 사용된 `Student` 클래스입니다. 정렬의 기준이 될 필드(`age`, `score`, `name`)를 가지고 있습니다.

```java
package chap2_8.comparator;

import java.util.Objects;

public class Student {

    private String name;
    private  int age;
    private  int score;

    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // 객체 비교 및 출력을 위한 오버라이딩
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return age == student.age && score == student.score && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, score);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}

```

---

## 3. 리스트 직접 정렬 (List.sort & Comparator)

`List` 인터페이스의 `sort()` 메서드를 사용하여 원본 리스트를 정렬하는 방법입니다. Java 버전에 따른 스타일 변화를 확인할 수 있습니다.

### 💡 핵심 개념: Comparator.compare()

* **반환값 < 0**: o1이 o2보다 앞에 옴 (순서 유지/변경 없음)
* **반환값 > 0**: o1이 o2보다 뒤에 옴 (순서 바꿈)
* **반환값 == 0**: 같음
* **Tip**: `o1 - o2`는 오름차순, `o2 - o1`은 내림차순 공식처럼 사용됩니다.

### 💻 소스 코드 (`Main.java`)

```java
package chap2_8.comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 정적 임포트를 사용하면 Comparator.comparing 등을 comparing으로 줄여 쓸 수 있음
import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) {


        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("홍길동",15,79));
        studentList.add(new Student("오로라핑",18,98));
        studentList.add(new Student("대길이",30,40));
        studentList.add(new Student("춘식이",7,20));

        // [Case 1] 익명 내부 클래스 방식 (Anonymous Inner Class)
        // 이 리스트를 나이순으로 오름차 정렬 (나이 적은 순서)
        // 주의: 코드 상 o2 - o1은 내림차순 로직이나, 주석은 오름차순이라 되어 있음. 
        // 실제 동작: o2.getAge() - o1.getAge() => 내림차순(나이 많은 순) 정렬됨
        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getAge() - o1.getAge();
            }
        });

        studentList.forEach(System.out::println);


        System.out.println("=============================");

        // [Case 2] 이름 가나다순 정렬 (String의 compareTo 활용)
        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        studentList.forEach(System.out::println);

        System.out.println("=============================");

        // [Case 3] Java 8 이후: 메서드 참조와 Comparator 유틸리티 활용
        
        // 성적 오름차 순 (주석 처리된 람다식 버전)
        //studentList.sort(Comparator.comparing(student -> student.getScore()));

        // 성적 오름차 순 (reversed를 사용하여 내림차순으로 변경 가능)
        // import static java.util.Comparator.*; 덕분에 comparing() 바로 사용
        studentList.sort(comparing(Student::getScore).reversed());

        studentList.forEach(System.out::println);

        // 이름 오름차 순
        studentList.sort(comparing(Student::getName));

        studentList.forEach(System.out::println);

    }
}

```

---

## 4. 스트림 API 정렬 (Stream.sorted)

원본 데이터를 건드리지 않고, 필터링(filtering)과 정렬(sorting)을 거쳐 **새로운 리스트**를 만들어내는 선언형 스타일입니다.

### 💡 핵심 개념: Stream Pipeline

1. **Source**: `stream()`으로 스트림 생성.
2. **Intermediate Operations**: `filter`, `sorted`, `limit` 등으로 데이터 가공. (반환값이 Stream)
3. **Terminal Operation**: `collect`, `forEach` 등으로 결과 도출.

### 💻 소스 코드 (`Sorting.java`)

```java
package chap2_8.stream;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting {

    public static void main(String[] args) {

        // [Case 1] 단순 필터링 후 정렬
        // 육류 요리 중 칼로리가 낮은 순으로 정렬하기
        List<Dish> lowCalorieMeats = Menu.menuList.stream()
                .filter(menu -> menu.getType() == DishType.MEAT)  // 고기 종류만 필터링
                .sorted(Comparator.comparing(Dish::getCalories))  // 칼로리 기준 오름차순
                .collect(Collectors.toList());                    // 리스트로 변환

        lowCalorieMeats.forEach(System.out::println);

        System.out.println("=============================");

        // [Case 2] 역순 정렬 (Reversed)
        // 메뉴들을 이름 내림차로 정렬 (zyx순)
        List<Dish> sortedDishesByName = Menu.menuList.stream()
                .sorted(Comparator.comparing(Dish::getName).reversed()) // 이름 기준 오름차순 -> 뒤집기(내림차순)
                .collect(Collectors.toList());

        sortedDishesByName.forEach(System.out::println);

        System.out.println("=============================");

        // [Case 3] 복합 처리 (필터링 -> 정렬 -> 개수 제한)
        // 300 칼로리 이상인 요리 중 칼로리가 낮은 탑 3 요리를 필터링
        List<Dish> lowCalorieDishes = Menu.menuList.stream()
                .filter(menu -> menu.getCalories() >= 300)       // 300 칼로리 이상 필터링
                .sorted(Comparator.comparing(Dish::getCalories)) // 칼로리 낮은 순(오름차순) 정렬
                .limit(3)                                        // 앞에서부터 3개만 자름
                .collect(Collectors.toList());

        lowCalorieDishes.forEach(System.out::println);
    }
}

```

*(참고: `Dish`, `Menu`, `DishType` 클래스는 코드에 포함되지 않았으나, 스트림 로직 이해에는 무리가 없습니다.)*

---

## 5. 핵심 요약 및 메서드 정리

### 🔑 Comparator 주요 메서드

`Comparator` 인터페이스의 static 및 default 메서드를 활용하면 코드가 매우 간결해집니다.

| 메서드 | 설명 | 사용 예시 |
| --- | --- | --- |
| **`comparing(KeyExtractor)`** | 해당 키(필드)를 기준으로 오름차순 정렬 `Comparator` 생성 | `comparing(Student::getScore)` |
| **`reversed()`** | 정렬 순서를 반대로 뒤집음 (내림차순) | `comparing(...).reversed()` |
| **`thenComparing(...)`** | 1차 정렬 조건이 같을 때 사용할 2차 정렬 조건 지정 | `comparing(...).thenComparing(...)` |

### 🔑 Stream 주요 메서드

| 메서드 | 구분 | 설명 |
| --- | --- | --- |
| **`filter(Predicate)`** | 중간 연산 | 조건이 `true`인 요소만 남김 |
| **`sorted(Comparator)`** | 중간 연산 | 주어진 기준에 따라 스트림 요소를 정렬 |
| **`limit(long n)`** | 중간 연산 | 앞에서부터 n개의 요소만 가져옴 |
| **`collect(Collector)`** | 최종 연산 | 스트림의 요소를 List, Set 등으로 변환 (`Collectors.toList()`) |

> **초보자를 위한 팁**:
> 복잡한 정렬 로직이 필요할 때는 `compare` 메서드를 직접 구현(익명 클래스)하는 것이 이해하기 쉬울 수 있지만, 단순한 필드 기준 정렬은 `Comparator.comparing(클래스::메서드)` 패턴을 익혀두는 것이 가독성과 유지보수 면에서 훨씬 유리합니다.