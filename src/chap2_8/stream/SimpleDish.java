package chap2_8.stream;

import java.util.Objects;

public class SimpleDish {

    private String menuName;

    private int Calories;

    public SimpleDish() {
    }

    public SimpleDish(Dish menu) {
        this.menuName = menu.getName();
        this.Calories = menu.getCalories();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SimpleDish that)) return false;
        return Calories == that.Calories && Objects.equals(menuName, that.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName, Calories);
    }


    @Override
    public String toString() {
        return "{" +
                "menuName='" + menuName + '\'' +
                ", Calories=" + Calories +
                '}';
    }
}
