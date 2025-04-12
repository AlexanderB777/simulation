module com.alex678.Simulation {
    requires javafx.controls;
    requires static lombok;
    requires org.slf4j;

    opens com.alex678 to javafx.graphics;
    exports com.alex678;
}