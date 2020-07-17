package ui;

import control.Merchant;
import control.Rider;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Discount_coupon;
import model.Rider_information;
import model.order_;
import model.view_order;
import util.BaseException;

import javax.swing.*;
import java.util.Date;

public class FrmRider {

    public void Rider_Init(Stage primaryStage) throws BaseException{
        new Rider().check();
        //创建sence组件
        Scene sence_init;
        Button Rider_getOrder = new Button("抢单");
        Button Rider_deliveryOrder = new Button("配送订单");
        Button Rider_commentOrder = new Button("历史订单评价");
        Button Rider_salary = new Button("查询本月工资");
        Button Rider_changeinf = new Button("修改骑手信息");
        Button exit = new Button("退出");

        // 创建布局控件 添加到布局
        SplitPane init = new SplitPane();
        VBox left = new VBox();
//        TableView<FrmMerchant.Mer_information> right = new TableView<>();
        init.getItems().addAll(left);
        left.setMaxWidth(200);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(Rider_getOrder);
        left.getChildren().add(Rider_deliveryOrder);
        left.getChildren().add(Rider_commentOrder);
        left.getChildren().add(Rider_salary);
        left.getChildren().add(Rider_changeinf);
        left.getChildren().add(exit);
        left.getStylesheets().add("/NodeStyle.css");
        Rider_getOrder.getStyleClass().addAll("mainButton");
        Rider_deliveryOrder.getStyleClass().addAll("mainButton");
        Rider_commentOrder.getStyleClass().addAll("mainButton");
        Rider_salary.getStyleClass().addAll("mainButton");
        Rider_changeinf.getStyleClass().addAll("mainButton");
        exit.getStyleClass().addAll("mainButton");

        //创建场景
        sence_init = new Scene(init, 800, 400);

        // 给按钮添加事件处理的对象
        Rider_changeinf.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_changeinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Rider_getOrder.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, getOrder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Rider_deliveryOrder.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, deliveryOrder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Rider_salary.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, salary());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        exit.setOnMouseClicked(e -> {
            primaryStage.setScene(FrmMainLogin.scene_F);
            primaryStage.setTitle("外卖助手");
        });

        Rider_commentOrder.setOnMouseClicked(e ->{
            init.getItems().clear();
            try {
                init.getItems().addAll(left, comment());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

                // 将场景添加到窗口
        primaryStage.setScene(sence_init);
        primaryStage.setTitle("外卖助手-骑手:"+FrmMainLogin.Currentusername);
    }

    private TableView<view_order> comment() throws BaseException {
        ObservableList<view_order> data = new Rider().comment();
        TableView<view_order> tableView = new TableView<>();
        TableColumn<view_order, String> Order_id =
                new TableColumn<>("订单编号");
        Order_id.setMinWidth(100);
        Order_id.setCellValueFactory(
                new PropertyValueFactory<>("Order_id"));
        TableColumn<view_order, String> Merchant_name =
                new TableColumn<>("商家名");
        Merchant_name.setMinWidth(100);
        Merchant_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchant_name"));
        TableColumn<view_order, String> Consumer_name =
                new TableColumn<>("顾客名");
        Consumer_name.setMinWidth(100);
        Consumer_name.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_name"));
        TableColumn<view_order, String> Order_state =
                new TableColumn<>("订单状态");
        Order_state.setMinWidth(100);
        Order_state.setCellValueFactory(
                new PropertyValueFactory<>("Order_state"));
        TableColumn<view_order, String> Comment_Rider =
                new TableColumn<>("骑手评价");
        Comment_Rider.setMinWidth(100);
        Comment_Rider.setCellValueFactory(
                new PropertyValueFactory<>("Comment_Rider"));

        tableView.getColumns().addAll(Merchant_name, Consumer_name, Order_state, Comment_Rider);
        tableView.setItems(data);
        return tableView;
    }

    private TableView<Rider_information> view_changeinf() throws BaseException {
        TableView<Rider_information> tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<Rider_information, String> name =
                new TableColumn<>("Name");
        TableColumn<Rider_information, String> pwd =
                new TableColumn<>("Pwd");
        TableColumn<Rider_information, String> sort =
                new TableColumn<>("类别");

        Callback<TableColumn<Rider_information, String>,
                TableCell<Rider_information, String>> cellFactory_String
                = (TableColumn<Rider_information, String> p) -> new EditingRiderinf_String();

        name.setMinWidth(100);
        name.setCellValueFactory(
                new PropertyValueFactory<>("Rider_name"));
        name.setCellFactory(cellFactory_String);
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Rider_information, String> t) -> {
                    ((Rider_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setRider_name(t.getNewValue());
                    try {
                        new Rider().updateriderinf(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        pwd.setMinWidth(100);
        pwd.setCellValueFactory(
                new PropertyValueFactory<>("Rider_pwd"));
        pwd.setCellFactory(cellFactory_String);
        pwd.setOnEditCommit(
                (TableColumn.CellEditEvent<Rider_information, String> t) -> {
                    ((Rider_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setRider_name(t.getNewValue());
                    try {
                        new Rider().updateriderinf(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        sort.setMinWidth(100);
        sort.setCellValueFactory(
                new PropertyValueFactory<>("Rider_sort"));

        tableView.getColumns().addAll(name, pwd, sort);
        tableView.setItems(new Rider().loadriderinf());
        return tableView;
    }

    private VBox getOrder() throws BaseException {
        TableView<view_order> tableView = new TableView<>();
        ObservableList<view_order> data = new Rider().loadgetorder();
        tableView.setEditable(true);
        Button button_getOrder = new Button("抢单");

        TableColumn<view_order, String> Address_province =
                new TableColumn<>("省");
        TableColumn<view_order, String> Address_city =
                new TableColumn<>("市");
        TableColumn<view_order, String> Address_region =
                new TableColumn<>("区");
        TableColumn<view_order, String> Address_add =
                new TableColumn<>("地址");
        TableColumn<view_order, String> Address_linkman =
                new TableColumn<>("收货人");
        TableColumn<view_order, String> Address_phonenum =
                new TableColumn<>("电话");
        TableColumn<view_order, String> Merchant_name =
                new TableColumn<>("商家名");

        Address_province.setCellValueFactory(new PropertyValueFactory<>("Address_province"));
        Address_city.setCellValueFactory(new PropertyValueFactory<>("Address_city"));
        Address_region.setCellValueFactory(new PropertyValueFactory<>("Address_region"));
        Address_add.setCellValueFactory(new PropertyValueFactory<>("Address_add"));
        Address_linkman.setCellValueFactory(new PropertyValueFactory<>("Address_linkman"));
        Address_phonenum.setCellValueFactory(new PropertyValueFactory<>("Address_phonenum"));
        Merchant_name.setCellValueFactory(new PropertyValueFactory<>("Merchant_name"));

        button_getOrder.setOnAction((ActionEvent e) -> {
            try {
                new Rider().getorder(tableView.getSelectionModel().getSelectedItem());
                data.clear();
                data.addAll(new Rider().loadgetorder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        tableView.getColumns().addAll(Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Merchant_name);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(button_getOrder);
        vbox.getChildren().addAll(tableView, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    private VBox salary() throws BaseException {
        Label label = new Label("本月工资:" + new Rider().salary()+"￥");
        label.setFont(new Font(25));
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

    private VBox deliveryOrder() throws BaseException {
        TableView<view_order> tableView = new TableView<>();
        ObservableList<view_order> data = new Rider().loadhavingorder();
        tableView.setEditable(true);
        Button button_getOrder = new Button("送达");

        TableColumn<view_order, String> Address_city =
                new TableColumn<>("市");
        TableColumn<view_order, String> Address_region =
                new TableColumn<>("区");
        TableColumn<view_order, String> Address_add =
                new TableColumn<>("地址");
        TableColumn<view_order, String> Address_linkman =
                new TableColumn<>("收货人");
        TableColumn<view_order, String> Address_phonenum =
                new TableColumn<>("电话");

        Address_city.setCellValueFactory(new PropertyValueFactory<>("Address_city"));
        Address_region.setCellValueFactory(new PropertyValueFactory<>("Address_region"));
        Address_add.setCellValueFactory(new PropertyValueFactory<>("Address_add"));
        Address_linkman.setCellValueFactory(new PropertyValueFactory<>("Address_linkman"));
        Address_phonenum.setCellValueFactory(new PropertyValueFactory<>("Address_phonenum"));

        button_getOrder.setOnAction((ActionEvent e) -> {
            try {
                new Rider().deliveryOrder(tableView.getSelectionModel().getSelectedItem());
                data.clear();
                data.addAll(new Rider().loadhavingorder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        tableView.getColumns().addAll(Address_city, Address_region, Address_add, Address_linkman, Address_phonenum);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(button_getOrder);
        vbox.getChildren().addAll(tableView, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    static class EditingRiderinf_String extends TableCell<Rider_information, String> {

        private TextField textField;

        public EditingRiderinf_String() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> arg0,
                     Boolean arg1, Boolean arg2) -> {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
