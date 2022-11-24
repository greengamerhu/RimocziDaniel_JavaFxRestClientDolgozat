package hu.petrik.rimoczidaniel_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class AutoController extends Controller {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Auto> carTable;
    @FXML
    private TableColumn<Auto, Integer> idCol;
    @FXML
    private TableColumn<Auto, String> companyCol;
    @FXML
    private TableColumn<Auto, String> modelCol;
    @FXML
    private TableColumn<Auto, Integer> performanceCol;
    @FXML
    private TableColumn<Auto, Boolean> hybridCol;

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id")); //getId() függvény eredményét jeleníti meg
        companyCol.setCellValueFactory(new PropertyValueFactory<>("gyarto"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        performanceCol.setCellValueFactory(new PropertyValueFactory<>("teljesitmeny"));
        hybridCol.setCellValueFactory(new PropertyValueFactory<>("hybrid"));
        Platform.runLater(() -> {
            try {
                loadCarsFromServer();
            } catch (IOException e) {
                error("Hiba történt az adatok lekérése során", e.getMessage());

                Platform.exit();
            }
        });
    }

    private void loadCarsFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Auto[] people = converter.fromJson(content, Auto[].class);
        carTable.getItems().clear();
        for (Auto person : people) {
            carTable.getItems().add(person);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("insert-cars-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Add a new car");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                try {
                    loadCarsFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        Auto SelectedAuto =  carTable.getSelectionModel().getSelectedItem();
        if (SelectedAuto == null) {
            warning("Törléshez előbb válasszon ki egy elemet!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-cars-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            UpdateCarsController controller = fxmlLoader.getController();
            controller.setAuto(SelectedAuto);
            stage.setTitle("Update "+ SelectedAuto.getGyarto());
            stage.setScene(scene);
            stage.setOnHidden(event -> {
                try {
                    loadCarsFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Auto SelectedAuto =  carTable.getSelectionModel().getSelectedItem();
        if (SelectedAuto == null) {
            warning("Törléshez előbb válasszon ki egy elemet!");
            return;
        }
        Optional<ButtonType> optionalButtonType =
                alert(Alert.AlertType.CONFIRMATION, "Biztos?",
                        "Biztos, hogy törölni szeretné az alábbi rekordot: "
                                + SelectedAuto.getGyarto() +  " model: " + SelectedAuto.getModel(),
                        "");
        if (optionalButtonType.isPresent() &&
                optionalButtonType.get().equals(ButtonType.OK)
        ) {
            String url = App.BASE_URL + "/" + SelectedAuto.getId();
            try {
                RequestHandler.delete(url);
                loadCarsFromServer();
            } catch (IOException e) {
                error("Nem sikerült kapcsolódni a szerverhez");
            }
        }
    }
}