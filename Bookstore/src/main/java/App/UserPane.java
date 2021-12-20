package App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class UserPane extends Pane {

    // Declare GUI elements
    private Label title, id_search_label, title_search_label, results_label, cart_label, total_label,
                order_details, shipping_name, shipping_address, billing_name, billing_address, billing_payment,
                track_order_label, order_status;
    private TextField id_search_field, title_search_field, shipping_name_field, shipping_address_field,
                billing_name_field, billing_address_field, billing_payment_field, track_order_field;
    private Button id_search_button, title_search_button, place_order_button, add_to_cart, remove_from_cart,
                track_order_button;
    private ListView<String> list_view;
    private ListView<String> cart_list_view;
    private ObservableList<String> observable_list;
    private ObservableList<String> cart_observable_list;
    private ArrayList<Double> book_prices;
    private ArrayList<String> book_ISBNs;
    private ArrayList<Double> cart_prices;
    private ArrayList<String> cart_ISBNs;
    private double total = 0;

    public UserPane(){

        // Test DB connection
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");
             Statement statement = connection.createStatement();
        )
         {
            try {
                statement.executeUpdate("delete from users");
                statement.executeUpdate(
                        "insert into users values('1', 'User', 'Address', 'phone')"
                );
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
                System.out.printf("user_id: " + resultSet.getString("user_id") + ", name: " + resultSet.getString(2));
            }

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }

        Pane innerPane = new Pane();

        // Create Labels
        title = new Label("User Section");
        title.relocate(25, 0);
        title.setPrefSize(200,50);
        title.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        id_search_label = new Label("Search by ID");
        id_search_label.relocate(25,75);
        id_search_label.setPrefSize(150,30);
        id_search_label.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        title_search_label = new Label("Search by Title");
        title_search_label.relocate(25,125);
        title_search_label.setPrefSize(150, 30);
        title_search_label.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        results_label = new Label("Search Results");
        results_label.relocate(25,175);
        results_label.setPrefSize(250, 50);
        results_label.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        cart_label = new Label("Cart");
        cart_label.relocate(25,600);
        cart_label.setPrefSize(200, 50);
        cart_label.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        total_label = new Label("Total: " + total);
        total_label.relocate(25, 950);
        total_label.setPrefSize(200,50);
        total_label.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        order_details = new Label("Order Details");
        order_details.relocate(600, 300);
        order_details.setPrefSize(200,50);
        order_details.setStyle("-fx-font: 24 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        shipping_name = new Label("Shipping Name");
        shipping_name.relocate(600,350);
        shipping_name.setPrefSize(150, 30);
        shipping_name.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        shipping_address = new Label("Shipping Address");
        shipping_address.relocate(600,425);
        shipping_address.setPrefSize(150, 30);
        shipping_address.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        billing_name = new Label("Billing Name");
        billing_name.relocate(600,500);
        billing_name.setPrefSize(150, 30);
        billing_name.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        billing_address = new Label("Billing Address");
        billing_address.relocate(600,575);
        billing_address.setPrefSize(150, 30);
        billing_address.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        billing_payment = new Label("Billing Payment");
        billing_payment.relocate(600,650);
        billing_payment.setPrefSize(150, 30);
        billing_payment.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        track_order_label = new Label("Track Order Number (order_id)");
        track_order_label.relocate(600,25);
        track_order_label.setPrefSize(250, 30);
        track_order_label.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        order_status = new Label("Order Status: ");
        order_status.relocate(600,200);
        order_status.setPrefSize(150, 30);
        order_status.setStyle("-fx-font: 14 arial; -fx-base: rgb(0,255,255); " + "-fx-text-fill: rgb(0,0,0);");

        // Create TextFields
        id_search_field = new TextField();
        id_search_field.relocate(300, 75);
        id_search_field.setPrefSize(150,30);

        title_search_field = new TextField();
        title_search_field.relocate(300,125);
        title_search_field.setPrefSize(150,30);

        shipping_name_field = new TextField();
        shipping_name_field.relocate(600,375);
        shipping_name_field.setPrefSize(300,30);

        shipping_address_field = new TextField();
        shipping_address_field.relocate(600,450);
        shipping_address_field.setPrefSize(300,30);

        billing_name_field = new TextField();
        billing_name_field.relocate(600,525);
        billing_name_field.setPrefSize(300,30);

        billing_address_field = new TextField();
        billing_address_field.relocate(600,600);
        billing_address_field.setPrefSize(300,30);

        billing_payment_field = new TextField();
        billing_payment_field.relocate(600,675);
        billing_payment_field.setPrefSize(300,30);

        track_order_field = new TextField();
        track_order_field.relocate(600,75);
        track_order_field.setPrefSize(300,30);

        // Create Buttons
        id_search_button = new Button("Search");
        id_search_button.relocate(475,75);
        id_search_button.setPrefSize(60,30);

        title_search_button = new Button("Search");
        title_search_button.relocate(475,125);
        title_search_button.setPrefSize(60,30);

        place_order_button = new Button("Place Order");
        place_order_button.relocate(475, 900);
        place_order_button.setPrefSize(100,30);

        add_to_cart = new Button("Add To Cart");
        add_to_cart.relocate(475, 520);
        add_to_cart.setPrefSize(100,30);

        remove_from_cart = new Button("Remove From Cart");
        remove_from_cart.relocate(475,800);
        remove_from_cart.setPrefSize(120,30);

        track_order_button = new Button("Track Order");
        track_order_button.relocate(600,150);
        track_order_button.setPrefSize(100,30);

        // Create ListView that displays search results
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");
             Statement statement = connection.createStatement();
        ) {

            // Query all books and add it to a list (Searching books)
            ResultSet resultSet = statement.executeQuery(
                    "select * "
                    + "from book "
                    + "group by ISBN"
            );

            observable_list = FXCollections.observableArrayList();
            book_prices = new ArrayList<Double>();
            book_ISBNs = new ArrayList<String>();
            while(resultSet.next()){
                observable_list.add("ISBN: "+ resultSet.getString("ISBN") +
                        ", Title: " + resultSet.getString("title") +
                        ", Author: " + resultSet.getString("author") +
                        ", Price: " + resultSet.getDouble("price"));
                book_prices.add(resultSet.getDouble("price"));
                book_ISBNs.add(resultSet.getString("ISBN"));
            }

            list_view = new ListView<String>();
            list_view.setItems(observable_list);
            list_view.relocate(25,250);
            list_view.setPrefSize(400,300);


            // Initialize empty cart
            cart_observable_list = FXCollections.observableArrayList();
            cart_prices = new ArrayList<Double>();
            cart_ISBNs = new ArrayList<String>();
            cart_list_view = new ListView<String>();
            cart_list_view.setItems(cart_observable_list);
            cart_list_view.relocate(25,650);
            cart_list_view.setPrefSize(400,250);

        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }


        // Add List View Listeners


        // Add Button Listeners
        add_to_cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Add to cart button clicked: ");

                int selected_index = list_view.getSelectionModel().getSelectedIndex();
                if(selected_index >= 0) {
                    String selected_item = list_view.getSelectionModel().getSelectedItem();

                    System.out.println("list_view index: " + selected_index);
                    System.out.println("list_view item: " + selected_item);

                    cart_observable_list.add(selected_item);
                    cart_prices.add(book_prices.get(selected_index));
                    cart_ISBNs.add(book_ISBNs.get(selected_index));

                    updateTotal();
                }else{
                    System.out.println("Invalid selection");
                }
            }
        });

        remove_from_cart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Remove from cart button clicked: ");

                int selected_index = cart_list_view.getSelectionModel().getSelectedIndex();
                if(selected_index >= 0) {
                    String selected_item = cart_list_view.getSelectionModel().getSelectedItem();

                    System.out.println("list_view index: " + selected_index);
                    System.out.println("list_view item: " + selected_item);

                    cart_observable_list.remove(selected_item);
                    cart_prices.remove(book_prices.get(selected_index));
                    cart_ISBNs.remove(book_ISBNs.get(selected_index));

                    updateTotal();
                }else{
                    System.out.println("Invalid selection");
                }
            }
        });

        place_order_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("The following ISBNs are ordered: ");
                for(int i = 0; i < cart_ISBNs.size(); i++){
                    System.out.println(cart_ISBNs.get(i));
                }
                if(placeOrder(cart_ISBNs)) {
                    cart_ISBNs.clear();
                }
            }
        });

        track_order_button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                String target = track_order_field.getText();
                trackOrder(target);
            }
        });

        // Add all elements to pane
        innerPane.getChildren().addAll(title, id_search_label, title_search_label, results_label, cart_label,
        order_details, shipping_name, shipping_address, billing_name, billing_address, billing_payment,
        id_search_field, title_search_field, shipping_name_field, shipping_address_field, billing_name_field,
                billing_address_field, billing_payment_field, track_order_field, track_order_label,
                id_search_button, title_search_button, place_order_button, list_view, track_order_button,
        cart_list_view, add_to_cart, remove_from_cart, total_label, order_status);
        getChildren().addAll(innerPane);
    }

    public void updateTotal(){
        total = 0;
        for(int i = 0; i < cart_prices.size(); i++){
            total += cart_prices.get(i);
        }
        total_label.setText("Total: "+total);
    }

    public boolean placeOrder(ArrayList<String> isbn_arr){
        if(isbn_arr.size() < 1){
            System.out.println("Can't place order because there are no items");
            return false;
        }

        String ship_name = shipping_name_field.getText();
        String ship_address = shipping_address_field.getText();
        String bill_name = billing_name_field.getText();
        String bill_address = billing_address_field.getText();
        String bill_payment = billing_payment_field.getText();
        if(ship_name == ""){
            System.out.println("Can't place order because shipping name is not specified");
            return false;
        }else if(ship_address == ""){
            System.out.println("Can't place order because shipping address is not specified");
            return false;
        }else if(bill_name == ""){
            System.out.println("Can't place order because billing name is not specified");
            return false;
        }else if(bill_address == ""){
            System.out.println("Can't place order becausae billing address is not specified");
            return false;
        }else if(bill_payment == ""){
            System.out.println("Can't place order because billing payment is not specified");
            return false;
        }

        // Insert into orders
        String sql = "INSERT INTO orders VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String order_id = "";
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");){
            ArrayList<String> randArr = new ArrayList<String>();
            randArr.add(getRandomString());
            randArr.add(getRandomString());
            randArr.add(getRandomString());
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, randArr.get(0));
                preparedStatement.setDouble(2, total);
                preparedStatement.setString(3, randArr.get(1));
                preparedStatement.setString(4, "SHIPPED");
                preparedStatement.setString(5, randArr.get(2));
                preparedStatement.setString(6, ship_name);
                preparedStatement.setString(7, ship_address);
                preparedStatement.setString(8, bill_name);
                preparedStatement.setString(9, bill_address);
                preparedStatement.setString(10, bill_payment);
                preparedStatement.setString(11, "1");
                preparedStatement.executeUpdate();
                order_id = randArr.get(0);
                System.out.println("Order_id: " + randArr.get(0) + " successfully placed!");
            }catch (Exception e){
                System.out.println("Exception: " + e);
                return false;
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            return false;
        }

        // Insert into book_order
        String sql2;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");){
            for(int i = 0; i < isbn_arr.size(); i++) {
                sql2 = "INSERT INTO book_order VALUES (?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                    preparedStatement.setString(1, isbn_arr.get(i));
                    preparedStatement.setString(2, order_id);
                    preparedStatement.executeUpdate();
                    System.out.println("book_order successfully inserted");
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    return false;
                }
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            return false;
        }

        // Insert into user_order
        String sql3;
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");){
            for(int i = 0; i < isbn_arr.size(); i++) {
                sql3 = "INSERT INTO user_order VALUES (?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql3)) {
                    preparedStatement.setString(1, "1");
                    preparedStatement.setString(2, order_id);
                    preparedStatement.executeUpdate();
                    System.out.println("user_order successfully inserted");
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    return false;
                }
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            return false;
        }

        // Delete books in order
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");){
            for(int i = 0; i < isbn_arr.size(); i++) {
                sql3 = "DELETE FROM book WHERE ISBN=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql3)) {
                    preparedStatement.setString(1, isbn_arr.get(i));
                    preparedStatement.executeUpdate();
                    System.out.println("book successfully deleted");
                } catch (Exception e) {
                    System.out.println("Exception: " + e);
                    return false;
                }
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
            return false;
        }

        updateBooks();
        emptyCart();
        System.out.println("placeOrder() success!");
        return true;
    }

    public void trackOrder(String target){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");){
            String sql = "SELECT status FROM orders WHERE order_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, target);
            ResultSet resultSet = preparedStatement.executeQuery();
            int count = 0;
            String status = "";
            while(resultSet.next()){
                status=resultSet.getString(1);
                count++;
            }
            if(count>0){
                order_status.setText("Order Status: " + status);
                System.out.println("Status updated");
            }else{
                System.out.println("Status not updated, order_id not found");
            }
        }catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
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

    public void updateBooks(){
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bookstore", "postgres", "password");
             Statement statement = connection.createStatement();
        ) {

            // Query all books and add it to a list (Searching books)
            ResultSet resultSet = statement.executeQuery(
                    "select * "
                            + "from book "
                            + "group by ISBN"
            );
            observable_list = FXCollections.observableArrayList();
            observable_list.clear();
            book_prices.clear();
            book_ISBNs.clear();
            while(resultSet.next()){
                observable_list.add("ISBN: "+ resultSet.getString("ISBN") +
                        ", Title: " + resultSet.getString("title") +
                        ", Author: " + resultSet.getString("author") +
                        ", Price: " + resultSet.getDouble("price"));
                book_prices.add(resultSet.getDouble("price"));
                book_ISBNs.add(resultSet.getString("ISBN"));
            }
            list_view.setItems(observable_list);
            System.out.println("Books Updated");
        } catch (Exception sqle) {
            System.out.println("Exception: " + sqle);
        }
    }

    public void emptyCart(){
        cart_observable_list.clear();
        System.out.println("Cart Cleared");
    }
}
