package com.fx.fx;


import com.fx.fx.Utils.DataBaseUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class PaneController implements Initializable {

    @FXML
    private TreeView<String> modelTreeView;

    @FXML
    private TextField startConditionText;

    @FXML
    private TextField StartTimeText;

    @FXML
    private TextField endConditionText;

    @FXML
    private TextField endTimeText;

    @FXML
    private TextField filePathText;

    @FXML
    private TextField fileTimeText;

    @FXML
    private TextField samplingRatioText;

    @FXML
    private CheckBox startTimeCheckBox;

    @FXML
    private CheckBox endTimeCheckBox;

    @FXML
    private ComboBox fileFormatComboBox;

    @FXML
    private TableView<DataRecord> dataTable = new TableView<DataRecord>();

    @FXML
    private TableColumn variableId = new TableColumn();

    @FXML
    private TableColumn variableName = new TableColumn();

    @FXML
    private TableColumn variableAlias = new TableColumn();

    @FXML
    private Button insertButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button moveUpButton;

    @FXML
    private Button moveDownButton;

    @FXML
    private Button moveTopButton;

    @FXML
    private Button moveBottomButton;

    @FXML
    private Button addConnect;

    @FXML
    private MenuButton menuButtonDatabase;

    private ObservableList<DataRecord> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDataRecordTable();
        listenDataRecordTable();

    }


    public void listenDataRecordTable(){
        insertButton.setOnAction(actionEvent -> {
            System.out.print("click");
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                dataTable.getItems().add(selectIndex,new DataRecord("","",""));
                dataTable.getSelectionModel().select(selectIndex);
            });
        });
        deleteButton.setOnAction(actionEvent -> {
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                dataTable.getItems().remove(dataTable.getItems().get(selectIndex));
            });
        });
        moveUpButton.setOnAction(actionEvent -> {
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                int foreIndex = selectIndex.intValue() == 0 ? 0 : selectIndex.intValue() - 1;
                Collections.swap(dataTable.getItems(),foreIndex,selectIndex.intValue());
                dataTable.getSelectionModel().select(foreIndex);
            });
        });
        moveDownButton.setOnAction(actionEvent -> {
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                int afterIndex = selectIndex.intValue() == dataTable.getItems().size() - 1 ? dataTable.getItems().size() - 1 : selectIndex.intValue() + 1;
                Collections.swap(dataTable.getItems(),afterIndex,selectIndex.intValue());
                dataTable.getSelectionModel().select(afterIndex);
            });
        });
        moveTopButton.setOnAction(actionEvent -> {
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                int foreIndex = 0;
                Collections.swap(dataTable.getItems(),foreIndex,selectIndex.intValue());
                dataTable.getSelectionModel().select(foreIndex);
            });
        });
        moveBottomButton.setOnAction(actionEvent -> {
            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
                int afterIndex = dataTable.getItems().size()-1;
                Collections.swap(dataTable.getItems(),afterIndex,selectIndex.intValue());
                dataTable.getSelectionModel().select(afterIndex);
            });
        });
        // 添加连接
        addConnect.setOnAction(actionEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-connect.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 600, 400);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = new Stage();
//            stage.setAlwaysOnTop(true);
            stage.setTitle("添加链接");
            stage.setScene(scene);
            stage.getScene().getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            stage.show();
        });
//        JavafxReactor.actionEventsOf(insertButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                dataTable.getItems().add(selectIndex,new DataRecord("","",""));
//                dataTable.getSelectionModel().select(selectIndex);
//            });
//        });
//
//        JavafxReactor.actionEventsOf(deleteButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                dataTable.getItems().remove(dataTable.getItems().get(selectIndex));
//            });
//        });
//
//        JavafxReactor.actionEventsOf(moveUpButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                int foreIndex = selectIndex.intValue() == 0 ? 0 : selectIndex.intValue() - 1;
//                Collections.swap(dataTable.getItems(),foreIndex,selectIndex.intValue());
//                dataTable.getSelectionModel().select(foreIndex);
//            });
//        });
//
//        JavafxReactor.actionEventsOf(moveDownButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                int afterIndex = selectIndex.intValue() == dataTable.getItems().size() - 1 ? dataTable.getItems().size() - 1 : selectIndex.intValue() + 1;
//                Collections.swap(dataTable.getItems(),afterIndex,selectIndex.intValue());
//                dataTable.getSelectionModel().select(afterIndex);
//            });
//        });
//
//        JavafxReactor.actionEventsOf(moveTopButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                int foreIndex = 0;
//                Collections.swap(dataTable.getItems(),foreIndex,selectIndex.intValue());
//                dataTable.getSelectionModel().select(foreIndex);
//            });
//        });
//
//        JavafxReactor.actionEventsOf(moveBottomButton).subscribe(event -> {
//            dataTable.getSelectionModel().getSelectedIndices().forEach( selectIndex ->{
//                int afterIndex = dataTable.getItems().size()-1;
//                Collections.swap(dataTable.getItems(),afterIndex,selectIndex.intValue());
//                dataTable.getSelectionModel().select(afterIndex);
//            });
//        });
    }

    public void initializeDataRecordTable(){
        variableId.setCellValueFactory(new PropertyValueFactory<DataRecord, Integer>("id"));
        variableName.setCellValueFactory(new PropertyValueFactory<DataRecord, String>("name"));
        variableAlias.setCellValueFactory(new PropertyValueFactory<DataRecord, String>("alias"));
        dataTable.getItems().setAll(getDataRecordInfo());
    }

    /**
     * 绑定选择数据库点击事件
     */
    public void addMenuItemAddAction () {
        ObservableList<MenuItem> menuButtonList = menuButtonDatabase.getItems();
        for (MenuItem btn : menuButtonList) {
            System.out.println(btn.getId());
            btn.setOnAction(event -> {
                // 查询数据库展示
                setDatabaseTreeView(btn.getId());
            });
        }
    }

    /**
     * 设置数据库列表
     */
    public void setDatabaseTreeView (String id) {
        TreeItem<String> root = new TreeItem<>(id);
        modelTreeView.setRoot(root);

        // 监听当前的选择
        modelTreeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println(modelTreeView.getSelectionModel().getSelectedItem());
                    if (newValue != null) databaseTreeViewOnSelectItem(newValue.getValue(), root, modelTreeView.getSelectionModel().getSelectedItem());
                }
        );


        // 链接数据库，查询库里面的表
        List<String> databases = DataBaseUtils.getDatabases(id);
        for (String databaseName : databases) {
            TreeItem<String> mammalTree = new TreeItem<>(databaseName);
            root.getChildren().add(mammalTree);
        }

//        birdTree.getChildren().add(new TreeItem<>("麻雀"));
//        birdTree.getChildren().add(new TreeItem<>("乌鸦"));
//        birdTree.getChildren().add(new TreeItem<>("燕子"));
//
//
//        TreeItem<String> mammalTree = new TreeItem<>("哺乳类");
//        mammalTree.getChildren().add(new TreeItem<String>("猫"));
//        mammalTree.getChildren().add(new TreeItem<String>("狗"));
//        mammalTree.getChildren().add(new TreeItem<String>("兔子"));
//        root.getChildren().add(mammalTree);
    }

    private void databaseTreeViewOnSelectItem(String item, TreeItem<String> root, TreeItem<String> currentMenuItem)
    {
        Connection connection1 = DataBaseUtils.getConnectDruid(root.getValue());
        try {
            Statement statement = connection1.createStatement();
            statement.executeUpdate("USE " + item);
            ResultSet resultSet = statement.executeQuery("show tables");
            while (resultSet.next()) {
                TreeItem<String> treeItem = new TreeItem<>(resultSet.getString("Tables_in_" + item));
                currentMenuItem.getChildren().add(treeItem);
                System.out.println(resultSet.getString("Tables_in_" + item));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMenuButtonDatabase (List<String> data) {
        System.out.println(menuButtonDatabase);
        for (String name:data) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(name);
            menuItem.setId(name);
            menuButtonDatabase.getItems().add(menuItem);
            addMenuItemAddAction();
        }
    }

    public List<DataRecord> getDataRecordInfo(){
        data =  FXCollections.observableArrayList(
                new DataRecord("1", "Smith", "张三"),
                new DataRecord("2", "Johnson", "李四"),
                new DataRecord("3", "Williams", "王五"),
                new DataRecord("4", "Jones", "卢二"),
                new DataRecord("5", "Brown", "赵一")
        );
        return data;
    }

    public static class DataRecord {
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty alias;

        private DataRecord(String fName, String lName, String email) {
            this.id = new SimpleStringProperty (fName);
            this.name = new SimpleStringProperty(lName);
            this.alias = new SimpleStringProperty(email);
        }

        public String getId() {
            return id.get();
        }

        public SimpleStringProperty idProperty() {
            return id;
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getAlias() {
            return alias.get();
        }

        public SimpleStringProperty aliasProperty() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias.set(alias);
        }

    }


}