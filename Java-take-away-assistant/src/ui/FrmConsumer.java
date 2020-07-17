package ui;

import control.Admin;
import control.Consumer;
import control.Merchant;
import control.Rider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Path;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;
import util.BaseException;
import util.BusinessException;
import util.DBUtil;
import ui.FrmMerchant.Merchandise_information;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class FrmConsumer {
    public static Boolean member;
    private Float reduce;
    private Float coupon;
    private Float Discount;
    private int Address_id;
    private String Address;
    SplitPane init;
    private int Reduction_id;
    private int Coupon_id;
    private int Order_count;
    private int Merchant_id;
    ObservableList<view_takeorder> data_havingorder;
    TableView<view_takeorder>  tableView_r, tableView_m;

    public void Con_Init(Stage primaryStage){
        //创建sence组件
        Scene sence_init = null;
        Button Con_setOrder = new Button("下单");
        Button Con_getOrder = new Button("查看订单");
        Button Con_Commentdise = new Button("评价商品");
        Button Con_Commentrider = new Button("评价骑手");
        Button Con_havingCoupon = new Button("查看优惠券");
        Button Con_editAddress = new Button("修改配送地址");
        Button Con_editinf = new Button("修改顾客信息");
        Button exit = new Button("退出");
//        Con_setOrder.setMinWidth(90);

        // 创建布局控件 添加到布局
        init = new SplitPane();
        VBox left = new VBox();
        init.getItems().addAll(left);
        left.setMaxWidth(150);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(Con_setOrder);
        left.getChildren().add(Con_getOrder);
        left.getChildren().add(Con_Commentdise);
        left.getChildren().add(Con_Commentrider);
        left.getChildren().add(Con_havingCoupon);
        left.getChildren().add(Con_editAddress);
        left.getChildren().add(Con_editinf);
        left.getChildren().add(exit);
        left.getStylesheets().add("/NodeStyle.css");
        Con_setOrder.getStyleClass().addAll("mainButton");
        Con_getOrder.getStyleClass().addAll("mainButton");
        Con_Commentdise.getStyleClass().addAll("mainButton");
        Con_Commentrider.getStyleClass().addAll("mainButton");
        Con_havingCoupon.getStyleClass().addAll("mainButton");
        Con_editAddress.getStyleClass().addAll("mainButton");
        Con_editinf.getStyleClass().addAll("mainButton");
        exit.getStyleClass().addAll("mainButton");

        //创建场景
        sence_init = new Scene(init, 1000, 500);

        // 给按钮添加事件处理的对象
        Con_setOrder.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                TakeOrder(left);
//                init.getItems().addAll(left, editinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
        });

        Con_getOrder.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, getOrder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
        });

        Con_editAddress.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, editAddress());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Con_havingCoupon.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, havingCoupon());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Con_Commentdise.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, Commentdise(init, primaryStage));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        exit.setOnMouseClicked(e -> {
            primaryStage.setScene(FrmMainLogin.scene_F);
            primaryStage.setTitle("外卖助手");
        });

        Con_Commentrider.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, Commentrider());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Con_editinf.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, editinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        // 将场景添加到窗口
//        Parent root = fxmlLoader.load();
//        sence_init.getStylesheets().add(getClass().getResource("NodeStyle.css").toExternalForm());
//        Con_setOrder.getStyleClass().add(String.valueOf(Con_setOrder));
//        .getStylesheets().add("NodeStyle.css");
        primaryStage.setScene(sence_init);
        primaryStage.setTitle("外卖助手-顾客:"+FrmMainLogin.Currentusername);
    }

    private VBox getOrder() throws BaseException {
        TableView<view_order> tableView = new TableView<>();
        ObservableList<view_order> data = new Consumer().loadAllOrder();
        tableView.setMinHeight(450);

        TableColumn<view_order, String> Order_id =
                new TableColumn<>("订单编号");
        Order_id.setCellValueFactory(new PropertyValueFactory<>("Order_id"));
        TableColumn<view_order, String> Consumer_name =
                new TableColumn<>("顾客名");
        Consumer_name.setCellValueFactory(new PropertyValueFactory<>("Consumer_name"));
        TableColumn<view_order, String> Merchant_name =
                new TableColumn<>("商家名");
        Merchant_name.setCellValueFactory(new PropertyValueFactory<>("Merchant_name"));
        TableColumn<view_order, Integer> Rider_name =
                new TableColumn<>("骑手编号");
        Rider_name.setCellValueFactory(new PropertyValueFactory<>("Rider_name"));
        TableColumn<view_order, Date> Order_platime =
                new TableColumn<>("订单开始时间");
        Order_platime.setCellValueFactory(new PropertyValueFactory<>("Order_platime"));
        TableColumn<view_order, Date> Order_deltime =
                new TableColumn<>("订单截至时间");
        Order_deltime.setCellValueFactory(new PropertyValueFactory<>("Order_deltime"));
        TableColumn<view_order, String> Address =
                new TableColumn<>("地址");
        Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
        TableColumn<view_order, String> Address_linkman =
                new TableColumn<>("联系人姓名");
        Address_linkman.setCellValueFactory(new PropertyValueFactory<>("Address_linkman"));
        TableColumn<view_order, String> Address_phonenum =
                new TableColumn<>("联系人电话");
        Address_phonenum.setCellValueFactory(new PropertyValueFactory<>("Address_phonenum"));

        final TextField addQuery = new TextField();
        addQuery.setPromptText("订单编号");
        addQuery.setMaxWidth(Address_phonenum.getPrefWidth()*1.2);
        final Button queryButton = new Button("查询");
        queryButton.setOnAction((ActionEvent e) -> {
            try {
                if (addQuery.getText().isEmpty()) throw new BusinessException("请输入编号！");
                data.clear();
                data.add(new Consumer().queryOrder(Integer.valueOf(addQuery.getText())));
                tableView.refresh();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addQuery.clear();
        });

        final Button delectButton = new Button("取消订单");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                if (tableView.getSelectionModel().getSelectedItem()==null) throw new BusinessException("请选择订单！");
                new Consumer().delectOrder(tableView.getSelectionModel().getSelectedItem());
                data.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.refresh();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addQuery.clear();
        });

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        tableView.getColumns().addAll(Order_id, Consumer_name, Merchant_name, Rider_name, Order_platime, Order_deltime, Address, Address_linkman, Address_phonenum);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addQuery, queryButton, delectButton);
        vbox.getChildren().addAll(splitPane, tableView );
        vbox.setSpacing(3);
        return vbox;
    }

    private VBox editAddress() throws BaseException {
        TableView<delivery_address> tableView = new TableView<>();
        ObservableList<delivery_address> data = new Consumer().loadAddress();
        tableView.setEditable(true);
        tableView.setMinHeight(450);

        TableColumn<delivery_address, String> Address_province =
                new TableColumn<>("省");
        TableColumn<delivery_address, String> Address_city =
                new TableColumn<>("市");
        TableColumn<delivery_address, String> Address_region =
                new TableColumn<>("区/县");
        TableColumn<delivery_address, String> Address_add =
                new TableColumn<>("地址");
        TableColumn<delivery_address, String> Address_linkman =
                new TableColumn<>("收货人");
        TableColumn<delivery_address, String> Address_phonenum =
                new TableColumn<>("电话");
        TableColumn<delivery_address, Boolean> Default_ =
                new TableColumn<>("是否为默认地址");
        Callback<TableColumn<delivery_address, String>,
                TableCell<delivery_address, String>> cellFactory_String
                = (TableColumn<delivery_address, String> p) -> new EditingAddressCell_String();


        Address_province.setMinWidth(100);
        Address_province.setCellValueFactory(
                new PropertyValueFactory<>("Address_province"));
        Address_province.setCellFactory(cellFactory_String);
        Address_province.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_province(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Address_city.setMinWidth(100);
        Address_city.setCellValueFactory(
                new PropertyValueFactory<>("Address_city"));
        Address_city.setCellFactory(cellFactory_String);
        Address_city.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_city(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Address_region.setMinWidth(100);
        Address_region.setCellValueFactory(
                new PropertyValueFactory<>("Address_region"));
        Address_region.setCellFactory(cellFactory_String);
        Address_region.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_region(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Address_add.setMinWidth(100);
        Address_add.setCellValueFactory(
                new PropertyValueFactory<>("Address_add"));
        Address_add.setCellFactory(cellFactory_String);
        Address_add.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_add(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 6);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Address_linkman.setMinWidth(100);
        Address_linkman.setCellValueFactory(
                new PropertyValueFactory<>("Address_linkman"));
        Address_linkman.setCellFactory(cellFactory_String);
        Address_linkman.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_linkman(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 7);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Address_phonenum.setMinWidth(100);
        Address_phonenum.setCellValueFactory(
                new PropertyValueFactory<>("Address_phonenum"));
        Address_phonenum.setCellFactory(cellFactory_String);
        Address_phonenum.setOnEditCommit(
                (TableColumn.CellEditEvent<delivery_address, String> t) -> {
                    ((delivery_address) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAddress_phonenum(t.getNewValue());
                    try {
                        new Consumer().updateAddress(t.getRowValue(), 8);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Default_.setMinWidth(100);
        Default_.setCellValueFactory(
                new PropertyValueFactory<>("Default_"));

        final TextField addprovince = new TextField();
        addprovince.setPromptText("省");
        addprovince.setMaxWidth(Address_province.getPrefWidth()*1.2);
        final TextField addcity = new TextField();
        addcity.setPromptText("市");
        addcity.setMaxWidth(Address_city.getPrefWidth()*1.2);
        final TextField addregion = new TextField();
        addregion.setPromptText("区/县");
        addregion.setMaxWidth(Address_region.getPrefWidth()*1.2);
        final TextField addadd = new TextField();
        addadd.setPromptText("具体地址");
        addadd.setMaxWidth(Address_add.getPrefWidth()*1.2);
        final TextField addlinkman = new TextField();
        addlinkman.setPromptText("联系人姓名");
        addlinkman.setMaxWidth(Address_linkman.getPrefWidth()*1.2);
        final TextField addphonenum = new TextField();
        addphonenum.setPromptText("联系人电话");
        addphonenum.setMaxWidth(Address_phonenum.getPrefWidth()*1.2);

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                if (addprovince.getText().isEmpty() || addcity.getText().isEmpty() || addregion.getText().isEmpty() || addadd.getText().isEmpty() || addlinkman.getText().isEmpty() || addphonenum.getText().isEmpty()){
                    throw new BusinessException("信息未输入完整!");
                } else {
                    data.add(new Consumer().insertAddress(addprovince.getText(), addcity.getText(), addregion.getText(),
                            addadd.getText(), addlinkman.getText(), addphonenum.getText()));
                }
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addprovince.clear();
            addcity.clear();
            addregion.clear();
            addadd.clear();
            addlinkman.clear();
            addphonenum.clear();
        });

        final Button delectButton = new Button("DELECT");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                new Consumer().delectAddress(tableView.getSelectionModel().getSelectedItem());
                data.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.refresh();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        final Button defaultButton = new Button("设为默认");
        defaultButton.setOnAction((ActionEvent e) -> {
            try {
                new Consumer().defaultAddress(tableView.getSelectionModel().getSelectedItem());
                tableView.getSelectionModel().getSelectedItem().setDefault_(true);
                tableView.refresh();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        tableView.getColumns().addAll(Address_province, Address_city, Address_region, Address_add, Address_linkman, Address_phonenum, Default_);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addprovince, addcity, addregion, addadd, addlinkman, addphonenum, addButton, delectButton, defaultButton);
        vbox.getChildren().addAll(tableView, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    public TableView<Consumer_information> editinf() throws BaseException {
        TableView<Consumer_information> tableView = new TableView<Consumer_information>();
        tableView.setEditable(true);

        TableColumn<Consumer_information, String> Consumer_name =
                new TableColumn<>("姓名");
        TableColumn<Consumer_information, String> Consumer_pwd =
                new TableColumn<>("密码");
        TableColumn<Consumer_information, String> Consumer_gender =
                new TableColumn<>("性别");
        TableColumn<Consumer_information, String> Consumer_phonenum =
                new TableColumn<>("电话");
        TableColumn<Consumer_information, String> Consumer_mail =
                new TableColumn<>("邮箱");
        TableColumn<Consumer_information, String> Consumer_city =
                new TableColumn<>("所在城市");
        TableColumn<Consumer_information, Boolean> Consumer_member =
                new TableColumn<>("会员");

        Callback<TableColumn<Consumer_information, String>,
                TableCell<Consumer_information, String>> cellFactory_String
                = (TableColumn<Consumer_information, String> p) -> new EditingConinf_String();

        Callback<TableColumn<Consumer_information, Boolean>,
                TableCell<Consumer_information, Boolean>> cellFactory_Boolean
                = (TableColumn<Consumer_information, Boolean> p) -> new EditingConinf_Boolean();

        Consumer_name.setMinWidth(80);
        Consumer_name.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_name"));
        Consumer_name.setCellFactory(cellFactory_String);
        Consumer_name.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_name(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 2);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_pwd.setMinWidth(80);
        Consumer_pwd.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_pwd"));
        Consumer_pwd.setCellFactory(cellFactory_String);
        Consumer_pwd.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_pwd(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_gender.setMinWidth(50);
        Consumer_gender.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_gender"));
        Consumer_gender.setCellFactory(cellFactory_String);
        Consumer_gender.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_gender(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_phonenum.setMinWidth(120);
        Consumer_phonenum.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_phonenum"));
        Consumer_phonenum.setCellFactory(cellFactory_String);
        Consumer_phonenum.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_phonenum(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_city.setMinWidth(80);
        Consumer_city.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_city"));
        Consumer_city.setCellFactory(cellFactory_String);
        Consumer_city.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_city(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 7);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_mail.setMinWidth(150);
        Consumer_mail.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_mail"));
        Consumer_mail.setCellFactory(cellFactory_String);
        Consumer_mail.setOnEditCommit(
                (TableColumn.CellEditEvent<Consumer_information, String> t) -> {
                    ((Consumer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setConsumer_mail(t.getNewValue());
                    try {
                        new Consumer().updateConinf(t.getRowValue(), 6);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Consumer_member.setMinWidth(50);
        Consumer_member.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_member"));


        tableView.getColumns().addAll(Consumer_name, Consumer_pwd, Consumer_gender, Consumer_phonenum, Consumer_mail, Consumer_city, Consumer_member);
        tableView.setItems(new Consumer().loadConinf());
        return tableView;
    }

    public VBox havingCoupon() throws BaseException {
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        TableView<Consumer_Coupon> tableView = new TableView<Consumer_Coupon>();
        TableColumn<Consumer_Coupon, String> Coupon_name =
                new TableColumn<>("商家名");
        TableColumn<Consumer_Coupon, Float> Coupon_amount =
                new TableColumn<>("优惠额度");
        TableColumn<Consumer_Coupon, Integer> Coupon_aim =
                new TableColumn<>("集单目标");
        TableColumn<Consumer_Coupon, Integer> Coupon_now =
                new TableColumn<>("目前集单数");
        TableColumn<Consumer_Coupon, Date> Coupon_Starttime =
                new TableColumn<>("开始时间");
        TableColumn<Consumer_Coupon, Date> Coupon_Finishtime =
                new TableColumn<>("结束时间");
//        TableColumn<Consumer_Coupon, String> Support_coupon =
//                new TableColumn<>("是否支持满减");

        Coupon_name.setMinWidth(50);
        Coupon_name.setCellValueFactory(
                new PropertyValueFactory<>("Coupon_name"));
        Coupon_amount.setMinWidth(50);
        Coupon_amount.setCellValueFactory(
                new PropertyValueFactory<>("Coupon_amount"));
        Coupon_aim.setMinWidth(50);
        Coupon_aim.setCellValueFactory(
                new PropertyValueFactory<>("Coupon_aim"));
        Coupon_now.setMinWidth(50);
        Coupon_now.setCellValueFactory(
                new PropertyValueFactory<>("Coupon_now"));
        Coupon_Starttime.setMinWidth(50);
        Coupon_Starttime.setCellValueFactory(
                new PropertyValueFactory<>("Starttime"));
        Coupon_Finishtime.setMinWidth(50);
        Coupon_Finishtime.setCellValueFactory(
                new PropertyValueFactory<>("Finishtime"));
//        Support_coupon.setMinWidth(50);
//        Support_coupon.setCellValueFactory(
//                new PropertyValueFactory<>("Support_coupon"));

        Label label = new Label("未过期的优惠券：");
        tableView.getColumns().addAll(Coupon_name, Coupon_amount, Coupon_aim, Coupon_now, Coupon_Starttime, Coupon_Finishtime);
        tableView.setItems(new Consumer().loadCoupon());
        vbox.getChildren().addAll(label, tableView);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox Commentrider() throws BaseException {
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        TableView<view_order> tableView = new TableView<view_order>();
        tableView.setEditable(true);

        TableColumn<view_order, String> Merchant_name =
                new TableColumn<>("商家名");
        TableColumn<view_order, String> Rider_name =
                new TableColumn<>("骑手姓名");
        TableColumn<view_order, String> Order_state =
                new TableColumn<>("商品状态");
        TableColumn<view_order, String> Comment_Rider =
                new TableColumn<>("评价内容");

        Callback<TableColumn<view_order, String>,
                TableCell<view_order, String>> cellRidercomment_String
                = (TableColumn<view_order, String> p) -> new EditingRidercomment_String();

        Merchant_name.setMinWidth(100);
        Merchant_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchant_name"));
        Rider_name.setMinWidth(100);
        Rider_name.setCellValueFactory(
                new PropertyValueFactory<>("Rider_name"));
        Order_state.setMinWidth(100);
        Order_state.setCellValueFactory(
                new PropertyValueFactory<>("Order_state"));
        Comment_Rider.setMinWidth(150);
        Comment_Rider.setCellValueFactory(
                new PropertyValueFactory<>("Comment_Rider"));
        Comment_Rider.setCellFactory(cellRidercomment_String);
        Comment_Rider.setOnEditCommit(
                (TableColumn.CellEditEvent<view_order, String> t) -> {
                    ((view_order) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setComment_Rider(t.getNewValue());
                    try {
                        if (!tableView.getSelectionModel().getSelectedItem().getComment_Rider().equals("未评价"))
                            new Consumer().updateRidercomment(tableView.getSelectionModel().getSelectedItem());
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Label label = new Label("待评价骑手：");
        tableView.getColumns().addAll(Merchant_name, Rider_name, Order_state, Comment_Rider);
        tableView.setItems(new Consumer().loadRidercomment());
        vbox.getChildren().addAll(label, tableView);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox Commentdise(SplitPane init, Stage primaryStage) throws BaseException {
        VBox vbox = new VBox();
        Button comment = new Button("评价");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        TableView<view_setComment> tableView = new TableView<view_setComment>();
        tableView.setMaxWidth(300);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        Label level = new Label("星级");
        GridPane.setConstraints(level, 0, 0);
        grid.getChildren().add(level);
        final TextField addlevel = new TextField();
        addlevel.setPromptText("星级（1~5）");
        addlevel.setMinWidth(300);
        GridPane.setConstraints(addlevel, 1, 0);
        grid.getChildren().add(addlevel);
        Label lcomment = new Label("评价");
        GridPane.setConstraints(lcomment, 0, 1);
        grid.getChildren().add(lcomment);
        final TextField addlcomment = new TextField();
        addlcomment.setPromptText("请填写10~60字的评价");
        addlcomment.setMinWidth(300);
        addlcomment.setMinHeight(100);
        GridPane.setConstraints(addlcomment, 1, 1);
        grid.getChildren().add(addlcomment);
        Label picture = new Label("添加图片");
        GridPane.setConstraints(picture, 0, 2);
        grid.getChildren().add(picture);
        final Button addlpicture = new Button("选择图片");
        final FileInputStream[] chosepicture = new FileInputStream[1];
        addlpicture.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    chosepicture[0] = chooser(primaryStage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        GridPane.setConstraints(addlpicture, 1, 2);
        grid.getChildren().add(addlpicture);
        Button setcomment = new Button("完成评价");
        GridPane.setConstraints(setcomment, 1, 3);
        grid.getChildren().add(setcomment);

        TableColumn<view_setComment, String> Merchant_name =
                new TableColumn<>("商家名");
        TableColumn<view_setComment, String> Merchandise_name =
                new TableColumn<>("菜品名");
        TableColumn<view_setComment, String> starttime =
                new TableColumn<>("送达时间");

        Merchant_name.setMinWidth(80);
        Merchant_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchant_name"));
        Merchandise_name.setMinWidth(80);
        Merchandise_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchandise_name"));
        starttime.setMinWidth(80);
        starttime.setCellValueFactory(
                new PropertyValueFactory<>("starttime"));

        comment.setOnAction((ActionEvent e) -> {
            if (tableView.getSelectionModel().getSelectedItem()!=null){
                init.getItems().remove(grid);
                init.getItems().add(grid);
            } else {
                try {
                    throw new BusinessException("请先选择评价订单！");
                } catch (BusinessException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            }
        });

        setcomment.setOnAction((ActionEvent e) -> {
            try {
                if (chosepicture[0] == null) {
                    throw new BusinessException("未选择图片！");
                }
                else {
                    new Consumer().insertcomment(tableView.getSelectionModel().getSelectedItem(), Integer.valueOf(addlevel.getText()), addlcomment.getText(), chosepicture[0]);
                    addlcomment.clear();
                    addlevel.clear();
                    chosepicture[0].close();
                }
            } catch (BaseException | IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Label label = new Label("待评价订单：");
        tableView.getColumns().addAll(Merchant_name, Merchandise_name, starttime);
        tableView.setItems(new Consumer().loadComment());
        vbox.getChildren().addAll(label, tableView, comment);
        vbox.setSpacing(3);
        return vbox;
    }

    public FileInputStream chooser(Stage prStage) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要的打开的文件");
        // 初始打开的位置
        fileChooser.setInitialDirectory(new File("."));

        File result =fileChooser.showOpenDialog(prStage);
        // 输入所选择文件的路径
        if (result.getAbsolutePath()==null) {
            return null;
        } else {
            return new FileInputStream(result);
        }
    }

    public void TakeOrder(VBox left) throws BaseException{
        if (getAddress_id()) {
            JOptionPane.showMessageDialog(null, "未设置默认地址", "错误",JOptionPane.ERROR_MESSAGE);
            init.getItems().addAll(left);
            return ;
        }
        this.Merchant_id = 0;

        VBox middle = new VBox();
        VBox right = new VBox();
        Label label_m1 = new Label("选择商家");
        Label label_m2 = new Label("选择菜品");
        SplitPane middle_bottom = new SplitPane();
        tableView_m= new TableView<view_takeorder>();
        tableView_r= new TableView<view_takeorder>();
        Label label_r = new Label("购物车");
        Button button_m1 = new Button("选择商家");
        Button button_back = new Button("返回");
        Button button_m2 = new Button("加入购物车");
        Button button_recommend = new Button("推荐菜品");
        Button button_r = new Button("下单");
        data_havingorder = FXCollections.observableArrayList(new ArrayList<view_takeorder>());

        TableColumn<view_takeorder, String> merchant_name = new TableColumn<>("商家名");
        merchant_name.setCellValueFactory(new PropertyValueFactory<>("merchant_name"));
        TableColumn<view_takeorder, String> Merchant_level = new TableColumn<>("星级");
        Merchant_level.setCellValueFactory(new PropertyValueFactory<>("Merchant_level"));
        TableColumn<view_takeorder, String> Consume_avgprice = new TableColumn<>("平均消费");
        Consume_avgprice.setCellValueFactory(new PropertyValueFactory<>("Consume_avgprice"));
        TableColumn<view_takeorder, String> Total_sales = new TableColumn<>("总销售额");
        Total_sales.setCellValueFactory(new PropertyValueFactory<>("Total_sales"));

        TableColumn<view_takeorder, String> merchandise_name = new TableColumn<>("菜品名");
        merchandise_name.setCellValueFactory(new PropertyValueFactory<>("merchandise_name"));
        TableColumn<view_takeorder, String> Sort_name = new TableColumn<>("类别");
        Sort_name.setCellValueFactory(new PropertyValueFactory<>("Sort_name"));
        TableColumn<view_takeorder, String> Merchandise_price = new TableColumn<>("价格");
        Merchandise_price.setCellValueFactory(new PropertyValueFactory<>("Merchandise_price"));
        TableColumn<view_takeorder, String> Disconut_price = new TableColumn<>("会员价");
        Disconut_price.setCellValueFactory(new PropertyValueFactory<>("Disconut_price"));

        TableColumn<view_takeorder, String> merchandise_name2 = new TableColumn<>("菜品名");
        merchandise_name2.setCellValueFactory(new PropertyValueFactory<>("merchandise_name"));
        TableColumn<view_takeorder, String> Sort_name2 = new TableColumn<>("类别");
        Sort_name2.setCellValueFactory(new PropertyValueFactory<>("Sort_name"));
        TableColumn<view_takeorder, String> Merchandise_price2 = new TableColumn<>("价格");
        Merchandise_price2.setCellValueFactory(new PropertyValueFactory<>("Merchandise_price"));
        TableColumn<view_takeorder, String> Disconut_price2 = new TableColumn<>("会员价");
        Disconut_price2.setCellValueFactory(new PropertyValueFactory<>("Disconut_price"));
        TableColumn<view_takeorder, Integer> count2 = new TableColumn<>("数量");
        count2.setCellValueFactory(new PropertyValueFactory<>("count"));
        tableView_r.setEditable(true);
        Callback<TableColumn<view_takeorder, Integer>,
                TableCell<view_takeorder, Integer>> cellFactory_Integer
                = (TableColumn<view_takeorder, Integer> p) -> new EditingcountCell_Integer();
        count2.setCellFactory(cellFactory_Integer);
        count2.setOnEditCommit(
                (TableColumn.CellEditEvent<view_takeorder, Integer> t) -> {
                    ((view_takeorder) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setCount(t.getNewValue());
                });

        middle_bottom.getItems().addAll(button_m2, button_back);
        middle.getChildren().addAll(label_m1, tableView_m, button_m1);

        tableView_m.setMinHeight(450);
        tableView_r.setMinHeight(450);
        middle.setMinWidth(425);
        middle.setMaxWidth(425);
        right.setMinWidth(425);
        right.setMaxWidth(425);

        button_m1.setOnAction((ActionEvent e) -> {
            try {
                this.Merchant_id = tableView_m.getSelectionModel().getSelectedItem().getMerchant_id();
                ObservableList<view_takeorder> data = new Consumer().takeorder_Merchandise(tableView_m.getSelectionModel().getSelectedItem());
                middle.getChildren().clear();
                middle.getChildren().addAll(label_m2, tableView_m, middle_bottom);
                tableView_m.getItems().clear();
                tableView_m.getColumns().clear();
                tableView_m.getColumns().addAll(merchandise_name, Sort_name, Merchandise_price, Disconut_price);
                tableView_m.setItems(data);
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        button_back.setOnAction((ActionEvent e) -> {
            middle.getChildren().clear();
            middle.getChildren().addAll(label_m1, tableView_m, button_m1);
            tableView_m.getItems().clear();
            tableView_m.getColumns().clear();
            tableView_m.getColumns().addAll(merchant_name, Merchant_level, Consume_avgprice, Total_sales);
            data_havingorder.clear();
            try {
                tableView_m.setItems(new Consumer().takeorder_Merchant());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            this.Merchant_id = 0;
        });

        button_m2.setOnAction((ActionEvent e) -> {
            int count_ = tableView_m.getSelectionModel().getSelectedItem().getCount()+1;
            int id = tableView_m.getSelectionModel().getSelectedItem().getMerchandise_id();
            tableView_m.getSelectionModel().getSelectedItem().setCount(count_);
            if (count_==1){
                view_takeorder a = new view_takeorder();
                a.setMerchant_id(tableView_m.getSelectionModel().getSelectedItem().getMerchant_id());
                a.setMerchandise_id(tableView_m.getSelectionModel().getSelectedItem().getMerchandise_id());
                a.setMerchandise_name(tableView_m.getSelectionModel().getSelectedItem().getMerchandise_name());
                a.setSort_name(tableView_m.getSelectionModel().getSelectedItem().getSort_name());
                a.setMerchandise_price(tableView_m.getSelectionModel().getSelectedItem().getMerchandise_price());
                a.setDisconut_price(tableView_m.getSelectionModel().getSelectedItem().getDisconut_price());
                a.setCount(count_);
                data_havingorder.add(a);
            } else {
                for (int i=0; i<data_havingorder.size(); i++) {
                    if (data_havingorder.get(i).getMerchandise_id()==id) {
                        data_havingorder.get(i).setCount(count_);
                        break;
                    }
                }
            }
            tableView_r.refresh();
        });

        button_r.setOnAction((ActionEvent e) -> {
            if (data_havingorder.size()==0) {
                try {
                    throw new BusinessException("购物车为空！");
                } catch (BusinessException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            } else {
                try {
                    check(data_havingorder);
                } catch (BaseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            }
        });

        button_recommend.setOnAction((ActionEvent e) -> {
            try {
                recommend(Merchant_id);
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        tableView_m.getItems().clear();
        tableView_m.getColumns().clear();
        tableView_m.getColumns().addAll(merchant_name, Merchant_level, Consume_avgprice, Total_sales);
        tableView_m.setItems(new Consumer().takeorder_Merchant());
        tableView_r.getColumns().addAll(merchandise_name2, Sort_name2, Merchandise_price2, Disconut_price2, count2);
        tableView_r.setItems(data_havingorder);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(button_recommend, button_r);
        right.getChildren().addAll(label_r, tableView_r, splitPane);
        init.getItems().addAll(left, middle, right);
    }

    private void recommend(int Merchant_id) throws BaseException{
        if (Merchant_id==0) throw new BusinessException("请先选择商家！");

        Stage recommend = new Stage();
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 250, 200);
        recommend.setScene(scene);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        view_takeorder data = new Consumer().recommend(Merchant_id);
        Label name = new Label("菜品名：");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        Label price = new Label("原价：");
        GridPane.setConstraints(price, 0, 1);
        grid.getChildren().add(price);
        Label priceD = new Label("会员价：");
        GridPane.setConstraints(priceD, 0, 2);
        grid.getChildren().add(priceD);
        Label addname = new Label(data.getMerchandise_name());
        GridPane.setConstraints(addname, 1, 0);
        grid.getChildren().add(addname);
        Label addprice = new Label(String.valueOf(data.getMerchandise_price()));
        GridPane.setConstraints(addprice, 1, 1);
        grid.getChildren().add(addprice);
        Label addpriceD = new Label(String.valueOf(data.getDisconut_price()));
        GridPane.setConstraints(addpriceD, 1, 2);
        grid.getChildren().add(addpriceD);
        Button accept = new Button("加入购物车");
        GridPane.setConstraints(accept, 1, 3);
        grid.getChildren().add(accept);
        accept.setOnAction((ActionEvent e) -> {
            int count_=1;
            for (int i=0; i<data_havingorder.size(); i++) {
                if (data_havingorder.get(i).getMerchandise_id() == data.getMerchandise_id()) {
                    count_ =  data_havingorder.get(i).getCount()+1;
                    break;
                }
            }
            int id = data.getMerchandise_id();
            if (count_==1){
                view_takeorder a = new view_takeorder();
                a.setMerchant_id(data.getMerchant_id());
                a.setMerchandise_id(data.getMerchandise_id());
                a.setMerchandise_name(data.getMerchandise_name());
                a.setSort_name(data.getSort_name());
                a.setMerchandise_price(data.getMerchandise_price());
                a.setDisconut_price(data.getDisconut_price());
                a.setCount(count_);
                data_havingorder.add(a);
            } else {
                for (int i=0; i<data_havingorder.size(); i++) {
                    if (data_havingorder.get(i).getMerchandise_id()==id) {
                        data_havingorder.get(i).setCount(count_);
                        break;
                    }
                }
            }
            tableView_r.refresh();
            recommend.close();
        });

        recommend.show();
        recommend.setTitle("推荐菜品");
    }

    private void check(ObservableList<view_takeorder> inf) throws BaseException{
        getDisCount(inf);

        Stage check = new Stage();
        GridPane grid = new GridPane();
        StackPane login = new StackPane();
        Scene scene = new Scene(login, 250, 200);
        Label aaa = new Label();
        Label labelsum1 = new Label("总金额：");
        Label labelreduce = new Label("满减：");
        Label labelcoupon = new Label("优惠券：");
        Label labelsum2 = new Label("优惠后金额：");
        Label labeladd = new Label("地址：");
        Label labeladddata = new Label(Address);
        Label labelsum1data = new Label(getSum(inf));
        Label labelreducedata = new Label(this.Reduction_id==-1 ? "0" : "-" + this.reduce);
        Label labelcoupondata = new Label(this.Coupon_id==-1 ? "0" : "-" + this.coupon);
        Label labelsum2data = new Label(String.valueOf(Float.valueOf(getSum(inf))-this.Discount));
        Button load = new Button("确认下单");

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        GridPane.setConstraints(labelsum1, 0, 0);
        grid.getChildren().add(labelsum1);
        GridPane.setConstraints(labelsum1data, 1, 0);
        grid.getChildren().add(labelsum1data);
        GridPane.setConstraints(labelreduce, 0, 1);
        grid.getChildren().add(labelreduce);
        GridPane.setConstraints(labelreducedata, 1, 1);
        grid.getChildren().add(labelreducedata);
        GridPane.setConstraints(labelcoupon, 0, 2);
        grid.getChildren().add(labelcoupon);
        GridPane.setConstraints(labelcoupondata, 1, 2);
        grid.getChildren().add(labelcoupondata);
        GridPane.setConstraints(labelsum2, 0, 3);
        grid.getChildren().add(labelsum2);
        GridPane.setConstraints(labelsum2data, 1, 3);
        grid.getChildren().add(labelsum2data);
        GridPane.setConstraints(labeladd, 0, 4);
        grid.getChildren().add(labeladd);
        GridPane.setConstraints(labeladddata, 1, 4);
        grid.getChildren().add(labeladddata);
        GridPane.setConstraints(aaa, 1, 5);
        grid.getChildren().add(aaa);
        GridPane.setConstraints(load, 1, 6);
        grid.getChildren().add(load);
        login.getChildren().addAll(grid);

        load.setOnAction((ActionEvent e) -> {
            try {
                new Consumer().takeorder(inf, Address_id, Float.valueOf(getSum(inf)), Float.valueOf(getSum(inf))-Discount,
                        Reduction_id, Coupon_id, Order_count, Merchant_id);
                check.close();
                collectCoupon();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        check.setScene(scene);
        check.setTitle("付款界面");
        check.show();
    }

    private void collectCoupon() throws BaseException {
        Stage collect = new Stage();
        StackPane login = new StackPane();
        GridPane grid = new GridPane();
        Scene scene = new Scene(login, 250, 200);
        RadioButton rb1 = null, rb2 = null, rb3 = null, rb4 = null, rb5 = null;
        Button buttonComplete = new Button("完成");
        ArrayList<Integer> coupon = new Consumer().loadcoupon(Merchant_id);
        login.getChildren().addAll(grid);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        int size = coupon.size();
        final ToggleGroup group = new ToggleGroup();
        if (size==0) {
            Label label = new Label("该商家占无优惠券");
            GridPane.setConstraints(label, 0, 0);
            grid.getChildren().add(label);
        } if (size>=2){
            Label label = new Label("请选择您想要集的优惠券");
            GridPane.setConstraints(label, 0, 0);
            grid.getChildren().add(label);
            rb1 = new RadioButton("集"+coupon.get(0)+"单，送"+coupon.get(1)+"元券");
            rb1.setToggleGroup(group);
            GridPane.setConstraints(rb1, 0, 1);
            grid.getChildren().add(rb1);
        } if (size>=5) {
            rb2 = new RadioButton("集"+coupon.get(3)+"单，送"+coupon.get(4)+"元券");
            rb2.setToggleGroup(group);
            GridPane.setConstraints(rb2, 0, 2);
            grid.getChildren().add(rb2);
        } if (size>=8) {
            rb3 = new RadioButton("集"+coupon.get(6)+"单，送"+coupon.get(7)+"元券");
            rb3.setToggleGroup(group);
            GridPane.setConstraints(rb3, 0, 3);
            grid.getChildren().add(rb3);
        } if (size>=11) {
            rb4 = new RadioButton("集"+coupon.get(9)+"单，送"+coupon.get(10)+"元券");
            rb4.setToggleGroup(group);
            GridPane.setConstraints(rb4, 0, 4);
            grid.getChildren().add(rb4);
        } if (size>=14) {
            rb5 = new RadioButton("集"+coupon.get(12)+"单，送"+coupon.get(13)+"元券");
            rb5.setToggleGroup(group);
            GridPane.setConstraints(rb5, 0, 5);
            grid.getChildren().add(rb5);
        }
        GridPane.setConstraints(buttonComplete, 0, 6);
        grid.getChildren().add(buttonComplete);

        RadioButton finalRb = rb1;
        RadioButton finalRb1 = rb2;
        RadioButton finalRb2 = rb3;
        RadioButton finalRb3 = rb4;
        RadioButton finalRb4 = rb5;
        buttonComplete.setOnAction((ActionEvent e) -> {
            if (size==0) {
                collect.close();
                return ;
            }
            try {
                if (finalRb.isSelected()) {
                    new Consumer().collect(finalRb.getText(), coupon, Merchant_id);
                } else if (finalRb1.isSelected()){
                    new Consumer().collect(finalRb1.getText(), coupon, Merchant_id);
                } else if (finalRb2.isSelected()){
                    new Consumer().collect(finalRb2.getText(), coupon, Merchant_id);
                } else if (finalRb3.isSelected()){
                    new Consumer().collect(finalRb3.getText(), coupon, Merchant_id);
                } else if (finalRb4.isSelected()){
                    new Consumer().collect(finalRb4.getText(), coupon, Merchant_id);
                    collect.close();
                }
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            collect.close();
        });

        collect.setScene(scene);
        collect.setTitle("集券界面");
        collect.show();
    }

    public String getSum(ObservableList<view_takeorder> inf) {
        double sum = 0.0;
        for (int i=0; i<inf.size(); i++) {
            if (this.member){
                sum += inf.get(i).getCount()*inf.get(i).getDisconut_price();
            } else {
                sum += inf.get(i).getCount()*inf.get(i).getMerchandise_price();
            }
        }
        return String.valueOf(sum);
    }

    public void getDisCount(ObservableList<view_takeorder> inf) throws BaseException {
        this.Merchant_id = inf.get(0).getMerchant_id();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select max(Red_Amount+Discount_price) Discount, Red_Amount, Discount_price, Reduction_id, Coupon_id, Order_count, Merchant_id from view_allDiscount where Merchant_id = ? and Consumer_id = ? and Red_Aim <= ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, this.Merchant_id);
            pst.setInt(2, FrmMainLogin.Currentuserid);
            pst.setFloat(3, Float.valueOf(getSum(inf)));
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                if (rs.getFloat(1)==0){
                    sql = "select max(Red_Amount), Reduction_id, Merchant_id from full_reduction where Merchant_id = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, this.Merchant_id);
                    rs = pst.executeQuery();
                    if (rs.next()){
                        if (rs.getFloat(1)==0){
                            this.Discount = Float.valueOf(0);
                            this.coupon = Float.valueOf(-1);
                            this.reduce = Float.valueOf(0);
                            this.Reduction_id = -1;
                            this.Coupon_id = -1;
                            this.Order_count = 0;
                        } else {
                            this.Discount = rs.getFloat(1);
                            this.coupon = Float.valueOf(-1);
                            this.reduce = rs.getFloat(1);
                            this.Reduction_id = rs.getInt(2);
                            this.Coupon_id = -1;
                            this.Order_count = 0;
                        }
                    }
                } else {
                    this.Discount = rs.getFloat(1);
                    this.coupon = rs.getFloat(2);
                    this.reduce = rs.getFloat(3);
                    this.Reduction_id = rs.getInt(4);
                    this.Coupon_id = rs.getInt(5);
                    this.Order_count = rs.getInt(6);
                    this.Merchant_id = rs.getInt(7);
                }
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
//        return sum<0 ? "0" : String.valueOf(sum);
    }

    public Boolean getAddress_id() throws BaseException{
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select Address_id, Address_add from delivery_address where Consumer_id = ? and Default_ = true";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, FrmMainLogin.Currentuserid);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) return true;
            this.Address_id = rs.getInt(1);
            this.Address = rs.getString(2);
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return false;
    }

    class EditingRidercomment_String extends TableCell<view_order, String> {

        private TextField textField;

        public EditingRidercomment_String() {
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

    static class EditingConinf_String extends TableCell<Consumer_information, String> {

        private TextField textField;

        public EditingConinf_String() {
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

    class EditingConinf_Boolean extends TableCell<Consumer_information, Boolean> {

        private TextField textField;

        public EditingConinf_Boolean() {
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

            setText(String.valueOf(getItem()));
            setGraphic(null);
        }

        @Override
        public void updateItem(Boolean item, boolean empty) {
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
                            commitEdit(Boolean.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingAddressCell_String extends TableCell<delivery_address, String> {

        private TextField textField;

        public EditingAddressCell_String() {
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

            setText((String) getItem() );
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

    class EditingcountCell_Integer extends TableCell<view_takeorder, Integer> {

        private TextField textField;

        public EditingcountCell_Integer() {
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

            setText( String.valueOf(getItem()));
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
//            System.out.println(item);

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
                            commitEdit(Integer.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
