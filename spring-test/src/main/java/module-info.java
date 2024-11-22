module com.ti {

    // Требуется Spring Boot и его зависимости
    requires spring.boot; // Модуль Spring Boot
    requires spring.boot.autoconfigure; // Автоконфигурация Spring Boot

    // Требуется Spring Core компоненты
    requires spring.web;
    requires spring.core;
    requires spring.beans;
    requires spring.context;

    // Требуется Jakarta Inject API
    requires jakarta.inject;

    exports com.ti;

    // Требуем модули, используемые в проекте
//    requires spring.core;           // Для ядра Spring
//    requires spring.beans;          // Для работы с бинами
//    requires spring.context;        // Для ApplicationContext
//    requires spring.boot;           // Для Spring Boot
//    requires spring.boot.autoconfigure; // Для автоконфигурации
//    requires jakarta.inject;        // Для Dependency Injection API

    // Открываем пакеты для Spring (нужно для автоконфигурации)
    opens com.ti.controller to spring.core, spring.beans, spring.context, spring.boot, spring.web;
    opens com.ti to spring.core, spring.beans, spring.context, spring.boot;
}