package com.ti.examples;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.HashMap;
import java.util.Map;

public class ChartControlPanel extends VBox {
    private RealTimeMultiChart chart;
    private Map<String, Button> buttons = new HashMap<>();
    private Map<String, Boolean> visibilityStates = new HashMap<>();

    // Фиксированные цвета, соответствующие JavaFX `.default-colorX`
    private final String[] defaultColors = {
            "#f3622d", "#fba71b", "#57b757", "#41a9c9",
            "#4258c9", "#9a42c8", "#c84164", "#888888",
            "#c8c8c8", "#a4a4a4", "#636363", "#3b3b3b"
    };

    public ChartControlPanel(RealTimeMultiChart chart) {
        this.chart = chart;
        setSpacing(5);

        for (int i = 0; i < chart.getSeriesMap().size(); i++) {
            String seriesName = String.valueOf(i); // Ключ в Map

            // Берём фиксированный цвет
            Color assignedColor = Color.web(defaultColors[i % defaultColors.length]);

            Button button = createColorButton(assignedColor, seriesName);
            buttons.put(seriesName, button);
            getChildren().add(button);
        }
    }

    private Button createColorButton(Color color, String seriesName) {
        Button button = new Button(seriesName); // Текст кнопки = номер серии
        button.setMinSize(30, 30);
        button.setMaxSize(30, 30);
        button.setFont(new Font(14)); // Делаем номер читаемым
        button.setStyle("-fx-background-color: " + toRgbString(color) + "; -fx-border-color: black;");

        button.setOnAction(event -> {
            boolean currentState = visibilityStates.getOrDefault(seriesName, true);
            chart.setSeriesVisible(seriesName, !currentState);
            visibilityStates.put(seriesName, !currentState);
        });

        return button;
    }

    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
