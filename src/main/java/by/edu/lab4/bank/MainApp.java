package by.edu.lab4.bank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Главное JavaFX-приложение для расчёта процентов по банковским счетам.
 */
public class MainApp extends Application {
    private final TextField numberField = new TextField();
    private final TextField ownerField = new TextField();
    private final TextField balanceField = new TextField();
    private final TextField rateField = new TextField();
    private final TextField minimumField = new TextField();
    private final TextField bonusField = new TextField();
    private final TextField debtField = new TextField();
    private final ComboBox<String> typeBox = new ComboBox<>();
    private final CheckBox capitalizationBox = new CheckBox("Включить капитализацию");
    private final TextArea resultArea = new TextArea();

    @Override
    public void start(Stage stage) {
        Menu fileMenu = new Menu("Файл");
        MenuItem newItem = new MenuItem("Новый расчет");
        MenuItem exitItem = new MenuItem("Выход");
        newItem.setOnAction(e -> clearFields());
        exitItem.setOnAction(e -> stage.close());
        fileMenu.getItems().addAll(newItem, new SeparatorMenuItem(), exitItem);

        Menu helpMenu = new Menu("Справка");
        MenuItem authorItem = new MenuItem("Об авторе");
        authorItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION,
                "Автор: Баранов И.И.\nЛабораторная работа № 4, задание 2.").showAndWait());
        helpMenu.getItems().add(authorItem);

        MenuBar menuBar = new MenuBar(fileMenu, helpMenu);

        typeBox.getItems().addAll("Обычный", "Накопительный", "Кредитный");
        typeBox.setValue("Обычный");
        typeBox.setOnAction(e -> updateFields());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(9);
        grid.setPadding(new Insets(18));

        addRow(grid, 0, "Тип счёта:", typeBox);
        addRow(grid, 1, "Номер счёта:", numberField);
        addRow(grid, 2, "Владелец:", ownerField);
        addRow(grid, 3, "Баланс:", balanceField);
        addRow(grid, 4, "Годовая ставка (%):", rateField);
        addRow(grid, 5, "Минимальный остаток:", minimumField);
        addRow(grid, 6, "Бонусная ставка (%):", bonusField);
        addRow(grid, 7, "Задолженность:", debtField);
        grid.add(capitalizationBox, 1, 8);

        Button calculateButton = new Button("Рассчитать");
        calculateButton.setOnAction(e -> calculate());

        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefRowCount(7);

        Label title = new Label("Банковские счета");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        VBox root = new VBox(menuBar, title, grid, calculateButton, resultArea);
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        updateFields();
        stage.setTitle("Расчёт банковских счетов");
        stage.setScene(new Scene(root, 560, 620));
        stage.show();
    }

    private void addRow(GridPane grid, int row, String label, Control control) {
        grid.add(new Label(label), 0, row);
        control.setPrefWidth(280);
        grid.add(control, 1, row);
    }

    private void updateFields() {
        boolean savings = "Накопительный".equals(typeBox.getValue());
        boolean credit = "Кредитный".equals(typeBox.getValue());
        minimumField.setDisable(!savings);
        bonusField.setDisable(!savings);
        capitalizationBox.setDisable(!savings);
        debtField.setDisable(!credit);
    }

    private void calculate() {
        try {
            String number = numberField.getText().trim();
            String owner = ownerField.getText().trim();
            double balance = parse(balanceField.getText());
            double rate = parse(rateField.getText());

            BankAccount account;
            if ("Накопительный".equals(typeBox.getValue())) {
                account = new SavingsAccount(number, owner, balance, rate,
                        parse(minimumField.getText()), parse(bonusField.getText()),
                        capitalizationBox.isSelected());
            } else if ("Кредитный".equals(typeBox.getValue())) {
                account = new CreditAccount(number, owner, balance, rate, parse(debtField.getText()));
            } else {
                account = new BankAccount(number, owner, balance, rate);
            }
            String info = account.getInfo();
            if (account instanceof SavingsAccount savings && capitalizationBox.isSelected()) {
                double oldBalance = savings.getBalance();
                savings.applyCapitalization();
                info += String.format("\\nБаланс после капитализации: %.2f", savings.getBalance());
            }
            resultArea.setText(info);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Проверьте правильность введённых данных.").showAndWait();
        }
    }

    private double parse(String value) {
        return Double.parseDouble(value.trim().replace(',', '.'));
    }

    private void clearFields() {
        numberField.clear();
        ownerField.clear();
        balanceField.clear();
        rateField.clear();
        minimumField.clear();
        bonusField.clear();
        debtField.clear();
        capitalizationBox.setSelected(false);
        resultArea.clear();
        typeBox.setValue("Обычный");
        updateFields();
    }

    /**
     * Запускает приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}
