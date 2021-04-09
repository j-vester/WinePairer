package winepairer.api.dto;

public class WinePairing {
    public WinePairing(winepairer.domain.WinePairer winepairer, String mealInput) {
        foods = new Food[winepairer.getFoods().size()];
        for (int i=0; i<foods.length; i++) {
            winepairer.domain.Food food = winepairer.getFoods().get(i);
            if (food.getName().equals(mealInput)) {
                foods[i] = new Food(food, true);
            } else {
                foods[i] = new Food(food, false);
            }
        }
    }

    Food[] foods;
    public Food[] getFoods() { return foods; }
}