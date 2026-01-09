package chap2_8.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static chap2_8.stream.Menu.*;
import static java.util.stream.Collectors.*;

public class Mapping {

    public static void main(String[] args) {

        // 요리의 이름들만 추출
        List<String> dishNames = menuList.stream()
                .map(Dish::getName)
                .collect(toList());

        System.out.println("dishNames = " + dishNames);

        System.out.println("=============================");

        List<String> browsers = List.of("safari", "chrome", "ms edge", "opera", "firefox");

        // 브라우저 목록에서 브라우저의 각 글자수를 수집
        List<Integer> browserLengths = browsers.stream()
                .map(String::length)
                .collect(toList());

        System.out.println("browserLengths = " + browserLengths);

        // 브라우저 목록에서 각 브라우저의 첫 글자만 추출

        List<String> browsersFirstStr = browsers.stream()
                .map(b -> b.substring(0,1))
                .collect(toList());

        System.out.println("browsersFirstStr = " + browsersFirstStr);

        // 요리 목록에서 요리 이름과 칼로리만 추출해서
        // 새 객체에 담아 포장하여 리스트에 담고 싶음.

        List<Map<String, Object>> menuDetails = menuList.stream()
                .map(menu -> {
                    Map<String, Object> memuMap = new HashMap<>();
                    memuMap.put("menuName", menu.getName());
                    memuMap.put("calories", menu.getCalories());
                    return memuMap;
                }).collect(toList());

        menuDetails.forEach(System.out::println);

        System.out.println("=============================");

        List<SimpleDish> simpleDishList = menuList.stream()
                .map(SimpleDish::new)
                .collect(toList());

        simpleDishList.forEach(System.out::println);

        System.out.println("=============================");

        /*
            요리 목록에서 500칼로리 이상의 메뉴들의 메뉴 이름만 추출
         */
        List<String> highCalorieDishes = menuList.stream()
                .filter(m -> m.getCalories() >= 500)
                .map(m -> m.getName())
                .collect(toList());

        System.out.println("highCalorieDishes = " + highCalorieDishes);


        System.out.println("=============================");

        /*
            메뉴 목록에서 칼로리가 500칼로리보다 큰
            음식들을 필터링한 다음에 음식의 이름과 타입만
            추출해서 출력해주세요.

            단, 타입은 MEAT의 경우 육류라고 저장
            FISH는 어류라고 저장, OTHER 기타라고 저장
         */

        List<Object> menuTypeOrName = menuList.stream()
                .filter(m -> m.getCalories() >= 500)
                .map(menu -> {
                    Map<String, Object> menuMap = new HashMap<>();
                    menuMap.put("이름", menu.getName());
                    menuMap.put("타입", menu.getType().getTypeName());
                    return menuMap;
                }).collect(toList());

        System.out.println("menuTypeOrName = " + menuTypeOrName);

        // 요리의 총 칼로리 수 구하기
        int totalCalories = menuList.stream()
                .mapToInt(m -> m.getCalories())
                .sum();

        System.out.println("totalCalories = " + totalCalories);
        
        // 평균 칼로리
        double averageCalories = menuList.stream()
                .mapToInt(d -> d.getCalories())
                .average()
                .getAsDouble();

        System.out.println("averageCalories = " + averageCalories);
    }
}
