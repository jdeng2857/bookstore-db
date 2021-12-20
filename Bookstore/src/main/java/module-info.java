module com.example.bookstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens App to javafx.fxml;
    exports App;
}