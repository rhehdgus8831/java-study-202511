package chap2_8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static chap2_8.stream.Menu.*;

public class Filtering {
    public static void main(String[] args) {

//         // 요리 메뉴 중 채식주의자가 먹을 수 있는 요리만 필터링
//        List<Dish> vegetarianList = new ArrayList<>();
//
//        for (Dish dish : Menu.menuList) {
//            if (dish.isVegetarian()){
//                vegetarianList.add(dish);
//            }
//        }

        List<Dish> vegetarianList = menuList.stream() // 1. 데이터 소스 생성
                .filter(Dish::isVegetarian)           // 2. 중간 연산
                .collect(Collectors.toList());        // 3. 최종 연산

        //System.out.println("vegetarianList = " + vegetarianList);

        vegetarianList.forEach(System.out::println);

        System.out.println("=============================");

        // 메뉴 중 육류이면서 600칼로리 미만인 요리 필터링
        List<Dish> meatMenu = menuList.stream()
                .filter(menu -> menu.getType().equals(DishType.MEAT) && menu.getCalories() <= 600)
                .collect(Collectors.toList());

        meatMenu.forEach(System.out::println);

        System.out.println("=============================");

        // 메뉴 중에 요리 이름이 4글자인 것만 필터링
        List<Dish> menuLength4 = menuList.stream()
                .filter(menuList -> menuList.getName().length() == 4)
                .collect(Collectors.toList());

        menuLength4.forEach(System.out::println);

        System.out.println("=============================");

        // 300칼로리보다 큰 요리 중 앞에서 3개만 필터링
        List<Dish> calorie300 = menuList.stream()
                .filter(menu -> menu.getCalories() > 300)
                .limit(3) // 큰거 3개
                .collect(Collectors.toList());

        calorie300.forEach(System.out::println);

        System.out.println("=============================");
        menuList.stream()
                .filter(menu -> menu.getCalories() > 300)
                .sorted((m1, m2) -> Integer.compare(m2.getCalories(), m1.getCalories()))
                .skip(2) // 2개 제외
                .limit(3)
                .forEach(System.out::println);

        System.out.println("=============================");

        List<Integer> numbers = List.of(1,23,14,5,6,435,341,241,2,3,12,3,4,6,77,89,9,12,3123,1,1);

        List<Integer> collect = numbers.stream()
                .filter(n -> n % 2 == 1)
                .distinct() // 중복제거
                .collect(Collectors.toList());

        collect.forEach(System.out::println);



    }


}
