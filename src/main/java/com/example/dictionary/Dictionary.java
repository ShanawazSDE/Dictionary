package com.example.dictionary;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Dictionary extends Application {
    Label meaningLabel;
    DictionaryLogic dictionaryLogic;
    TextField wordField;
    DatabaseHelper databaseHelper;
    ListView<String> listView;
    VBox vBoxOfWordFieldListView;
    private VBox createContent() throws SQLException {
        VBox vBox = new VBox(10);
        vBox.setPrefSize(500,250);
        vBox.setPadding(new Insets(20,10,10,10));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-background-color:#DCCCA3;");
       // vBox.setStyle("-fx-border-color:#1A1B25;");
        //vBox.setBorder(new Border(new BorderStroke(Color.web("#48297A"),BorderStrokeStyle.SOLID,new CornerRadii(5),BorderStroke.MEDIUM)));
         wordField = new TextField();
         wordField.setFont(new Font("Times",15));
        wordField.setStyle("-fx-background-color: -fx-control-inner-background;");
        wordField.setBorder(new Border(new BorderStroke(Color.web("#1A1B25"),BorderStrokeStyle.SOLID,new CornerRadii(4),new BorderWidths(1.5))));
        wordField.setPromptText("search here!!!");
        wordField.prefWidthProperty().bind(vBox.widthProperty());

        wordField.setMaxWidth(400);
                dictionaryLogic = new DictionaryLogic();
        Button searchButton = new Button();
        searchButton.setText("search");
        searchButton.setFont(Font.font("search",FontWeight.BLACK,15));

        searchButton.setStyle("-fx-background-color:#767B91;");
        searchButton.setTextFill(Color.WHITE);
        searchButton.setBorder(new Border(new BorderStroke(Color.web("#1A1B25"),BorderStrokeStyle.SOLID,new CornerRadii(4),new BorderWidths(1.5))));
        searchButton.prefWidthProperty().bind(vBox.widthProperty());
        searchButton.setMaxWidth(150);
        searchButton.setMinWidth(100);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String word = wordField.getText();
                if(word.isBlank()){
                    meaningLabel.setTextFill(Color.RED);
                    meaningLabel.setText("please type a word");
                }
                else{
                    meaningLabel.setTextFill(Color.BLACK);
                    meaningLabel.setText(dictionaryLogic.searchMeaning(word));
                }
            }
        });
        HBox hBox = new HBox(10);
       hBox.setAlignment(Pos.TOP_CENTER);

        //hBox.setPadding(new Insets(0,100,0,100));
       // hBox.getChildren().addAll(wordField,searchButton);

        databaseHelper = new DatabaseHelper();
       listView = new ListView<>(databaseHelper.getWords());
       listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
               wordField.setText(t1);
           }
       });
        //listView.prefWidthProperty().bind(vBox.widthProperty());
        //listView.prefWidthProperty().bind(wordField.widthProperty());
        //listView.setMaxWidth(400);
         vBoxOfWordFieldListView = new VBox();
         vBoxOfWordFieldListView.getChildren().addAll(wordField,listView);
        hBox.getChildren().addAll(vBoxOfWordFieldListView,searchButton);


         meaningLabel = new Label();
         meaningLabel.setWrapText(true);
         meaningLabel.setBorder(new Border(new BorderStroke(Color.web("#1A1B25"),BorderStrokeStyle.SOLID,new CornerRadii(4),new BorderWidths(1.5))));
         meaningLabel.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(4),new Insets(0))));
         //meaningLabel.prefHeightProperty().bind(vBox.heightProperty());
        // meaningLabel.setMaxHeight(450);
        meaningLabel.setPadding(new Insets(10));
         meaningLabel.prefWidthProperty().bind(vBox.widthProperty());
       meaningLabel.setMaxWidth(900);
        meaningLabel.setText("Meaning will appear here");
        meaningLabel.setFont(new Font("Times",15));
        meaningLabel.setContentDisplay(ContentDisplay.CENTER);
        vBox.getChildren().addAll(hBox,meaningLabel);
        return vBox;
    }
    @Override
    public void start(Stage stage) throws IOException, SQLException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Dictionary.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Dictionary");
        stage.setScene(scene);
        stage.show();
        //listView.setMaxWidth(wordField.getBoundsInParent().getWidth());

    }

    public static void main(String[] args) {
        launch();
    }
}