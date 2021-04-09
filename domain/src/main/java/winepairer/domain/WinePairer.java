package winepairer.domain;

import java.sql.*;
import java.util.*;
import org.apache.commons.text.similarity.LevenshteinDistance;
public class WinePairer {
    private final int MAX_LEVENSHTEIN = 3;
    private List<Food> foods = new ArrayList<>();

    public WinePairer(String mealInput) {
        obtainFoods(mealInput);
    }

    private void obtainFoods(String mealInput) {
        String mealInQuotes = "\""+mealInput.toLowerCase()+"\"";
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/wine_dine_pairing", 
                "root", 
                "winepairer1");
            Statement stmt = conn.createStatement();
            String getMealId = "SELECT food_id, gamechanger "+
                "FROM foods WHERE foods.name = "+ mealInQuotes;
            ResultSet rs_exactFood = stmt.executeQuery(getMealId);
            while (rs_exactFood.next()) {
                int foodId = rs_exactFood.getInt("food_id");
                boolean gamechanger = rs_exactFood.getBoolean("gamechanger");
                Food food = new Food(mealInput, gamechanger);
                obtainPairings(food, foodId);
            }
            if (this.foods.isEmpty()) {
                LevenshteinDistance lDist = new LevenshteinDistance(MAX_LEVENSHTEIN);
                String getAllFoods = "SELECT * FROM foods";
                ResultSet rs_allFoods = stmt.executeQuery(getAllFoods);
                while (rs_allFoods.next()) {
                    String compareTo = rs_allFoods.getString("name");
                    if (lDist.apply(mealInput, compareTo) != -1) {
                        int foodId = rs_allFoods.getInt("food_id");
                        boolean gamechanger = rs_allFoods.getBoolean("gamechanger");
                        Food food = new Food(compareTo, gamechanger);
                        obtainPairings(food, foodId);
                    }
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void obtainPairings(Food food, int foodId) {
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/wine_dine_pairing", 
                "root", 
                "winepairer1");
            String getSubFoods = "SELECT DISTICT food_specs FROM parings "+
                "WHERE pairings.food_id = "+ foodId;
            String getPairings = "SELECT * FROM pairings "+
                "WHERE pairings.food_specs = ?";
            String getWines = "SELECT * FROM wines "+
                "WHERE wines.wine_id = ?";
            Statement stmt = conn.createStatement();
            PreparedStatement getPairingsStmt = conn.prepareStatement(getPairings);
            PreparedStatement getWinesStmt = conn.prepareStatement(getWines);
            ResultSet rs_subFoods = stmt.executeQuery(getSubFoods);
            while (rs_subFoods.next()) {
                String subFoodName = rs_subFoods.getString("food_specs");
                Food subFood = new Food(subFoodName, food.isGamechanger());
                getPairingsStmt.setString(1, subFoodName);
                ResultSet rs_pairings = getPairingsStmt.executeQuery();
                while (rs_pairings.next()) {
                    int wineId = rs_pairings.getInt("wine_id");
                    String wineName = rs_pairings.getString("wine_specs");
                    getWinesStmt.setInt(1, wineId);
                    ResultSet rs_wines = getWinesStmt.executeQuery();
                    while (rs_wines.next()) {
                        WineColour colour = WineColour.valueOfName(rs_wines.getString("colour"));
                        Wine wine = new Wine(colour, wineName);
                        String descr = rs_wines.getString("description");
                        if (!rs_wines.wasNull()) {
                            wine.addDescription(descr);
                        }
                        new WineFood(wine, subFood);
                    }
                }
                this.foods.add(subFood);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Food> getFoods() {
        return this.foods;
    }
}