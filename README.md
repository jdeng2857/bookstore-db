# bookstore-db
For COMP3005 Project

This is a Java JDBC DBMS using Postgresql.

Prerequisites:
Install IntelliJ IDEA: (Recommended Editor):
https://www.jetbrains.com/help/idea/installation-guide.html
Install JDK 17 and JavaFX and set it up with Intellij:
https://www.jetbrains.com/help/idea/javafx.html

Download zip of bookstore folder, unzip it, and open in IntelliJ.

Files:
Under Folder /src/main/java/App
Main: Main App, run this
OwnerPane: Owner section of application (UNFINISHED)
UserPane: User section of application

Under Folder /resources/SQL
database-initializer.sql: creates database tables
insertFile.sql: insert sample tuples into database tables


How to run application:
Go to IDE (preferably IntelliJ IDEA) and select Main as the run file and click run.
A desktop window should appear. If something fails, make sure to configure JDK and
JavaFX in the prerequisites.

Currently, only listing of all books, add to cart, place order, and track order is
functional for users. The user cannot search by ID nor title as it is not implemented.
