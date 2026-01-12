
# Java Stream API: 매칭(Matching)과 검색(Finding)

데이터 컬렉션을 처리할 때 특정 조건에 만족하는 요소가 있는지 확인(**매칭**)하거나, 조건에 맞는 요소를 찾아내는(**검색**) 기능을 제공하는 스트림의 최종 연산(Terminal Operation)입니다.

---

## 1. 매칭 (Matching)

스트림의 요소들이 특정 **프레디케이트(Predicate, 조건)**를 충족하는지 검사합니다. 결과는 `boolean`으로 반환됩니다.

### **핵심 메서드**

| 메서드 | 설명 | 특징 |
| :--- | :--- | :--- |
| **`anyMatch`** | 스트림에서 **적어도 한 요소**가 조건과 일치하면 `true` | 쇼트 서킷 (조건 만족 시 즉시 종료) |
| **`allMatch`** | 스트림의 **모든 요소**가 조건과 일치하면 `true` | 하나라도 불일치 시 `false` 반환 후 종료 |
| **`noneMatch`** | 스트림의 **모든 요소**가 조건과 일치하지 **않으면** `true` | 하나라도 일치 시 `false` 반환 후 종료 |

### **예제 코드 (Matching)**

> **Note**: `anyMatch`는 조건에 맞는 요소를 하나라도 발견하는 즉시 연산을 끝냅니다 (Short-circuiting).

```java
// 요리 목록에서 채식주의자가 먹을 수 있는 요리가 하나라도 있는가?
boolean flag1 = Menu.menuList.stream()
        // .filter(menu -> menu.getType() == DishType.MEAT) // (주석 처리된 필터)
        .anyMatch(menu -> menu.isVegetarian()); // 하나라도 채식 요리면 true

System.out.println("flag1 = " + flag1);

// 요리 목록에서 모든 요리가 1000칼로리 미만입니까?
boolean flag2 = Menu.menuList.stream()
        .allMatch(menu -> menu.getCalories() < 1000);

System.out.println("flag2 = " + flag2);

```

---

## 2. 검색 (Finding)

스트림에서 특정 조건에 일치하는 요소를 찾아 반환합니다. 결과는 존재하지 않을 수 있으므로 `Optional<T>`로 감싸서 반환됩니다.

### **핵심 메서드**

| 메서드 | 설명 | 병렬 처리 시 특징 |
| --- | --- | --- |
| **`findFirst`** | 조건에 일치하는 요소 중 **첫 번째 요소**를 반환 | 순서가 중요할 때 사용 (논리적 첫 번째) |
| **`findAny`** | 조건에 일치하는 요소 중 **아무거나 하나**를 반환 | 병렬 스트림에서 성능이 더 좋음 (순서 무관) |

### **예제 코드 (Finding)**

> **Note**: `findFirst()`는 결과가 없을 경우를 대비해 `Optional` 객체를 반환합니다. 값을 꺼낼 때는 `.get()`, `.orElse()`, `.ifPresent()` 등을 사용합니다.

```java
// 요리 중에 첫번째 발견된 생선요리를 찾기 : find
Dish firstFishDishes = Menu.menuList.stream()
        .filter(menu -> menu.getType() == DishType.FISH) // 1. 생선 요리만 필터링
        .findFirst() // 2. 필터링된 것 중 첫 번째 요소 가져오기 (반환타입: Optional<Dish>)
        .get();      // 3. Optional 내부의 객체 꺼내기 (값이 없으면 예외 발생 가능)

System.out.println("firstFishDishes = " + firstFishDishes);

```

---

## 3. 요약 및 주의사항

1. **쇼트 서킷 (Short-circuiting)**
* `allMatch`, `anyMatch`, `noneMatch`, `findFirst`, `findAny`는 전체 스트림을 다 처리하지 않아도 결과를 결정할 수 있는 경우 즉시 종료됩니다. (성능 최적화)


2. **Optional 처리**
* `findFirst`와 `findAny`는 찾는 요소가 없을 수 있기 때문에 `Optional<T>`를 반환합니다.
* 실무에서는 `.get()` 보다는 안전한 `.orElse(defaultVal)`나 `.ifPresent(...)`를 사용하는 것이 권장됩니다.


3. **병렬 스트림 (Parallel Stream)**
* 병렬 처리 시 `findFirst`는 병목이 될 수 있으므로, 순서가 중요하지 않다면 `findAny`를 사용하는 것이 효율적입니다.

```