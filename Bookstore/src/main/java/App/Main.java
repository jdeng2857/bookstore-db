package App;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        databaseInitializer();

        Pane aPane = new Pane();

        UserPane userPane = new UserPane();
        userPane.relocate(0,0);
        userPane.setVisible(true);
        aPane.getChildren().add(userPane);
        System.out.println("User Pane Added");

        OwnerPane ownerPane = new OwnerPane();
        ownerPane.relocate(960,0);
        ownerPane.setVisible(true);
        aPane.getChildren().add(ownerPane);
        System.out.println("Owner Pane Added");

        Scene scene = new Scene(aPane, 1920, 1080);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void databaseInitializer(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");
             Statement statement = connection.createStatement();
        )
        {
            try {
                statement.executeUpdate("delete from users");
                statement.executeUpdate("delete from orders");
                statement.executeUpdate("delete from publisher");
                statement.executeUpdate("delete from store");
                statement.executeUpdate("delete from book");
                statement.executeUpdate("delete from owner");
                statement.executeUpdate("delete from book_order");
                statement.executeUpdate("delete from user_order");
                statement.executeUpdate("insert into users values('1', 'User', 'Address', 'phone')");
                statement.executeUpdate("insert into store values ('1', 'Bookstore', '456 Road', '123-456-7890', 'bookstore@gmail.com')");
                statement.executeUpdate("insert into publisher values ('1', 'Publisher', 'Publisher address', 'bank account', 2.5, 'publisher email')");
                statement.executeUpdate("insert into book values ('1', 'title', 'author', 'genre', 100, 100, '1', '1')");
                statement.executeUpdate("insert into owner values ('1', 'name', 'address', 'phone', '1')");
                statement.executeUpdate("insert into orders values ('1', 12, 'ABK43HKKJHKL', 'SHIPPED', 'Canada Post', 'John Doe', '123 Pine Crescent', 'John Doe', '123 Street', 'CREDIT', '1')");
                statement.executeUpdate("insert into book_order values ('1', '1')");
                statement.executeUpdate("insert into user_order values ('1', '1')");
                System.out.println("Database Initialized");
            }
            catch(SQLException sqle) {
                System.out.println("Could not insert tuple" + sqle);
            }

            ResultSet resultSet = statement.executeQuery(
                    "select user_id, name "
                            + "from users "
                            + "group by user_id"
            );

            while (resultSet.next()) {
                System.out.println("user_id: " + resultSet.getString("user_id") + ", name: " + resultSet.getString(2));
            }

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }
}