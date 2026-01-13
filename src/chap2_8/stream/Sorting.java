package chap2_8.stream;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting {

    public static void main(String[] args) {

        // 육류 요리 중 칼로리가 낮은 순으로 정렬하기
        List<Dish> lowCalorieMeats = Menu.menuList.stream()
                .filter(menu -> menu.getType() == DishType.MEAT)
                .sorted(Comparator.comparing(Dish::getCalories))
                .collect(Collectors.toList());

        lowCalorieMeats.forEach(System.out::println);

        System.out.println("=============================");

        // 메뉴들을 이름 내림차로 정렬 (zyx순)

        List<Dish> sortedDishesByName = Menu.menuList.stream()
                .sorted(Comparator.comparing(Dish::getName).reversed())
                .collect(Collectors.toList());

        sortedDishesByName.forEach(System.out::println);

        System.out.println("=============================");

        // 300 칼로리 이상인 요리 중 칼로리가 낮은 탑 3 요리를 필터링
        List<Dish> lowCalorieDishes = Menu.menuList.stream()
                .filter(menu -> menu.getCalories() >= 300)
                .sorted(Comparator.comparing(Dish::getCalories))
                .limit(3)
                .collect(Collectors.toList());

        lowCalorieDishes.forEach(System.out::println);
    }
}
