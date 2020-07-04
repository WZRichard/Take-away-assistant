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
    private int user_type; //1�û� 2�̼� 3���� 4����Ա

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //����sence���
        Scene scene_login, scene;
        Label label = new Label("��ѡ���������");
        Button button_Cons = new Button("�û�");
        Button button_Mer = new Button("�̼�");
        Button button_Rid = new Button("����");
        Button button_Admin = new Button("����Ա");

        //����scene_login���
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

        // �������ֿؼ� ��ӵ�����
        StackPane login = new StackPane();
        VBox main = new VBox();
        login.getChildren().add(grid);
        main.getChildren().add(label);
        main.getChildren().add(button_Cons);
        main.getChildren().add(button_Mer);
        main.getChildren().add(button_Rid);
        main.getChildren().add(button_Admin);

        // ��������
        scene_login = new Scene(login, 400, 200);
        scene = new Scene(main, 400, 200);

        // ����ť����¼�����Ķ���
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

        // ��������ӵ�����
        primaryStage.setScene(scene);
        primaryStage.setTitle("��������");

        // ��ʾ����
        primaryStage.show();
    }

    private void setUser_type(int i){
        this.user_type = i;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}

