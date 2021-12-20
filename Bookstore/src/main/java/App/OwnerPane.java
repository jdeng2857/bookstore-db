package App;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.Random;

public class OwnerPane extends Pane {

    private Label title;

    public OwnerPane(){
        Pane innerPane = new Pane();

        title = new Label("Owner Section");
        title.relocate(25, 0);
        title.setPrefSize(200,50);
        title.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        innerPane.getChildren().addAll(title);
        getChildren().addAll(innerPane);
    }


    // Method taken from https://stackoverflow.com/questions/20536566/creating-a-random-string-with-a-z-and-0-9-in-java
    // Credit to owner: Suresh Atta
    protected String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
