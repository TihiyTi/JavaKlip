package com.ti.controller;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("default")
public final class DefaultFxController{
    @FXML
    private Label title;

//    @Inject
//    public DefaultFxController() {

//    }
}