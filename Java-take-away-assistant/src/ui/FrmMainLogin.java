package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class FrmMainLogin extends Application implements EventHandler<MouseEvent> {
    private int user_type; //1用户 2商家 3骑手 4管理员

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //创建sence组件
        Scene scene_login, scene;
        Label label = new Label("请选择您的身份");
        Button button_Cons = new Button("用户");
        Button button_Mer = new Button("商家");
        Button button_Rid = new Button("骑手");
        Button button_Admin = new Button("管理员");

        //创建scene_login组件
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        final TextField name = new TextField();
        name.setPromptText("Enter your first name.");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        GridPane.setConstraints(lastName, 0, 1);
        grid.getChildren().add(lastName);
        final TextField comment = new TextField();
        comment.setPromptText("Enter your comment.");
        GridPane.setConstraints(comment, 0, 2);
        grid.getChildren().add(comment);
        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);
        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        // 创建布局控件 添加到布局
        StackPane login = new StackPane();
        VBox main = new VBox();
        login.getChildren().add(grid);
        main.getChildren().add(label);
        main.getChildren().add(button_Cons);
        main.getChildren().add(button_Mer);
        main.getChildren().add(button_Rid);
        main.getChildren().add(button_Admin);

        // 创建场景
        scene_login = new Scene(login, 400, 200);
        scene = new Scene(main, 400, 200);

        // 给按钮添加事件处理的对象
        button_Cons.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
            setUser_type(1);
        });
        button_Admin.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
            setUser_type(2);
        });
        button_Mer.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
            setUser_type(3);
        });
        button_Rid.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
            setUser_type(4);
        });
        submit.setOnMouseClicked(e -> {

        });

        // 将场景添加到窗口
        primaryStage.setScene(scene);
        primaryStage.setTitle("外卖助手");

        // 显示窗口
        primaryStage.show();
    }

    private void setUser_type(int i){
        this.user_type = i;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}

