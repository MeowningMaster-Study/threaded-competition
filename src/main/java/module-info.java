module com.meowningmaster.threadedcompetition {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.meowningmaster.threadedcompetition to javafx.fxml;
    exports com.meowningmaster.threadedcompetition;
}