
# [Java] Stream API 기초: Filtering, Sorting, Slicing

**학습 일자:** 2025-01-07
**주제:** Java 8 Stream API의 핵심인 데이터 필터링, 정렬, 범위 자르기, 매칭 익히기

---

## 1. 학습 개요

스트림(Stream)은 데이터 컬렉션(List 등)을 함수형 스타일로 처리하는 기술입니다.
오늘은 **"데이터를 조건에 맞춰 골라내고(Filter), 순서를 정하고(Sorted), 필요한 만큼 자르는(Limit/Skip)"** 과정을 중점적으로 학습했습니다.

### **핵심 흐름**
1.  **생성**: `stream()`으로 스트림을 엽니다.
2.  **중간 연산**: `filter`, `sorted`, `limit` 등으로 데이터를 가공합니다. (결과물: Stream)
3.  **최종 연산**: `collect`, `anyMatch` 등으로 결과를 도출합니다. (결과물: List, boolean 등)

---

## 2. 데이터 모델 (Data Setup)

실습에 사용된 기본 클래스와 데이터입니다.

### **Dish.java (요리 객체)**
```java
package chap2_8.stream;

import java.util.Objects;

public class Dish {
    private String name;        // 요리 이름
    private boolean vegetarian; // 채식주의 여부
    private int calories;       // 칼로리
    private DishType type;      // 종류 (MEAT, FISH, OTHER)

    // 생성자, Getter/Setter, toString...
    
    // [중요] boolean 타입의 Getter는 'is'로 시작하는 것이 관례
    public boolean isVegetarian() {
        return vegetarian;
    }
}

```

### **DishType.java (Enum)**

```java
public enum DishType {
    MEAT("육류"), FISH("어류"), OTHER("기타");
    // ...
}

```

---

## 3. 기초 실습: 필터링과 슬라이싱 (`Filtering.java`)

기본적인 `filter`, `limit`, `skip`, `distinct` 사용법입니다.

```java
package chap2_8.stream;

import java.util.List;
import java.util.stream.Collectors;
import static chap2_8.stream.Menu.*;

public class Filtering {
    public static void main(String[] args) {

        // 1. 기본 필터링: 채식주의자 요리만 추출
        List<Dish> vegetarianList = menuList.stream() 
                .filter(Dish::isVegetarian) // 메서드 참조 (dish -> dish.isVegetarian() 과 동일)
                .collect(Collectors.toList());

        // 2. 다중 조건: 육류이면서 600칼로리 미만
        List<Dish> meatMenu = menuList.stream()
                .filter(menu -> menu.getType().equals(DishType.MEAT) && menu.getCalories() <= 600)
                .collect(Collectors.toList());

        // 3. 고유 요소 필터링: 이름이 4글자인 요리
        List<Dish> menuLength4 = menuList.stream()
                .filter(dish -> dish.getName().length() == 4)
                .collect(Collectors.toList());

        // 4. 요소 제한(Limit): 300칼로리 초과 중 앞에서 3개만
        List<Dish> calorie300 = menuList.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3) // 상위 3개만 선택하고 스트림 닫기
                .collect(Collectors.toList());

        // 5. 건너뛰기(Skip)와 정렬 혼합
        System.out.println("=== 300칼로리 초과, 고칼로리순 정렬, 앞 2개 제외, 3개 선택 ===");
        menuList.stream()
                .filter(menu -> menu.getCalories() > 300)
                .sorted((m1, m2) -> Integer.compare(m2.getCalories(), m1.getCalories())) // 내림차순
                .skip(2)  // 처음 2개 요소 건너뜀
                .limit(3) // 그 다음 3개 요소 선택
                .forEach(System.out::println);

        // 6. 숫자 중복 제거 (Distinct)
        List<Integer> numbers = List.of(1, 2, 1, 3, 3, 2, 4);
        List<Integer> distinctNumbers = numbers.stream()
                .filter(n -> n % 2 == 1) // 홀수만
                .distinct()              // 중복 제거 (equals, hashCode 기반)
                .collect(Collectors.toList());
    }
}

```

---

## 4. 심화 실습: 퀴즈 풀이 (`StreamQuizNoMap.java`)

`map` 없이 필터링과 정렬만으로 데이터를 조작하는 심화 예제입니다.

```java
package chap2_8.stream;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static chap2_8.stream.Menu.*;

public class StreamQuizNoMap {
    public static void main(String[] args) {

        System.out.println("====== [Quiz 1] ======");
        // 1. '생선(FISH)' 종류의 요리만 모두 골라내세요.
        List<Dish> quiz1 = menuList.stream()
                .filter(menu -> menu.getType() == DishType.FISH) // Enum은 == 비교 권장 (Null Safe)
                .collect(Collectors.toList());
        quiz1.forEach(System.out::println);


        System.out.println("\n====== [Quiz 2] ======");
        // 2. '육류(MEAT)'가 아니면서, 칼로리가 500 '이하'인 요리
        // Point: 논리 연산자(!, &&) 활용
        List<Dish> quiz2 = menuList.stream()
                .filter(menu -> !menu.getType().equals(DishType.MEAT) && menu.getCalories() <= 500)
                .collect(Collectors.toList());
        quiz2.forEach(System.out::println);


        System.out.println("\n====== [Quiz 3] ======");
        // 3. 모든 요리를 '칼로리가 낮은 순서(오름차순)'로 정렬
        List<Dish> quiz3 = menuList.stream()
                .sorted(Comparator.comparingInt(Dish::getCalories)) // 깔끔한 Comparator 사용
                .collect(Collectors.toList());
        quiz3.forEach(System.out::println);


        System.out.println("\n====== [Quiz 4] ======");
        // 4. 칼로리가 높은 순서대로 정렬(내림차순) 후, 앞에서 3개만 선택
        List<Dish> quiz4 = menuList.stream()
                // 방법 1: 람다식 직접 구현 (개발자의 응용력!)
                .sorted((a, b) -> Integer.compare(b.getCalories(), a.getCalories()))
                // 방법 2: Comparator 활용 (가독성 추천)
                // .sorted(Comparator.comparingInt(Dish::getCalories).reversed())
                .limit(3)
                .collect(Collectors.toList());
        quiz4.forEach(System.out::println);


        System.out.println("\n====== [Quiz 5] ======");
        // 5. 칼로리 400 이상 -> 정렬 없이 -> 앞 2개 스킵 -> 3개 가져오기
        List<Dish> quiz5 = menuList.stream()
                .filter(menu -> menu.getCalories() >= 400)
                .skip(2)
                .limit(3)
                .collect(Collectors.toList());
        quiz5.forEach(System.out::println);


        System.out.println("\n====== [Quiz 6] ======");
        // 6. '채식주의자' 요리가 하나라도 있는지 확인 (boolean 반환)
        // Point: 최종 연산으로 collect 대신 anyMatch 사용
        boolean isAnyVegetarian = menuList.stream()
                .anyMatch(Dish::isVegetarian); // 메서드 참조 활용

        System.out.println("채식 요리가 있나요? " + isAnyVegetarian);
    }
}

```

---

## 5. 핵심 메서드 요약

오늘 사용한 주요 스트림 메서드 정리입니다.

| 메서드 | 분류 | 설명 | 예시 |
| --- | --- | --- | --- |
| **`filter`** | 중간 | 조건(Predicate)이 `true`인 요소만 남김 | `.filter(d -> d.isVegetarian())` |
| **`sorted`** | 중간 | 요소를 정렬함 (기본: 오름차순) | `.sorted()` 또는 `.sorted(Comparator)` |
| **`limit`** | 중간 | 앞에서부터 N개만 선택 | `.limit(3)` |
| **`skip`** | 중간 | 앞에서부터 N개를 건너뜀 | `.skip(2)` |
| **`distinct`** | 중간 | 중복된 요소를 제거 (`equals` 활용) | `.distinct()` |
| **`collect`** | 최종 | 스트림 결과를 컬렉션(List, Set 등)으로 변환 | `.collect(Collectors.toList())` |
| **`anyMatch`** | 최종 | 조건에 맞는 요소가 **하나라도** 있으면 `true` | `.anyMatch(d -> d.isVegetarian())` |

---

## 6. 리팩토링 팁 (Refactoring Tips)

**1. Enum 비교는 `==` 사용하기**

* `.filter(menu -> menu.getType().equals(DishType.FISH))` (O)
* `.filter(menu -> menu.getType() == DishType.FISH)` (Better)
* `==` 연산자는 `NullPointerException`에서 안전하고 코드가 더 간결합니다.



**2. 정렬(Sorting) 가독성 높이기**

* 람다식 직접 구현: `.sorted((a, b) -> b.getCalories() - a.getCalories())`
* Comparator 유틸리티: `.sorted(Comparator.comparingInt(Dish::getCalories).reversed())`
* 후자가 "무엇을 기준으로 역정렬하는지" 읽기가 훨씬 편합니다.



**3. 메서드 참조(Method Reference) 활용**

* 람다 표현식이 단순히 메서드 하나만 호출할 때는 `::` 연산자를 사용해 코드를 줄일 수 있습니다.
* `d -> d.isVegetarian()`  👉  `Dish::isVegetarian`
