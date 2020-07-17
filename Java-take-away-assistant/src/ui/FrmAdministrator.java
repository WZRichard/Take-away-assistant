package ui;

import control.Admin;
import control.Consumer;
import control.Merchant;
import control.Rider;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Consumer_information;
import model.Rider_information;
import model.delivery_address;
import model.view_order;
import util.BaseException;
import util.BusinessException;
import ui.FrmMerchant.Mer_information;

import javax.swing.*;
import java.util.Date;

public class FrmAdministrator {

    public void Admin_Init(Stage primaryStage){

        //创建sence组件
        Scene sence_init = null;
        Button Admin_orderQuery = new Button("订单查询");
        Button Admin_member = new Button("顾客会员办理");
        Button Admin_Consumerinf = new Button("顾客信息修改查询");
        Button Admin_Merchantinf = new Button("商家信息修改查询");
        Button Admin_Riderrinf = new Button("骑手信息修改查询");
        Button exit = new Button("退出");

        // 创建布局控件 添加到布局
        SplitPane init = new SplitPane();
        VBox left = new VBox();
        init.getItems().addAll(left);
        left.setMaxWidth(180);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(Admin_orderQuery);
        left.getChildren().add(Admin_member);
        left.getChildren().add(Admin_Consumerinf);
        left.getChildren().add(Admin_Merchantinf);
        left.getChildren().add(Admin_Riderrinf);
        left.getChildren().add(exit);
        left.getStylesheets().add("/NodeStyle.css");
        Admin_orderQuery.getStyleClass().addAll("mainButton");
        Admin_member.getStyleClass().addAll("mainButton");
        Admin_Consumerinf.getStyleClass().addAll("mainButton");
        Admin_Merchantinf.getStyleClass().addAll("mainButton");
        Admin_Riderrinf.getStyleClass().addAll("mainButton");
        exit.getStyleClass().addAll("mainButton");

        //创建场景
        sence_init = new Scene(init, 1000, 500);

        // 给按钮添加事件处理的对象
        Admin_orderQuery.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, orderQuery());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
            }
        });

        Admin_member.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, setmember());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Admin_Consumerinf.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, Consumerinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Admin_Merchantinf.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, Merchantinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Admin_Riderrinf.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, Riderrinf());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        exit.setOnMouseClicked(e -> {
            primaryStage.setScene(FrmMainLogin.scene_F);
            primaryStage.setTitle("外卖助手");
        });

        primaryStage.setScene(sence_init);
        primaryStage.setTitle("外卖助手-管理员:"+FrmMainLogin.Currentusername);
    }

    private VBox Consumerinf() throws BaseException {
        TableView<Consumer_information> tableView = new TableView<Consumer_information>();
        ObservableList<Consumer_information> data = new Admin().loadConinf();
        tableView.setEditable(true);
        tableView.setMinHeight(450);

        TableColumn<Consumer_information, Integer> Consumer_id =
                new TableColumn<>("编号");
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
                = (TableColumn<Consumer_information, String> p) -> new FrmConsumer.EditingConinf_String();

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

        Consumer_id.setMinWidth(50);
        Consumer_id.setCellValueFactory(
                new PropertyValueFactory<>("Consumer_id"));

        tableView.getColumns().addAll(Consumer_id, Consumer_name, Consumer_pwd, Consumer_gender, Consumer_phonenum, Consumer_mail, Consumer_city, Consumer_member);
        tableView.setItems(data);

        final TextField addConsid = new TextField();
        addConsid.setPromptText("顾客编号");
        addConsid.setMaxWidth(Consumer_id.getPrefWidth()*1.2);
        final Button ConsidButton = new Button("查询");
        ConsidButton.setOnAction((ActionEvent e) -> {
            try {
                if (addConsid.getText().isEmpty()) {
                    throw new BusinessException("请输入编号！");
                }
                data.clear();
                data.add(new Admin().queryConsumerinf(Integer.valueOf(addConsid.getText())));
                tableView.refresh();
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addConsid, ConsidButton);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(splitPane, tableView);
        vbox.setSpacing(3);
        return vbox;
    }

    private VBox orderQuery() throws BaseException {
        TableView<view_order> tableView = new TableView<>();
        ObservableList<view_order> data = new Admin().loadAllOrder();
        tableView.setMinHeight(450);

        TableColumn<view_order, Integer> Order_id =
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
        TableColumn<view_order, String> Comment_Rider =
                new TableColumn<>("骑手评价");
        Comment_Rider.setCellValueFactory(new PropertyValueFactory<>("Comment_Rider"));

        final TextField addQuery = new TextField();
        addQuery.setPromptText("订单编号");
        addQuery.setMaxWidth(Address_phonenum.getPrefWidth()*1.2);
        final Button queryButton = new Button("查询");
        queryButton.setOnAction((ActionEvent e) -> {
            try {
                if (addQuery.getText().isEmpty()) throw new BusinessException("请输入编号！");
                data.clear();
                data.add(new Admin().queryOrder(Integer.valueOf(addQuery.getText())));
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
        tableView.getColumns().addAll(Order_id, Consumer_name, Merchant_name, Rider_name, Order_platime, Order_deltime, Address, Address_linkman, Address_phonenum, Comment_Rider);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addQuery, queryButton);
        vbox.getChildren().addAll(splitPane, tableView );
        vbox.setSpacing(3);
        return vbox;
    }

    private VBox Merchantinf() throws BaseException {
        TableView<Mer_information> tableView = new TableView<>();
        ObservableList<Mer_information> data = new Admin().loadMerinf();
        tableView.setMinHeight(450);
        tableView.setEditable(true);

        TableColumn<Mer_information, String> id =
                new TableColumn<>("ID");
        TableColumn<Mer_information, String> name =
                new TableColumn<>("Name");
        TableColumn<Mer_information, String> pwd =
                new TableColumn<>("Pwd");
        TableColumn<Mer_information, Integer> level =
                new TableColumn<>("Level");
        TableColumn<Mer_information, Float> avgprice =
                new TableColumn<>("Avg_price");
        TableColumn<Mer_information, Integer> Total_sales =
                new TableColumn<>("Total_sales");

        Callback<TableColumn<Mer_information, String>,
                TableCell<Mer_information, String>> cellFactory_String
                = (TableColumn<Mer_information, String> p) -> new FrmMerchant.EditingCell_String();

        Callback<TableColumn<Mer_information, Integer>, TableCell<Mer_information, Integer>> cellFactory_Integer
                = (TableColumn<Mer_information, Integer> p) -> new FrmMerchant.EditingCell_Integer();

        Callback<TableColumn<Mer_information, Float>, TableCell<Mer_information, Float>> cellFactory_Float
                = (TableColumn<Mer_information, Float> p) -> new FrmMerchant.EditingCell_Float();

        id.setMinWidth(100);
        id.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        name.setMinWidth(100);
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        name.setCellFactory(cellFactory_String);
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, String> t) -> {
                    ((FrmMerchant.Mer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                    try {
                        new Merchant().updateMer_information(t.getRowValue(), 2);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        pwd.setMinWidth(100);
        pwd.setCellValueFactory(
                new PropertyValueFactory<>("pwd"));
        pwd.setCellFactory(cellFactory_String);
        pwd.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, String> t) -> {
                    ((FrmMerchant.Mer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setPwd(t.getNewValue());
                    try {
                        new Merchant().updateMer_information(t.getRowValue(), 6);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        level.setMinWidth(100);
        level.setCellValueFactory(
                new PropertyValueFactory<>("level"));
        level.setCellFactory(cellFactory_Integer);
        level.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, Integer> t) -> {
                    ((FrmMerchant.Mer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setLevel(t.getNewValue());
                    try {
                        new Merchant().updateMer_information(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        avgprice.setMinWidth(100);
        avgprice.setCellValueFactory(
                new PropertyValueFactory<>("avgprice"));
        avgprice.setCellFactory(cellFactory_Float);
        avgprice.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, Float> t) -> {
                    ((FrmMerchant.Mer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setAvgprice(Float.valueOf(t.getNewValue()));
                    System.out.println();
                    try {
                        new Merchant().updateMer_information(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Total_sales.setMinWidth(100);
        Total_sales.setCellValueFactory(
                new PropertyValueFactory<>("Total_sales"));

        final TextField addQuery = new TextField();
        addQuery.setPromptText("商家编号");
        addQuery.setMaxWidth(name.getPrefWidth()*1.2);
        final Button queryButton = new Button("查询");
        queryButton.setOnAction((ActionEvent e) -> {
            try {
                if (addQuery.getText().isEmpty()) throw new BusinessException("请输入编号！");
                data.clear();
                data.add(new Admin().queryMerinf(Integer.valueOf(addQuery.getText())));
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
        tableView.getColumns().addAll(id, name, pwd, level, avgprice, Total_sales);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addQuery, queryButton);
        vbox.getChildren().addAll(splitPane, tableView );
        vbox.setSpacing(3);
        return vbox;
    }

    private VBox Riderrinf() throws BaseException {
        TableView<Rider_information> tableView = new TableView<>();
        ObservableList<Rider_information> data = new Admin().loadRiderinf();
        tableView.setEditable(true);
        tableView.setMinHeight(450);

        TableColumn<Rider_information, Integer> Rider_id =
                new TableColumn<>("ID");
        TableColumn<Rider_information, String> name =
                new TableColumn<>("Name");
        TableColumn<Rider_information, String> pwd =
                new TableColumn<>("Pwd");
        TableColumn<Rider_information, String> sort =
                new TableColumn<>("类别");

        Callback<TableColumn<Rider_information, String>,
                TableCell<Rider_information, String>> cellFactory_String
                = (TableColumn<Rider_information, String> p) -> new FrmRider.EditingRiderinf_String();

        Rider_id.setMinWidth(100);
        Rider_id.setCellValueFactory(
                new PropertyValueFactory<>("Rider_id"));

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

        final TextField addQuery = new TextField();
        addQuery.setPromptText("商家编号");
        addQuery.setMaxWidth(name.getPrefWidth()*1.2);
        final Button queryButton = new Button("查询");
        queryButton.setOnAction((ActionEvent e) -> {
            try {
                if (addQuery.getText().isEmpty()) throw new BusinessException("请输入编号！");
                data.clear();
                data.add(new Admin().queryRiderinf(Integer.valueOf(addQuery.getText())));
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
        tableView.getColumns().addAll(Rider_id, name, pwd, sort);
        tableView.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addQuery, queryButton);
        vbox.getChildren().addAll(splitPane, tableView );
        vbox.setSpacing(3);
        return vbox;
    }

    private GridPane setmember() throws BaseException {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        Label id = new Label("顾客编号");
        GridPane.setConstraints(id, 0, 0);
        grid.getChildren().add(id);
        final TextField addid = new TextField();
        addid.setPromptText("填写顾客编号");
        GridPane.setConstraints(addid, 1, 0);
        grid.getChildren().add(addid);
        Label name = new Label("顾客姓名");
        GridPane.setConstraints(name, 0, 1);
        grid.getChildren().add(name);
        final TextField addname = new TextField();
        addname.setPromptText("填写顾客姓名");
        GridPane.setConstraints(addname, 1, 1);
        grid.getChildren().add(addname);
        Label date = new Label("到期时间");
        GridPane.setConstraints(date, 0, 2);
        grid.getChildren().add(date);
        final TextField adddate = new TextField();
        adddate.setPromptText("格式：yyyy-MM-DD");
        GridPane.setConstraints(adddate, 1, 2);
        grid.getChildren().add(adddate);

        final Button memberButton = new Button("办理会员");
        memberButton.setOnAction((ActionEvent e) -> {
            try {
                if (addid.getText().isEmpty() || addname.getText().isEmpty() || adddate.getText().isEmpty())
                    throw new BusinessException("请输入顾客及会员信息！");
                new Admin().setmember(Integer.valueOf(addid.getText()), addname.getText(), adddate.getText());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addid.clear();
            addname.clear();
            adddate.clear();
        });
        GridPane.setConstraints(memberButton, 1, 3);
        grid.getChildren().add(memberButton);

        return grid;
    }
}
