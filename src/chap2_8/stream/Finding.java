package chap2_8.stream;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Finding {

    public static void main(String[] args) {

        // 요리 목록에서 채식주의자가 먹을 수 있는 요리가 하나라도 있는가?
        boolean flag1 = Menu.menuList.stream()
                // .filter(menu -> menu.getType() == DishType.MEAT)
                .anyMatch(menu -> menu.isVegetarian());

        System.out.println("flag1 = " + flag1);

        // 요리 목록에서 모든 요리가 1000칼로리 미만입니까?
        boolean flag2 = Menu.menuList.stream()
                .allMatch(menu -> menu.getCalories() < 1000);

        System.out.println("flag2 = " + flag2);

        // 요리 중에 첫번째 발견된 생선요리를 찾기 : find
        Dish firstFishDishes = Menu.menuList.stream()
                .filter(menu -> menu.getType() == DishType.FISH)
                .findFirst()
                .get();

        System.out.println("firstFishDishes = " + firstFishDishes);
    }
}
