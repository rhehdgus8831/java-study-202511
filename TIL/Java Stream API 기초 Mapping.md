
# 📒 학습 노트: Java Stream API - Mapping

## 1. 매핑(Mapping)의 핵심 개념

스트림의 **매핑**은 스트림 내의 요소들을 특정 형태로 변환하거나, 요소에서 특정 값만을 추출하여 새로운 스트림을 형성하는 중간 연산입니다.

* **특징**: 입력 스트림의 타입 를 출력 스트림의 타입 로 변환합니다 ().
* **대표 메서드**: `map()`, `mapToInt()`, `flatMap()` 등.

---

## 2. 주요 매핑 메서드 요약

| 메서드 | 설명 | 리턴 타입 |
| --- | --- | --- |
| `map(Function<T, R>)` | 요소를 다른 형태나 타입으로 변환 | `Stream<R>` |
| `mapToInt(ToIntFunction<T>)` | 요소를 `int` 기본형으로 변환 (산술 연산에 최적화) | `IntStream` |
| `collect(Collector)` | 스트림의 요소를 수집하여 리스트, 셋, 맵 등으로 변환 | `List<T>`, `Set<T>` 등 |

---

## 3. 실전 코드 분석

### 3-1. 단순 타입 변환 및 추출

가장 기본적인 매핑 사용법으로, 객체에서 특정 필드 값만 뽑아내거나 데이터의 형식을 바꿀 때 사용합니다.

```java
// 1. 요리의 이름들만 추출 (String 리스트로 변환)
List<String> dishNames = menuList.stream()
        .map(Dish::getName) // Dish 객체를 String(이름)으로 매핑
        .collect(toList());

System.out.println("dishNames = " + dishNames);

// 2. 브라우저 목록에서 각 글자수 추출
List<String> browsers = List.of("safari", "chrome", "ms edge", "opera", "firefox");
List<Integer> browserLengths = browsers.stream()
        .map(String::length) // String을 Integer(길이)로 매핑
        .collect(toList());

// 3. 각 브라우저의 첫 글자만 추출
List<String> browsersFirstStr = browsers.stream()
        .map(b -> b.substring(0,1)) // 람다식을 이용한 부분 문자열 추출
        .collect(toList());

```

---

### 3-2. 객체 변환 (DTO 및 Map 포장)

데이터베이스나 원본 리스트에서 필요한 정보만 골라 새로운 객체(DTO)나 `Map`에 담는 과정입니다. 현업에서 매우 자주 쓰이는 패턴입니다.

#### [SimpleDish.java] - 변환용 클래스

원본 `Dish` 객체에서 이름과 칼로리만 따로 관리하기 위한 클래스입니다.

```java
public class SimpleDish {
    private String menuName;
    private int Calories;

    public SimpleDish() {}

    // Dish 객체를 인수로 받아 필요한 필드만 복사하는 생성자
    public SimpleDish(Dish menu) {
        this.menuName = menu.getName();
        this.Calories = menu.getCalories();
    }
    
    // Getter, Setter, equals, hashCode, toString 생략...
}

```

#### [Mapping.java] - 변환 로직

```java
// 1. Map<String, Object> 구조로 매핑
List<Map<String, Object>> menuDetails = menuList.stream()
        .map(menu -> {
            Map<String, Object> memuMap = new HashMap<>();
            memuMap.put("menuName", menu.getName());
            memuMap.put("calories", menu.getCalories());
            return memuMap;
        }).collect(toList());

// 2. 커스텀 객체(SimpleDish)로 매핑
List<SimpleDish> simpleDishList = menuList.stream()
        .map(SimpleDish::new) // 생성자 참조를 이용한 객체 생성
        .collect(toList());

```

---

### 3-3. 필터링과 매핑의 조합

특정 조건으로 데이터를 먼저 걸러낸 후(`filter`), 남은 데이터에서 필요한 값만 추출(`map`)합니다.

```java
/*
    요리 목록에서 500칼로리 이상의 메뉴들의 메뉴 이름만 추출
 */
List<String> highCalorieDishes = menuList.stream()
        .filter(m -> m.getCalories() >= 500) // 1. 조건 필터링
        .map(m -> m.getName())               // 2. 이름만 추출
        .collect(toList());

/*
    500칼로리 초과 음식의 이름과 타입(한글 변환) 추출
 */
List<Object> menuTypeOrName = menuList.stream()
        .filter(m -> m.getCalories() >= 500)
        .map(menu -> {
            Map<String, Object> menuMap = new HashMap<>();
            menuMap.put("이름", menu.getName());
            // 내부 enum 혹은 객체의 타입을 한글 명칭으로 매핑
            menuMap.put("타입", menu.getType().getTypeName()); 
            return menuMap;
        }).collect(toList());

```

---

### 3-4. 숫자형 스트림 (Primitive Stream)

산술 계산(합계, 평균)이 필요한 경우 일반 `Stream<Integer>`보다 `IntStream`을 사용하는 것이 효율적입니다.

```java
// 요리의 총 칼로리 수 구하기 (sum)
int totalCalories = menuList.stream()
        .mapToInt(m -> m.getCalories()) // IntStream으로 변환
        .sum();

// 평균 칼로리 구하기 (average)
double averageCalories = menuList.stream()
        .mapToInt(d -> d.getCalories())
        .average()      // OptionalDouble 반환
        .getAsDouble(); // 실제 double 값 추출

```

---

## 💡 요약 및 팁

1. **연쇄 호출**: 스트림은 `filter().map().collect()`와 같이 파이프라인 형태로 연결하여 가독성을 높일 수 있습니다.
2. **데이터 가공**: 원본 데이터를 변경하지 않고, 필요한 형태의 **복사본(View)**을 만드는 것이 스트림 매핑의 핵심입니다.
3. **성능**: 단순 산술 연산은 `mapToInt`, `mapToDouble` 등의 기본형 스트림을 사용하여 박싱/언박싱 비용을 줄이세요.

---

