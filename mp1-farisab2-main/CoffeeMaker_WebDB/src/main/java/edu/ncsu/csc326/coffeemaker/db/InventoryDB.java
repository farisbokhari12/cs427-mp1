package edu.ncsu.csc326.coffeemaker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ncsu.csc326.coffeemaker.Inventory;
import edu.ncsu.csc326.coffeemaker.Recipe;
import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
public class InventoryDB {
public static boolean addInventory(int coffee, int milk, int sugar, int chocolate){
    DBConnection db = new DBConnection();
    Connection conn = null;
    PreparedStatement stmt = null;
    boolean inventoryAdded = false;
    try {
        conn = db.getConnection();
        stmt = conn.prepareStatement("SELECT COUNT(*) AS cnt FROM tutorials");
        ResultSet empty = stmt.executeQuery();
        if (empty.getInt("cnt") == 0) {
            stmt = conn.prepareStatement("INSERT INTO inventory VALUES(?,?,?,?)");
            stmt.setString(1, Integer.toString(coffee));
            stmt.setString(2, Integer.toString(milk));
            stmt.setString(3, Integer.toString(sugar));
            stmt.setString(4, Integer.toString(chocolate));
            stmt.executeUpdate();
            inventoryAdded = true;
        } else {
            stmt = conn.prepareStatement("UPDATE inventory SET coffee = "+ coffee +", milk = "+ milk + ", sugar ="+sugar+", chocolate = " + chocolate +"LIMIT 1");
            stmt.executeUpdate();
            inventoryAdded = true;
        }
        return inventoryAdded;

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        DBConnection.closeConnection(conn, stmt);
    }
    return inventoryAdded;
}
public boolean useInventory(Recipe r) {
    DBConnection db = new DBConnection();
    Connection conn = null;
    PreparedStatement stmt = null;
    boolean inventoryUsable = false;
        try {
            stmt = conn.prepareStatement("SELECT sum(coffee) as c, sum(milk) as m, sum(sugar) as s, sum(chocolate) as ch FROM inventory");            
            Inventory i = new Inventory();
            ResultSet rs = stmt.executeQuery();
            i.setCoffee(Integer.parseInt(rs.getString("c")));
            i.setMilk(Integer.parseInt(rs.getString("m")));
            i.setSugar(Integer.parseInt(rs.getString("s")));
            i.setChocolate(Integer.parseInt(rs.getString("ch")));
            if (i.useIngredients(r)) {
                stmt = conn.prepareStatement("TRUNCATE inventory");
                stmt.executeQuery();
                stmt = conn.prepareStatement("INSERT INTO inventory VALUES(?,?,?,?)");
                stmt.setString(1, Integer.toString(i.getCoffee()));
                stmt.setString(2, Integer.toString(i.getMilk()));
                stmt.setString(3, Integer.toString(i.getSugar()));
                stmt.setString(4, Integer.toString(i.getChocolate()));
                stmt.executeUpdate();
                inventoryUsable = true;
            } else {
                return inventoryUsable;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(conn, stmt);
        }
        return inventoryUsable;
    }
public Inventory checkInventory() {
    DBConnection db = new DBConnection();
    Connection conn = null;
    PreparedStatement stmt = null;
    Inventory i = new Inventory();
    try {
        stmt = conn.prepareStatement("SELECT sum(coffee) as c, sum(milk) as m, sum(sugar) as s, sum(chocolate) as ch FROM inventory");
        ResultSet rs = stmt.executeQuery();
        i.setCoffee(Integer.parseInt(rs.getString("c")));
        i.setMilk(Integer.parseInt(rs.getString("m")));
        i.setSugar(Integer.parseInt(rs.getString("s")));
        i.setChocolate(Integer.parseInt(rs.getString("ch")));
        return i;
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        DBConnection.closeConnection(conn,stmt);
    }
    return i;
}
}