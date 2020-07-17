package ui;

import control.LoginAndRegister;
import control.Merchant;
import control.Rider;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.BaseException;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FrmMainLogin extends Application { //implements EventHandler<MouseEvent>
    public static int user_type; //1用户 2商家 3骑手 4管理员
//    public static Boolean LoginorReg; //true=sign false=login
    public static int Currentuserid;
    public static String Currentusername;
    public static Scene scene_F;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Merchant().check();

        //创建sence组件
        Scene scene_login, scene_register, scene_S;
        Label label_F = new Label("请选择您的身份");
        Button button_Cons = new Button("用户");
        Button button_Mer = new Button("商家");
        Button button_Rid = new Button("骑手");
        Button button_Admin = new Button("管理员");
        Label label_S = new Label("选择登陆or注册");
        Button button_Login_S = new Button("登录");
        Button button_Reg_S = new Button("注册");
        Button button_back_F = new Button("返回");
        Button button_back_S1 = new Button("返回");
        Button button_back_S2 = new Button("返回");
        label_F.setFont(new Font(30));
        label_S.setFont(new Font(30));

        GridPane grid_Login = CreatLogin(primaryStage, button_back_S1);
        GridPane grid_Reg = CreatReg(button_back_S2);

        // 创建布局控件 添加到布局
        StackPane login = new StackPane();
        StackPane register = new StackPane();
        VBox select_F = new VBox();
        VBox select_S = new VBox();
        login.getChildren().add(grid_Login);
        register.getChildren().add(grid_Reg);
        select_F.getChildren().add(label_F);
        select_F.getChildren().add(button_Cons);
        select_F.getChildren().add(button_Mer);
        select_F.getChildren().add(button_Rid);
        select_F.getChildren().add(button_Admin);
        select_F.setAlignment(Pos.CENTER);
        select_F.getStylesheets().add("/NodeStyle.css");
        select_F.getStyleClass().addAll("menu");
        select_S.getChildren().add(label_S);
        select_S.getChildren().add(button_Login_S);
        select_S.getChildren().add(button_Reg_S);
        select_S.getChildren().add(button_back_F);
        select_S.setAlignment(Pos.CENTER);
        select_S.setAlignment(Pos.CENTER);
        select_S.getStylesheets().add("/NodeStyle.css");
        select_S.getStyleClass().addAll("menu");
//        login.setAlignment(Pos.CENTER);

        // 创建场景
        scene_login = new Scene(login, 400, 200);
        scene_register = new Scene(register, 400, 200);
        scene_F = new Scene(select_F, 400, 200);
        scene_S = new Scene(select_S, 400, 200);

        // 给按钮添加事件处理的对象
        button_Cons.setOnMouseClicked(e -> {
            setUser_type(1);
            primaryStage.setScene(scene_S);
        });
        button_Mer.setOnMouseClicked(e -> {
            setUser_type(2);
            primaryStage.setScene(scene_S);
        });
        button_Rid.setOnMouseClicked(e -> {
            setUser_type(3);
            primaryStage.setScene(scene_S);
        });
        button_Admin.setOnMouseClicked(e -> {
            setUser_type(4);
            primaryStage.setScene(scene_S);
        });
        button_Login_S.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
        });
        button_Reg_S.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_register);
        });
        button_back_F.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_F);
        });
        button_back_S1.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
        });
        button_back_S2.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
        });

        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        EventHandler<ActionEvent> eventHandler = e -> {
            primaryStage.setTitle("外卖助手("+df.format(new Date())+")");
        };
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // 将场景添加到窗口 显示窗口
        primaryStage.setScene(scene_F);
        primaryStage.setTitle("外卖助手");
        primaryStage.show();
    }

    private void setUser_type(int i){
        this.user_type = i;
    }

    public GridPane CreatLogin(Stage primaryStage, Button button_back_S1) {
        //创建scene_login组件
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);

        final TextField Account = new TextField();
        Account.setPromptText("Enter your Account.");
        GridPane.setConstraints(Account, 0, 0);
        grid.getChildren().add(Account);

        final TextField password = new TextField();
        password.setPromptText("Enter your password.");
        GridPane.setConstraints(password, 0, 1);
        grid.getChildren().add(password);

        Button submit = new Button("登录");
        GridPane.setConstraints(submit, 1, 1);
        grid.getChildren().add(submit);

        GridPane.setConstraints(button_back_S1, 1, 3);
        grid.getChildren().add(button_back_S1);

        submit.setOnMouseClicked(e -> {
            if (
                    (!password.getText().isEmpty() && !Account.getText().isEmpty())
            ) {
                try {
                    new LoginAndRegister().Login(Account.getText(), password.getText());
                    switch (this.user_type) {
                        case 1: {
                            new FrmConsumer().Con_Init(primaryStage);
                            break;
                        }
                        case 2: {
                            new FrmMerchant().Mer_Init(primaryStage);
                            break;
                        }
                        case 3: {
                            new FrmRider().Rider_Init(primaryStage);
                            break;
                        }
                        case 4: {
                            new FrmAdministrator().Admin_Init(primaryStage);
                            break;
                        }
                    }
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            }
            Account.clear();
            password.clear();
        });
        grid.getStylesheets().add("/NodeStyle.css");
        grid.getStyleClass().addAll("menu");
        return grid;
    }

    public GridPane CreatReg(Button button_back_S2) {
        //创建scene_login组件
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        final TextField Account = new TextField();
        Account.setPromptText("Enter your Account.");
        GridPane.setConstraints(Account, 0, 0);
        grid.getChildren().add(Account);
        final TextField password = new TextField();
        password.setPromptText("Enter your password.");
        GridPane.setConstraints(password, 0, 1);
        grid.getChildren().add(password);
        final TextField password2 = new TextField();
        password2.setPromptText("Enter your password again.");
        GridPane.setConstraints(password2, 0, 2);
        grid.getChildren().add(password2);
        Button register = new Button("注册");
        GridPane.setConstraints(register, 1, 2);
        grid.getChildren().add(register);
        GridPane.setConstraints(button_back_S2, 1, 3);
        grid.getChildren().add(button_back_S2);

        register.setOnMouseClicked(e -> {
            if (
                    (!Account.getText().isEmpty() && !password.getText().isEmpty() && !password2.getText().isEmpty())
            ) {
                try {
                    new LoginAndRegister().Register(Account.getText(), password.getText(), password2.getText());
                }
                catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
                    return ;
                }
            }
            Account.clear();
            password.clear();
            password2.clear();
        });
        grid.getStylesheets().add("/NodeStyle.css");
        grid.getStyleClass().addAll("menu");
        return grid;
    }
}

