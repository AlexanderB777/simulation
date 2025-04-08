module com.alex678.Simulation {
    requires javafx.controls;

    opens com.alex678 to javafx.graphics;
    exports com.alex678;
}