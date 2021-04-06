package winepairer.domain;

import java.sql.*;

import com.mysql.cj.xdevapi.Result;

public class DataBase {
    
    public static void main(String[] args) {
        WineDineReader readBook = new WineDineReader();
        try {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/wine_dine_pairing", 
                "root", 
                "winepairer1");
            String wineInsert = "INSERT INTO wines "+
                "(colour, name, description) VALUES (?, ?, ?)";
            String foodInsert = "INSERT INTO foods "+
                "(name, gamechanger) VALUES (?, ?)";
            String pairingInsert = "INSERT INTO pairings "+
                "(wine_id, food_id, wine_specs, food_specs) VALUES (?, ?, ?, ?)";
            String getWineId = "SELECT wine_id FROM wines WHERE wines.name = ?";
            PreparedStatement wineInsertStmt = conn.prepareStatement(wineInsert);
            PreparedStatement foodInsertStmt = conn.prepareStatement(foodInsert);
            PreparedStatement pairInsertStmt = conn.prepareStatement(pairingInsert);
            PreparedStatement getWineIdStmt = conn.prepareStatement(getWineId);
            Statement stmt = conn.createStatement();
            for (Wine wine:readBook.getWines().values()) {
                wineInsertStmt.setString(1, wine.getColour().getDutchName());
                wineInsertStmt.setString(2, wine.getName());
                if (wine.hasDescription()) {
                    wineInsertStmt.setString(3, wine.getDescription());
                } else {
                    wineInsertStmt.setNull(3, Types.VARCHAR);
                }
                wineInsertStmt.execute();
            }
            for (Food food:readBook.getFoods().values()) {
                foodInsertStmt.setString(1, food.getName());
                foodInsertStmt.setBoolean(2, food.isGamechanger());
                foodInsertStmt.execute();
                ResultSet rs_food = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                rs_food.next();
                int foodId = rs_food.getInt(1);
                for (WineFood pair:food.getPairings()) {
                    getWineIdStmt.setString(1, pair.getWine().getName());
                    ResultSet rs_wine = getWineIdStmt.executeQuery();
                    rs_wine.next();
                    int wineId = rs_wine.getInt("wine_id");
                    pairInsertStmt.setInt(1, wineId);
                    pairInsertStmt.setInt(2, foodId);
                    pairInsertStmt.setString(3, pair.getWineName());
                    pairInsertStmt.setString(4, pair.getFoodName());
                    pairInsertStmt.execute();
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
