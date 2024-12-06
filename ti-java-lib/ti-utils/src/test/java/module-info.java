module com.ti.tests {
    requires com.ti;
    requires org.junit.jupiter.api; // Для использования аннотаций и API JUnit
    requires org.junit.jupiter.engine;

    opens com.ti.tests;
}