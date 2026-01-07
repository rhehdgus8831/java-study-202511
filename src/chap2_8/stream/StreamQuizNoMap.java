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
                // .filter(...)
                .filter(menu -> menu.getType().equals(DishType.FISH))
                .collect(Collectors.toList());

        quiz1.forEach(System.out::println);


        System.out.println("\n====== [Quiz 2] ======");
        // 2. '육류(MEAT)'가 아니면서, 칼로리가 500 이하인 요리만 골라내세요.
        // (힌트: 논리 연산자 && 와 부정 연산자 ! 또는 != 사용)
        List<Dish> quiz2 = menuList.stream()
                // .filter(...)
                .filter(menu -> !menu.getType().equals(DishType.MEAT) && menu.getCalories() < 500)
                .collect(Collectors.toList());

        quiz2.forEach(System.out::println);


        System.out.println("\n====== [Quiz 3] ======");
        // 3. 모든 요리를 '칼로리가 낮은 순서(오름차순)'로 정렬해서 리스트에 담으세요.
        // (힌트: Comparator.comparingInt(Dish::getCalories))
        List<Dish> quiz3 = menuList.stream()
                // .sorted(...)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .collect(Collectors.toList());

        quiz3.forEach(System.out::println);


        System.out.println("\n====== [Quiz 4] ======");
        // 4. 칼로리가 높은 순서대로 정렬한 후, 앞에서 3개만 잘라서 가져오세요.
        // (힌트: sorted().reversed() -> limit())
        List<Dish> quiz4 = menuList.stream()
                // .sorted(...)
                .sorted((a,b) -> Integer.compare(b.getCalories(), a.getCalories()))
                // .limit(...)
                .limit(3)
                .collect(Collectors.toList());

        quiz4.forEach(System.out::println);


        System.out.println("\n====== [Quiz 5] ======");
        // 5. 칼로리가 400 이상인 요리들을 정렬하지 않고 순서대로 볼 때,
        //    앞에서 2개는 건너뛰고(skip), 그 다음 3개만 가져오세요.
        List<Dish> quiz5 = menuList.stream()
                // .filter(...)
                .filter(menu -> menu.getCalories() >= 400)
                // .skip(...)
                .skip(2)
                // .limit(...)
                .limit(3)
                .collect(Collectors.toList());

        quiz5.forEach(System.out::println);


        System.out.println("\n====== [Quiz 6] ======");
        // 6. (보너스) '채식주의자(Vegetarian)' 요리가 하나라도 있는지 확인하세요.
        // (리스트를 반환하는 게 아니라 boolean 값을 반환합니다.)
        boolean isAnyVegetarian = menuList.stream()
                // .anyMatch(...) // 힌트: 조건식 넣기
                .anyMatch(menu -> menu.isVegetarian());

        System.out.println("채식 요리가 있나요? " + isAnyVegetarian);
    }
}