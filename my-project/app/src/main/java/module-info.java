module org.example.app {
//    requires jakarta.activation;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires jakarta.inject;


    requires org.example.business.logic;
    requires org.example.data.model;
}