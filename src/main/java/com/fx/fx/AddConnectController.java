package com.fx.fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.*;
import java.sql.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AddConnectController implements Initializable {
    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField port;

    @FXML
    private TextField host;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        initializeDataRecordTable();
        listenDataRecordTable();

    }

    public void listenDataRecordTable() {
        submit.setOnAction(actionEvent -> {
            String portS = port.getText();
            String usernameS = username.getText();
            String passwordS = password.getText();
            String hostS = host.getText();

            hostS = "192.168.159.128";
            portS = "3306";
            usernameS = "root";
            passwordS = "123456";
            connectDatabase(hostS, portS, usernameS, passwordS);


            System.out.print("click");
            System.out.printf(port.getText());
        });
    }

    private void connectDatabase (String host, String port, String username, String password) {
        Connection con;
        String driver="com.mysql.cj.jdbc.Driver";
        //这里我的数据库是cgjr
        String url="jdbc:mysql://"+ host +":"+port+"?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            if (!((Connection) con).isClosed()) {
                System.out.println("数据库连接成功");
                File file = new File("connect.txt");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    outputStreamWriter.append(host).append(",").append(port).append(",").append(username).append(",").append(password);
                    outputStreamWriter.close();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            List<String> stringList = new ArrayList<>();
            stringList.add(host+","+port+","+username+","+password);

            PaneController paneController = (PaneController) (ControllerManage.controllers.get("pane"));
            paneController.setMenuButtonDatabase(stringList);

            con.close();
            System.out.println("数据库已关闭连接");
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
            System.out.printf(e.getMessage());
            System.out.println("数据库连接失败");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
