package ui;

import javafx.application.Application;
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


public class FrmMainLogin extends Application implements EventHandler<MouseEvent> {
    private int user_type; //1�û� 2�̼� 3���� 4����Ա
    private Boolean LoginorReg; //true=sign false=login

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //����sence���
        Scene scene_login, scene_register, scene_F, scene_S;
        GridPane grid_Login = CreatLogin(primaryStage);
        GridPane grid_Reg = CreatReg(primaryStage);
        Label label_F = new Label("��ѡ���������");
        Button button_Cons = new Button("�û�");
        Button button_Mer = new Button("�̼�");
        Button button_Rid = new Button("����");
        Button button_Admin = new Button("����Ա");
        Label label_S = new Label("ѡ���½orע��");
        Button button_Login_S = new Button("��¼");
        Button button_Reg_S = new Button("ע��");
        label_F.setFont(new Font(30));
        label_S.setFont(new Font(30));

        // �������ֿؼ� ��ӵ�����
//        button_Cons.setBorder();

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
        select_S.getChildren().add(label_S);
        select_S.getChildren().add(button_Login_S);
        select_S.getChildren().add(button_Reg_S);
        select_S.setAlignment(Pos.CENTER);
//        login.setAlignment(Pos.CENTER);

        // ��������
        scene_login = new Scene(login, 400, 200);
        scene_register = new Scene(register, 400, 200);
        scene_F = new Scene(select_F, 400, 200);
        scene_S = new Scene(select_S, 400, 200);

        // ����ť����¼�����Ķ���
        button_Cons.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
            setUser_type(1);
        });
        button_Admin.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
            setUser_type(2);
        });
        button_Mer.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
            setUser_type(3);
        });
        button_Rid.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_S);
            setUser_type(4);
        });
        button_Login_S.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_login);
            setSignorLogin(true);
        });
        button_Reg_S.setOnMouseClicked(e -> {
            primaryStage.setScene(scene_register);
            setSignorLogin(false);
        });

        // ��������ӵ�����
        primaryStage.setScene(scene_F);
        primaryStage.setTitle("��������");

        // ��ʾ����
        primaryStage.show();
    }

    private void setUser_type(int i){
        this.user_type = i;
    }

    public void setSignorLogin(Boolean signorLogin) {
        this.LoginorReg = signorLogin;
    }

    public GridPane CreatLogin(Stage primaryStage) {
        //����scene_login���
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        final TextField name = new TextField();
        name.setPromptText("Enter your Account.");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your password.");
        GridPane.setConstraints(lastName, 0, 1);
        grid.getChildren().add(lastName);
        final TextField comment = new TextField();
        Button submit = new Button("��¼");
        GridPane.setConstraints(submit, 1, 1);
        grid.getChildren().add(submit);


        submit.setOnMouseClicked(e -> {
            primaryStage.close();
        });
        return grid;
    }

    public GridPane CreatReg(Stage primaryStage) {
        //����scene_login���
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
        final TextField name = new TextField();
        name.setPromptText("Enter your Account.");
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);
        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your password.");
        GridPane.setConstraints(lastName, 0, 1);
        grid.getChildren().add(lastName);
        final TextField comment = new TextField();
        comment.setPromptText("Enter your password again.");
        GridPane.setConstraints(comment, 0, 2);
        grid.getChildren().add(comment);
//        Button submit = new Button("��¼");
//        GridPane.setConstraints(submit, 1, 1);
//        grid.getChildren().add(submit);
        Button register = new Button("ע��");
        GridPane.setConstraints(register, 1, 2);
        grid.getChildren().add(register);

        register.setOnMouseClicked(e -> {
            primaryStage.close();
        });
        return grid;
    }

//    public Button creatButton (String name) {
//        return new Button(name);
//    }

    @Override
    public void handle(MouseEvent event) {

    }
}

