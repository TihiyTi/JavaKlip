package com.ti.examples;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseFilterController implements Initializable {
    public TextField filterField;
    public Pane pane;

    @FXML
    public ListView<String> listView;

    private List<String> printVariants;
    private PushCommandInterface pushCommandInterface;


    public void setPrintVariants(List<String> printVariants){
        this.printVariants = printVariants;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.prefHeightProperty().bind(pane.heightProperty());
        listView.prefWidthProperty().bind(pane.widthProperty());
    }

    public void initPane(PushCommandInterface pushCommandInterface){
        this.pushCommandInterface = pushCommandInterface;

        listView.prefHeightProperty().bind(pane.heightProperty());
        listView.prefWidthProperty().bind(pane.widthProperty());

        ObservableList<String> list = FXCollections.observableList(printVariants);
        FilteredList<String> filteredList = new FilteredList<>(list, x -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(s -> s.toLowerCase().contains(newValue.toLowerCase()));
            if(filteredList.size()==1){
                listView.getSelectionModel().select(0);
            }
        });
        double h = list.size() * 24 + 1;
        listView.maxHeight(h);
        listView.setItems(filteredList);
//        filterField.requestFocus();
    }

    public void clickOnList(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()>1){
            pushCommandInterface.pushCommand(getAnswer());
        }
    }

    public void listKey(KeyEvent keyEvent) {
        if(keyEvent.getCode()== KeyCode.ENTER){
            System.out.println("list " + keyEvent.getCode());
            pushCommandInterface.pushCommand(getAnswer());
        }
    }

    public String getAnswer() {
        return listView.getSelectionModel().getSelectedItem();
    }
}
