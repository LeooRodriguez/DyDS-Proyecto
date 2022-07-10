package Model;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    public static boolean loadDatabase() throws Exception {
        boolean isLoad = false;
        String url = "jdbc:sqlite:./dictionary.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);
                statement.executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
                isLoad = true;
            }
        } catch (SQLException e) {
            throw new SQLException("ERROR");
        }
        return isLoad;
    }

    public static ArrayList<String> getLocallySaveResults() throws SQLException {
        ArrayList<String> locallyResults = new ArrayList<>();
        Connection dataBaseConnection = null;
        dataBaseConnection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = dataBaseConnection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("select * from catalog");
        while (rs.next()) locallyResults.add(rs.getString("title"));
        if (dataBaseConnection != null)
            dataBaseConnection.close();
        return locallyResults;
    }


    public static boolean saveInfoLocally(String title, String extract) throws Exception {
        boolean savedSuccessfully = false;
        Connection dataBaseConnection = null;
        try {
            dataBaseConnection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = dataBaseConnection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("replace into catalog values(null, '" + title + "', '" + extract + "', 1)");
            savedSuccessfully = true;
        } catch (SQLException e) {
            throw new SQLException("ERROR");
        } finally {
            try {
                if (dataBaseConnection != null)
                    dataBaseConnection.close();
            } catch (SQLException e) {
                throw new SQLException("ERROR");
            }
        }
        return savedSuccessfully;
    }

    public static String getInfoSelectedSavedLocally(String title) throws SQLException {
        String extract = null;
        Connection dataBaseConnection = null;
        dataBaseConnection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
        Statement statement = dataBaseConnection.createStatement();
        statement.setQueryTimeout(30);
        ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'");
        extract = rs.getString("extract");
        rs.next();
        if (dataBaseConnection != null)
            dataBaseConnection.close();
        return extract;
    }

    public static boolean deleteSelectedInfoSavedLocally(String title) throws Exception {
        boolean deleteSuccessfully = false;
        Connection dataBaseConnection = null;
        try {
            dataBaseConnection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = dataBaseConnection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'");
            deleteSuccessfully = true;
        } catch (SQLException e) {
            throw new SQLException("ERROR");
        } finally {
            try {
                if (dataBaseConnection != null)
                    dataBaseConnection.close();
            } catch (SQLException e) {
                throw new SQLException("ERROR");
            }
        }
        return deleteSuccessfully;
    }

}
