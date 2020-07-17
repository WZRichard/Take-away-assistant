package ui;

import control.Merchandise_sort;
import control.Merchant;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Discount_coupon;
import model.view_loadrecommend;
import model.view_order;
import util.BaseException;
import util.BusinessException;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FrmMerchant {

    public void Mer_Init(Stage primaryStage) {
        //创建sence组件
        Scene sence_init;
        Button Mer_updatecoupon = new Button("更新优惠券");
        Button Mer_loadorder = new Button("查看订单信息");
        Button Mer_loadcomment = new Button("查看订单评价");
        Button Mer_insertmerchandisesort = new Button("更新商品种类");
        Button Mer_updatereduce = new Button("更新满减");
        Button Mer_uploadmerchandise = new Button("更新商品");
        Button Mer_changeinformation = new Button("修改商家信息");
        Button exit = new Button("退出");

        // 创建布局控件 添加到布局
        SplitPane init = new SplitPane();
        VBox left = new VBox();
        TableView<Mer_information> right = new TableView<>();
        init.getItems().addAll(left, right);
        left.setMaxWidth(220);
        left.setAlignment(Pos.CENTER);
        left.getChildren().add(Mer_uploadmerchandise);
        left.getChildren().add(Mer_updatereduce);
        left.getChildren().add(Mer_updatecoupon);
        left.getChildren().add(Mer_loadcomment);
        left.getChildren().add(Mer_loadorder);
        left.getChildren().add(Mer_changeinformation);
        left.getChildren().add(Mer_insertmerchandisesort);
        left.getChildren().add(exit);
        left.getStylesheets().add("/NodeStyle.css");
        Mer_updatecoupon.getStyleClass().addAll("mainButton");
        Mer_loadorder.getStyleClass().addAll("mainButton");
        Mer_loadcomment.getStyleClass().addAll("mainButton");
        Mer_insertmerchandisesort.getStyleClass().addAll("mainButton");
        Mer_updatereduce.getStyleClass().addAll("mainButton");
        Mer_uploadmerchandise.getStyleClass().addAll("mainButton");
        Mer_changeinformation.getStyleClass().addAll("mainButton");
        exit.getStyleClass().addAll("mainButton");

        //创建场景
        sence_init = new Scene(init, 800, 400);

        // 给按钮添加事件处理的对象
        Mer_changeinformation.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_changeinformation(right));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Mer_uploadmerchandise.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_updatemerchandise());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Mer_insertmerchandisesort.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_updatemerchandisesort());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Mer_updatereduce.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_updatereduce());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Mer_updatecoupon.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_updatecoupon());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        Mer_loadorder.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_loadorder());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        exit.setOnMouseClicked(e -> {
            primaryStage.setScene(FrmMainLogin.scene_F);
            primaryStage.setTitle("外卖助手");
        });

        Mer_loadcomment.setOnMouseClicked(e -> {
            init.getItems().clear();
            try {
                init.getItems().addAll(left, view_loadcomment(init, primaryStage));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

                // 将场景添加到窗口
        primaryStage.setScene(sence_init);
        primaryStage.setTitle("外卖助手-商家:"+FrmMainLogin.Currentusername);
    }

    private VBox view_loadcomment(SplitPane init, Stage primaryStage) throws BaseException {
        ObservableList<view_loadrecommend> data = new Merchant().loadComment();
        VBox vbox = new VBox();
        Button comment = new Button("查看评价");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        TableView<view_loadrecommend> tableView = new TableView<view_loadrecommend>();
        tableView.setMaxWidth(300);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        TableColumn<view_loadrecommend, String> Merchant_name =
                new TableColumn<>("商家名");
        TableColumn<view_loadrecommend, String> Merchandise_name =
                new TableColumn<>("菜品名");
        TableColumn<view_loadrecommend, String> Comment_Date =
                new TableColumn<>("评价日期");

        Merchant_name.setMinWidth(80);
        Merchant_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchant_name"));
        Merchandise_name.setMinWidth(80);
        Merchandise_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchandise_name"));
        Comment_Date.setMinWidth(80);
        Comment_Date.setCellValueFactory(
                new PropertyValueFactory<>("Comment_Date"));

        comment.setOnAction((ActionEvent e) -> {
            if (tableView.getSelectionModel().getSelectedItem()!=null){
                init.getItems().remove(grid);
                grid.getChildren().clear();

                Label level = new Label("星级");
                GridPane.setConstraints(level, 0, 0);
                grid.getChildren().add(level);
                Label lcomment = new Label("评价");
                GridPane.setConstraints(lcomment, 0, 1);
                grid.getChildren().add(lcomment);
                Label picture = new Label("添加图片");
                GridPane.setConstraints(picture, 0, 2);
                grid.getChildren().add(picture);

                int index = tableView.getSelectionModel().getSelectedIndex();
                Label addlevel = new Label(String.valueOf(data.get(index).getComment_level()));
                GridPane.setConstraints(addlevel, 1, 0);
                grid.getChildren().add(addlevel);
                Label addlcomment = new Label(data.get(index).getComment_content());
                GridPane.setConstraints(addlcomment, 1, 1);
                grid.getChildren().add(addlcomment);
                ImageView addpicture = new ImageView();
                addpicture.setImage(data.get(index).getComment_picture());
                addpicture.setFitWidth(100);
                addpicture.setFitHeight(100);
                GridPane.setConstraints(addpicture, 1, 2);
                grid.getChildren().add(addpicture);
                grid.setMaxWidth(250);
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

        Label label = new Label("已评价订单：");
        tableView.getColumns().addAll(Merchant_name, Merchandise_name, Comment_Date);
        tableView.setItems(data);
        vbox.getChildren().addAll(label, tableView, comment);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox view_updatemerchandisesort() throws BaseException {
        ObservableList<Merchandisesort_information> data = new Merchandise_sort().loadallMerchandise_sort();
        TableView<Merchandisesort_information> right = new TableView<>();
        right.setEditable(true);

        TableColumn<Merchandisesort_information, String> name =
                new TableColumn<>("Name");

        Callback<TableColumn<Merchandisesort_information, String>,
                TableCell<Merchandisesort_information, String>> cellFactory_String
                = (TableColumn<Merchandisesort_information, String> p) -> new EditingMerchandisesortCell_String();

        name.setMinWidth(100);
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        name.setCellFactory(cellFactory_String);
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Merchandisesort_information, String> t) -> {
                    ((Merchandisesort_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                    try {
                        new Merchandise_sort().updateMerchandise_sort(t.getRowValue());
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        final TextField addName = new TextField();
        addName.setPromptText("addName");
        addName.setMaxWidth(name.getPrefWidth());


        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                data.add(new Merchandise_sort().insertMerchandise_sort(addName.getText()));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addName.clear();
        });

        final Button delectButton = new Button("DELECT");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                new Merchandise_sort().delecechandisesort(right.getSelectionModel().getSelectedItem());
                data.remove(right.getSelectionModel().getSelectedIndex());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addName.clear();
        });

//        right.setMaxWidth(500);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        right.getColumns().clear();
        right.getColumns().add(name);
        right.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addName, addButton,delectButton);
        vbox.getChildren().addAll(right, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox view_updatemerchandise() throws BaseException {
        ObservableList<Merchandise_information> data = new Merchant().loadallMerchandise();
        TableView<Merchandise_information> right = new TableView<>();
        right.setEditable(true);

        TableColumn<Merchandise_information, String> Merchandise_name =
                new TableColumn<>("Name");
        TableColumn<Merchandise_information, String> Sort_name =
                new TableColumn<>("Sort");
        TableColumn<Merchandise_information, Float> Merchandise_price =
                new TableColumn<>("Price");
        TableColumn<Merchandise_information, Float> Disconut_price =
                new TableColumn<>("Discount");

        Callback<TableColumn<Merchandise_information, String>,
                TableCell<Merchandise_information, String>> cellFactory_String
                = (TableColumn<Merchandise_information, String> p) -> new EditingMerchandiseCell_String();

        Callback<TableColumn<Merchandise_information, Float>,
                TableCell<Merchandise_information, Float>> cellFactory_Float
                = (TableColumn<Merchandise_information, Float> p) -> new EditingMerchandiseCell_Float();

        Merchandise_name.setMinWidth(100);
        Merchandise_name.setCellValueFactory(
                new PropertyValueFactory<>("Merchandise_name"));
        Merchandise_name.setCellFactory(cellFactory_String);
        Merchandise_name.setOnEditCommit(
                (TableColumn.CellEditEvent<Merchandise_information, String> t) -> {
                    ((Merchandise_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setMerchandise_name(t.getNewValue());
                    try {
                        new Merchant().updateMerchandise(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Sort_name.setMinWidth(100);
        Sort_name.setCellValueFactory(
                new PropertyValueFactory<>("Sort_name"));
        Sort_name.setCellFactory(cellFactory_String);
        Sort_name.setOnEditCommit(
                (TableColumn.CellEditEvent<Merchandise_information, String> t) -> {
                    ((Merchandise_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSort_name(t.getNewValue());
                    try {
                        new Merchant().updateMerchandise(t.getRowValue(), 0);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Merchandise_price.setMinWidth(100);
        Merchandise_price.setCellValueFactory(
                new PropertyValueFactory<>("Merchandise_price"));
        Merchandise_price.setCellFactory(cellFactory_Float);
        Merchandise_price.setOnEditCommit(
                (TableColumn.CellEditEvent<Merchandise_information, Float> t) -> {
                    ((Merchandise_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setMerchandise_price(t.getNewValue());
                    try {
                        new Merchant().updateMerchandise(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Disconut_price.setMinWidth(100);
        Disconut_price.setCellValueFactory(
                new PropertyValueFactory<>("Disconut_price"));
        Disconut_price.setCellFactory(cellFactory_Float);
        Disconut_price.setOnEditCommit(
                (TableColumn.CellEditEvent<Merchandise_information, Float> t) -> {
                    ((Merchandise_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDisconut_price(t.getNewValue());
                    try {
                        new Merchant().updateMerchandise(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        final TextField addName = new TextField();
        addName.setPromptText("Merchandise");
        addName.setMaxWidth(Merchandise_name.getPrefWidth()*1.2);
        final TextField addSore_name = new TextField();
        addSore_name.setPromptText("Sort");
        addSore_name.setMaxWidth(Merchandise_price.getPrefWidth()*1.2);
        final TextField addMerchandise_price = new TextField();
        addMerchandise_price.setPromptText("Price");
        addMerchandise_price.setMaxWidth(Merchandise_price.getPrefWidth()*1.2);
        final TextField addDisconut_price = new TextField();
        addDisconut_price.setPromptText("Disconut");
        addDisconut_price.setMaxWidth(Disconut_price.getPrefWidth()*1.2);


        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                if (addName.getText().isEmpty() || addSore_name.getText().isEmpty() || addMerchandise_price.getText().isEmpty() || addDisconut_price.getText().isEmpty()){
                    throw new BusinessException("信息未输入完整!");
                } else {
                    data.add(new Merchant().insertMerchandise(addName.getText(), addSore_name.getText(), Float.valueOf(addMerchandise_price.getText()),
                            Float.valueOf(addDisconut_price.getText())));
                }
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addName.clear();
            addSore_name.clear();
            addMerchandise_price.clear();
            addDisconut_price.clear();
        });


        final Button delectButton = new Button("DELECT");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                new Merchant().deletemerchandise(right.getSelectionModel().getSelectedItem());
                data.remove(right.getSelectionModel().getSelectedIndex());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addName.clear();
        });

//        right.setMaxWidth(500);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        right.getColumns().clear();
        right.getColumns().addAll(Merchandise_name, Sort_name, Merchandise_price, Disconut_price);
        right.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addName, addSore_name, addMerchandise_price, addDisconut_price, addButton, delectButton);
        vbox.getChildren().addAll(right, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    public TableView view_changeinformation(TableView<Mer_information> right) throws BaseException {
        right.setEditable(true);

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
                = (TableColumn<Mer_information, String> p) -> new EditingCell_String();

        Callback<TableColumn<Mer_information, Integer>, TableCell<Mer_information, Integer>> cellFactory_Integer
                = (TableColumn<Mer_information, Integer> p) -> new EditingCell_Integer();

        Callback<TableColumn<Mer_information, Float>, TableCell<Mer_information, Float>> cellFactory_Float
                = (TableColumn<Mer_information, Float> p) -> new EditingCell_Float();

        name.setMinWidth(100);
        name.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        name.setCellFactory(cellFactory_String);
        name.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, String> t) -> {
                    ((Mer_information) t.getTableView().getItems().get(
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
                    ((Mer_information) t.getTableView().getItems().get(
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
                    ((Mer_information) t.getTableView().getItems().get(
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
                    ((Mer_information) t.getTableView().getItems().get(
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
        Total_sales.setCellFactory(cellFactory_Integer);
        Total_sales.setOnEditCommit(
                (TableColumn.CellEditEvent<Mer_information, Integer> t) -> {
                    ((Mer_information) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setTotal_sales(Integer.valueOf(t.getNewValue()));
                    try {
                        new Merchant().updateMer_information(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        right.getColumns().clear();
        right.getColumns().addAll(name, pwd, level, avgprice, Total_sales);
        right.setItems(new Merchant().getMer_information());
        return right;
    }

    public VBox view_updatereduce() throws BaseException {
        ObservableList<reduce_inf> data = new Merchant().loadallreduce();
        TableView<reduce_inf> right = new TableView<>();
        right.setEditable(true);

        TableColumn<reduce_inf, Float> Red_Amount =
                new TableColumn<>("满减金额");
        TableColumn<reduce_inf, Float> Red_Aim =
                new TableColumn<>("目标金额");
        TableColumn<reduce_inf, Boolean> Support_coupon =
                new TableColumn<>("WithCoupon");

        Callback<TableColumn<reduce_inf, Float>,
                TableCell<reduce_inf, Float>> cellFactory_Float
                = (TableColumn<reduce_inf, Float> p) -> new EditingreduceCell_Float();

        Callback<TableColumn<reduce_inf, Boolean>,
                TableCell<reduce_inf, Boolean>> cellFactory_Boolean
                = (TableColumn<reduce_inf, Boolean> p) -> new EditingreduceCell_Boolean();

        Red_Amount.setMinWidth(100);
        Red_Amount.setCellValueFactory(
                new PropertyValueFactory<>("Red_Amount"));
        Red_Amount.setCellFactory(cellFactory_Float);
        Red_Amount.setOnEditCommit(
                (TableColumn.CellEditEvent<reduce_inf, Float> t) -> {
                    ((reduce_inf) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setRed_Amount(t.getNewValue());
                    try {
                        new Merchant().updatereduce(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Red_Aim.setMinWidth(100);
        Red_Aim.setCellValueFactory(
                new PropertyValueFactory<>("Red_Aim"));
        Red_Aim.setCellFactory(cellFactory_Float);
        Red_Aim.setOnEditCommit(
                (TableColumn.CellEditEvent<reduce_inf, Float> t) -> {
                    ((reduce_inf) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setRed_Aim(t.getNewValue());
                    try {
                        new Merchant().updatereduce(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Support_coupon.setMinWidth(100);
        Support_coupon.setCellValueFactory(
                new PropertyValueFactory<>("Support_coupon"));
        Support_coupon.setCellFactory(cellFactory_Boolean);
        Support_coupon.setOnEditCommit(
                (TableColumn.CellEditEvent<reduce_inf, Boolean> t) -> {
                    ((reduce_inf) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSupport_coupon(t.getNewValue());
                    try {
                        new Merchant().updatereduce(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        final TextField addAmount = new TextField();
        addAmount.setPromptText("满减金额");
        addAmount.setMaxWidth(Red_Amount.getPrefWidth()*1.2);
        final TextField addAim = new TextField();
        addAim.setPromptText("目标金额");
        addAim.setMaxWidth(Red_Aim.getPrefWidth()*1.2);
        final TextField addCoupon = new TextField();
        addCoupon.setPromptText("是否可与优惠券同享");
        addCoupon.setMaxWidth(Support_coupon.getPrefWidth()*1.8);

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                data.add(new Merchant().insertreduce(Float.valueOf(addAmount.getText()), Float.valueOf(addAim.getText()), Boolean.valueOf(addCoupon.getText())));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addAmount.clear();
            addAim.clear();
            addCoupon.clear();
        });

        final Button delectButton = new Button("DELECT");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                new Merchant().delectreduce(right.getSelectionModel().getSelectedItem());
                data.remove(right.getSelectionModel().getSelectedIndex());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

//        right.setMaxWidth(500);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        right.getColumns().clear();
        right.getColumns().addAll(Red_Amount, Red_Aim, Support_coupon);
        right.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addAmount, addAim, addCoupon, addButton, delectButton);
        vbox.getChildren().addAll(right, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox view_updatecoupon() throws BaseException {
        ObservableList<Discount_coupon> data = new Merchant().loadallcoupon();
        TableView<Discount_coupon> right = new TableView<>();
        right.setEditable(true);

        TableColumn<Discount_coupon, Float> Discount_price =
                new TableColumn<>("优惠金额");
        TableColumn<Discount_coupon, Integer> Order_count =
                new TableColumn<>("集单目标");
        TableColumn<Discount_coupon, Date> Starttime =
                new TableColumn<>("开始时间");
        TableColumn<Discount_coupon, Date> Finishtime =
                new TableColumn<>("结束时间");

        Callback<TableColumn<Discount_coupon, Float>,
                TableCell<Discount_coupon, Float>> cellFactory_Float
                = (TableColumn<Discount_coupon, Float> p) -> new EditingcouponCell_Float();

        Callback<TableColumn<Discount_coupon, Integer>,
                TableCell<Discount_coupon, Integer>> cellFactory_Integer
                = (TableColumn<Discount_coupon, Integer> p) -> new EditingcouponCell_Integer();

        Callback<TableColumn<Discount_coupon, Date>,
                TableCell<Discount_coupon, Date>> cellFactory_Date
                = (TableColumn<Discount_coupon, Date> p) -> new EditingcouponCell_Date();

        Discount_price.setMinWidth(50);
        Discount_price.setCellValueFactory(
                new PropertyValueFactory<>("Discount_price"));
        Discount_price.setCellFactory(cellFactory_Float);
        Discount_price.setOnEditCommit(
                (TableColumn.CellEditEvent<Discount_coupon, Float> t) -> {
                    ((Discount_coupon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setDiscount_price(t.getNewValue());
                    try {
                        new Merchant().updatecoupon(t.getRowValue(), 3);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Order_count.setMinWidth(50);
        Order_count.setCellValueFactory(
                new PropertyValueFactory<>("Order_count"));
        Order_count.setCellFactory(cellFactory_Integer);
        Order_count.setOnEditCommit(
                (TableColumn.CellEditEvent<Discount_coupon, Integer> t) -> {
                    ((Discount_coupon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOrder_count(t.getNewValue());
                    try {
                        new Merchant().updatecoupon(t.getRowValue(), 4);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Starttime.setMinWidth(150);
        Starttime.setCellValueFactory(
                new PropertyValueFactory<>("Starttime"));
        Starttime.setCellFactory(cellFactory_Date);
        Starttime.setOnEditCommit(
                (TableColumn.CellEditEvent<Discount_coupon, Date> t) -> {
                    ((Discount_coupon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setStarttime(t.getNewValue());
                    try {
                        new Merchant().updatecoupon(t.getRowValue(), 5);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        Finishtime.setMinWidth(150);
        Finishtime.setCellValueFactory(
                new PropertyValueFactory<>("Finishtime"));
        Finishtime.setCellFactory(cellFactory_Date);
        Finishtime.setOnEditCommit(
                (TableColumn.CellEditEvent<Discount_coupon, Date> t) -> {
                    ((Discount_coupon) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setFinishtime(t.getNewValue());
                    try {
                        new Merchant().updatecoupon(t.getRowValue(), 6);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                        return ;
                    }
                });

        final TextField addDiscount = new TextField();
        addDiscount.setPromptText("优惠金额");
        addDiscount.setMaxWidth(Discount_price.getPrefWidth());
        final TextField addOrder = new TextField();
        addOrder.setPromptText("集单目标");
        addOrder.setMaxWidth(Order_count.getPrefWidth());
        final TextField addStart = new TextField();
        addStart.setPromptText("开始时间");
        addStart.setMaxWidth(Starttime.getPrefWidth());
        final TextField addFinisht = new TextField();
        addFinisht.setPromptText("结束时间");
        addFinisht.setMaxWidth(Finishtime.getPrefWidth());

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                data.add(new Merchant().insertcoupon(Float.valueOf(addDiscount.getText()), Integer.valueOf(addOrder.getText()), StringtoDate(addStart.getText())
                        , StringtoDate(addFinisht.getText())));
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
            addDiscount.clear();
            addOrder.clear();
            addStart.clear();
            addFinisht.clear();
        });

        final Button delectButton = new Button("DELECT");
        delectButton.setOnAction((ActionEvent e) -> {
            try {
                new Merchant().delectcoupon(right.getSelectionModel().getSelectedItem());
                data.remove(right.getSelectionModel().getSelectedIndex());
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

//        right.setMaxWidth(500);
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        right.getColumns().clear();
        right.getColumns().addAll(Discount_price, Order_count, Starttime, Finishtime);
        right.setItems(data);
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(addDiscount, addOrder, addStart, addFinisht, addButton, delectButton);
        vbox.getChildren().addAll(right, splitPane);
        vbox.setSpacing(3);
        return vbox;
    }

    public VBox view_loadorder() throws BaseException {
        ObservableList<view_order> data = new Merchant().loadallorder();
        TableView<view_order> tableView = new TableView<>();

        TableColumn<view_order, Integer> Order_id =
                new TableColumn<>("订单号");
        TableColumn<view_order, String> Consumer_name =
                new TableColumn<>("顾客名");
        TableColumn<view_order, String> Merchandise_name =
                new TableColumn<>("菜品名");
        TableColumn<view_order, Integer> Merchandise_count =
                new TableColumn<>("菜品数量");
        TableColumn<view_order, String> Order_state =
                new TableColumn<>("订单状态");
        TableColumn<view_order, String> Rider_name =
                new TableColumn<>("骑手编号");
        TableColumn<view_order, Date> Order_deltime =
                new TableColumn<>("截止时间");

//        name.setMinWidth(100);
        Order_id.setCellValueFactory(new PropertyValueFactory<>("Order_id"));
        Consumer_name.setCellValueFactory(new PropertyValueFactory<>("Consumer_name"));
        Merchandise_name.setCellValueFactory(new PropertyValueFactory<>("Merchandise_name"));
        Merchandise_count.setCellValueFactory(new PropertyValueFactory<>("Merchandise_Count"));
        Order_state.setCellValueFactory(new PropertyValueFactory<>("Order_state"));
        Rider_name.setCellValueFactory(new PropertyValueFactory<>("Rider_name"));
        Order_deltime.setCellValueFactory(new PropertyValueFactory<>("Order_deltime"));

        Button done = new Button("完成烹饪");
        done.setOnAction((ActionEvent e) -> {
            try {
                if (!tableView.getSelectionModel().getSelectedItem().getOrder_state().equals("烹饪中")){
                    throw new BusinessException("已完成烹饪！");
                }
                new Merchant().doneorder(tableView.getSelectionModel().getSelectedItem());
                data.clear();
                data.addAll(new Merchant().loadallorder());
//                System.out.println(data.size());
                tableView.setItems(data);
            } catch (BaseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                return ;
            }
        });

        VBox vBox = new VBox();
        tableView.getColumns().clear();
        tableView.getColumns().addAll(Order_id, Consumer_name, Merchandise_name, Merchandise_count, Order_state, Rider_name, Order_deltime);
        tableView.setItems(data);
        vBox.getChildren().addAll(tableView, done);
        return vBox;
    }

    private Date StringtoDate(String st) throws BaseException {
        if (st==null) return null;
        SimpleDateFormat formatime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date;
        try {
            date = formatime.parse(st);
        } catch (ParseException e) {
            String error = "输入格式有误"+"\n"+"应为 yyyy-MM-dd HH-mm";
            throw new BusinessException(error);
        }
        return date;
    }

    public static class Mer_information {
        private int id;
        private SimpleStringProperty name ;
        private SimpleStringProperty pwd ;
        private SimpleIntegerProperty level ;
        private SimpleFloatProperty avgprice ;
        private SimpleIntegerProperty Total_sales ;

        public Mer_information(){
            this.name = new SimpleStringProperty();
            this.pwd = new SimpleStringProperty();
            this.level = new SimpleIntegerProperty();
            this.avgprice = new SimpleFloatProperty();
            this.Total_sales = new SimpleIntegerProperty();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private Mer_information(String name, String pwd, Integer level, Float avgprice, Integer Total_sales) {
            this.name = new SimpleStringProperty(name);
            this.pwd = new SimpleStringProperty(pwd);
            this.level = new SimpleIntegerProperty(level);
            this.avgprice = new SimpleFloatProperty(avgprice);
            this.Total_sales = new SimpleIntegerProperty(Total_sales);
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

        public String getPwd() {
            return pwd.get();
        }

        public SimpleStringProperty pwdProperty() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd.set(pwd);
        }

        public int getLevel() {
            return level.get();
        }

        public SimpleIntegerProperty levelProperty() {
            return level;
        }

        public void setLevel(int level) {
            this.level.set(level);
        }

        public float getAvgprice() {
            return avgprice.get();
        }

        public SimpleFloatProperty avgpriceProperty() {
            return avgprice;
        }

        public void setAvgprice(float avgprice) {
            this.avgprice.set(avgprice);
        }

        public int getTotal_sales() {
            return Total_sales.get();
        }

        public SimpleIntegerProperty total_salesProperty() {
            return Total_sales;
        }

        public void setTotal_sales(int total_sales) {
            this.Total_sales.set(total_sales);
        }
    }

    public static class Merchandisesort_information {
        private SimpleStringProperty name;
        private SimpleIntegerProperty amount;
        private int id;

        public Merchandisesort_information() {
            name = new SimpleStringProperty();
            amount = new SimpleIntegerProperty();
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

        public int getAmount() {
            return amount.get();
        }

        public SimpleIntegerProperty amountProperty() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount.set(amount);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Merchandise_information {
        private SimpleStringProperty Merchandise_name;
        private SimpleFloatProperty Merchandise_price;
        private SimpleFloatProperty Disconut_price;
        private SimpleStringProperty Sort_name;
        private int id;

        public Merchandise_information() {
            Merchandise_name = new SimpleStringProperty();
            Merchandise_price = new SimpleFloatProperty();
            Disconut_price = new SimpleFloatProperty();
            Sort_name = new SimpleStringProperty();
        }
        public String getSort_name() {
            return Sort_name.get();
        }

        public SimpleStringProperty sort_nameProperty() {
            return Sort_name;
        }

        public void setSort_name(String sort_name) {
            this.Sort_name.set(sort_name);
        }

        public String getMerchandise_name() {
            return Merchandise_name.get();
        }

        public SimpleStringProperty merchandise_nameProperty() {
            return Merchandise_name;
        }

        public void setMerchandise_name(String merchandise_name) {
            this.Merchandise_name.set(merchandise_name);
        }

        public float getMerchandise_price() {
            return Merchandise_price.get();
        }

        public SimpleFloatProperty merchandise_priceProperty() {
            return Merchandise_price;
        }

        public void setMerchandise_price(float merchandise_price) {
            this.Merchandise_price.set(merchandise_price);
        }

        public float getDisconut_price() {
            return Disconut_price.get();
        }

        public SimpleFloatProperty disconut_priceProperty() {
            return Disconut_price;
        }

        public void setDisconut_price(float disconut_price) {
            this.Disconut_price.set(disconut_price);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class reduce_inf {
        private SimpleIntegerProperty Reduction_id;
        private SimpleIntegerProperty Merchant_id;
        private SimpleFloatProperty Red_Amount;
        private SimpleFloatProperty Red_Aim;
        private SimpleBooleanProperty Support_coupon;

        public reduce_inf() {
            Reduction_id = new SimpleIntegerProperty();
            Merchant_id = new SimpleIntegerProperty();
            Red_Amount = new SimpleFloatProperty();
            Red_Aim = new SimpleFloatProperty();
            Support_coupon = new SimpleBooleanProperty();
        }

        public int getMerchant_id() {
            return Merchant_id.get();
        }

        public SimpleIntegerProperty merchant_idProperty() {
            return Merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.Merchant_id.set(merchant_id);
        }

        public int getReduction_id() {
            return Reduction_id.get();
        }

        public SimpleIntegerProperty reduction_idProperty() {
            return Reduction_id;
        }

        public void setReduction_id(int reduction_id) {
            this.Reduction_id.set(reduction_id);
        }

        public float getRed_Amount() {
            return Red_Amount.get();
        }

        public SimpleFloatProperty red_AmountProperty() {
            return Red_Amount;
        }

        public void setRed_Amount(float red_Amount) {
            this.Red_Amount.set(red_Amount);
        }

        public float getRed_Aim() {
            return Red_Aim.get();
        }

        public SimpleFloatProperty red_AimProperty() {
            return Red_Aim;
        }

        public void setRed_Aim(float red_Aim) {
            this.Red_Aim.set(red_Aim);
        }

        public boolean isSupport_coupon() {
            return Support_coupon.get();
        }

        public SimpleBooleanProperty support_couponProperty() {
            return Support_coupon;
        }

        public void setSupport_coupon(boolean support_coupon) {
            this.Support_coupon.set(support_coupon);
        }
    }

    class EditingcouponCell_Integer extends TableCell<Discount_coupon, Integer> {

        private TextField textField;

        public EditingcouponCell_Integer() {
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

            setText(String.valueOf(getItem()) );
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
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
                            commitEdit(Integer.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingcouponCell_Float extends TableCell<Discount_coupon, Float> {

        private TextField textField;

        public EditingcouponCell_Float() {
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

            setText(String.valueOf(getItem()) );
            setGraphic(null);
        }

        @Override
        public void updateItem(Float item, boolean empty) {
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
                            commitEdit(Float.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingcouponCell_Date extends TableCell<Discount_coupon, Date> {

        private TextField textField;
        Merchant a = new Merchant();

        public EditingcouponCell_Date() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                try {
                    createTextField();
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()) );
            setGraphic(null);
        }

        @Override
        public void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        try {
                            textField.setText(a.DatetoString(getItem()));
                        } catch (BaseException e) {
                            e.printStackTrace();
                        }
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    try {
                        setText(a.DatetoString(getItem()));
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                    setGraphic(null);
                }
            }
        }

        private void createTextField() throws BaseException {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
            textField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> arg0,
                     Boolean arg1, Boolean arg2) -> {
                        if (!arg2) {
                            try {
                                commitEdit(StringtoDate(textField.getText()));
                            } catch (BaseException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                                return ;
                            }
                        }
                    });
        }

        private String getString() throws BaseException {
            return getItem() == null ? "" : a.DatetoString(getItem());
        }
    }

    class EditingreduceCell_Integer extends TableCell<reduce_inf, Integer> {

        private TextField textField;

        public EditingreduceCell_Integer() {
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

            setText(String.valueOf(getItem()) );
            setGraphic(null);
        }

        @Override
        public void updateItem(Integer item, boolean empty) {
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
                            commitEdit(Integer.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingreduceCell_Float extends TableCell<reduce_inf, Float> {

        private TextField textField;

        public EditingreduceCell_Float() {
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
        public void updateItem(Float item, boolean empty) {
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
                            commitEdit(Float.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    class EditingreduceCell_Boolean extends TableCell<reduce_inf, Boolean> {

        private TextField textField;

        public EditingreduceCell_Boolean() {
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

    class EditingMerchandisesortCell_String extends TableCell<Merchandisesort_information, String> {

        private TextField textField;

        public EditingMerchandisesortCell_String() {
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

    class EditingMerchandiseCell_String extends TableCell<Merchandise_information, String> {

        private TextField textField;

        public EditingMerchandiseCell_String() {
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

    class EditingMerchandiseCell_Float extends TableCell<Merchandise_information, Float> {

        private TextField textField;

        public EditingMerchandiseCell_Float() {
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
        public void updateItem(Float item, boolean empty) {
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
                            commitEdit(Float.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    static class EditingCell_String extends TableCell<Mer_information, String> {

        private TextField textField;

        public EditingCell_String() {
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

    static class EditingCell_Integer extends TableCell<Mer_information, Integer> {

        private TextField textField;

        public EditingCell_Integer() {
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

    static class EditingCell_Float extends TableCell<Mer_information, Float> {

        private TextField textField;

        public EditingCell_Float() {
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
        public void updateItem(Float item, boolean empty) {
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
                            commitEdit(Float.valueOf(textField.getText()));
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
