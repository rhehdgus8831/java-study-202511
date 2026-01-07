package chap2_8.stream;

public class Dish {

    private String name; // 요리 이름
    private boolean vegetarian; // 채식주의 여부
    private int calories; // 음식 칼로리
    private DishType type; // 요리 종류

    public Dish() {
    }

    public Dish(String name, boolean vegetarian, int calories, DishType type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }


}



