package com.fx.fx.Utils;
import javafx.scene.control.Alert;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBaseUtils {
    private static final Map<String, Connection> connectDruid = new HashMap<>();

    public static void setConnectDruid (String connectInfo) {
        String[] connectArr = connectInfo.split(",");
        Connection con;
        String driver="com.mysql.cj.jdbc.Driver";
        //这里我的数据库是cj
        String url="jdbc:mysql://"+ connectArr[0] +":"+connectArr[1]+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";

        if (connectDruid.get(connectInfo) == null) {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(url, connectArr[2], connectArr[3]);
                if (!((Connection) con).isClosed()) {
                    // 放入单例池
                    connectDruid.put(connectInfo, con);
                }
            } catch (ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("数据库驱动没有安装");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(e.getMessage());
                alert.show();
                System.out.printf(e.getMessage());
            }
        }
    }

    public static Connection getConnectDruid (String connectInfo) {
        if (connectDruid.get(connectInfo) == null) setConnectDruid(connectInfo);
        return connectDruid.get(connectInfo);
    }

    public static List<String> getDatabases(String connectInfo) {
        setConnectDruid(connectInfo);
        Connection con = connectDruid.get(connectInfo);
        List<String> ret = new ArrayList<>();

        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("Show Databases");

            while (rs.next()) {
                ret.add(rs.getString("Database"));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
            System.out.printf(e.getMessage());
        }

        return ret;
    }
}
