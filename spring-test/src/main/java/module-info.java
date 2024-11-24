module com.ti {

    // FX
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;

    // Spring Boot
    requires spring.boot; // Модуль Spring Boot
    requires spring.boot.autoconfigure; // Автоконфигурация Spring Boot

    // Spring Core компоненты
    requires spring.web;
    requires spring.core;
    requires spring.beans;
    requires spring.context;

    // Jakarta Inject API
    requires jakarta.inject;

    exports com.ti;
    // For SimpleFxProjects
    exports com.ti.simplefx;
//    exports com.ti.desktop;
    // Открываем пакеты для Spring (нужно для автоконфигурации)
    opens com.ti.controller to spring.core, spring.beans, spring.context, spring.boot, spring.web;
//    opens com.ti.desktop to  spring.core, spring.beans, spring.context, spring.boot, javafx.fxml;
    opens com.ti.simplefx to  spring.core, spring.beans, spring.context, spring.boot, javafx.fxml;
    opens com.ti to  spring.core, spring.beans, spring.context, spring.boot, javafx.fxml;
}