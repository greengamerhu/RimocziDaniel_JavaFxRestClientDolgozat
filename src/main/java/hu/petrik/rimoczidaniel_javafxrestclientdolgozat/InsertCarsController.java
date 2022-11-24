package hu.petrik.rimoczidaniel_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class InsertCarsController extends Controller{
    @javafx.fxml.FXML
    private TextField companyInput;
    @javafx.fxml.FXML
    private TextField modelInput;
    @javafx.fxml.FXML
    private Spinner<Integer> performanceSpinner;
    @FXML
    private ChoiceBox<Boolean> choiceBoxHybrid;
    @javafx.fxml.FXML
    private Button insertButton;
    @FXML
    private void initialize() {
        choiceBoxHybrid.getItems().add(Boolean.TRUE);
        choiceBoxHybrid.getItems().add(Boolean.FALSE);
        choiceBoxHybrid.setValue(Boolean.FALSE);

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1200, 300);
        performanceSpinner.setValueFactory(valueFactory);


    }


    @javafx.fxml.FXML
    public void InsertClick(ActionEvent actionEvent) {
        String comp = this.companyInput.getText();
        String model = this.modelInput.getText();
        int perf = this.performanceSpinner.getValueFactory().getValue();
        boolean hybrid =  choiceBoxHybrid.getValue();
        if (comp.isEmpty()) {
            warning("Gyártó Megadás kötelező");
            return;
        }
        if (model.isEmpty()) {
            warning("Model megadás kötelező");
            return;
        }
        Auto auto = new Auto(0, comp, model, perf, hybrid);
        Gson gson = new Gson();
        String json = gson.toJson(auto);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if(response.getResponseCode() == 201) {
                companyInput.setText("");
                modelInput.setText("");
                choiceBoxHybrid.setValue(Boolean.FALSE);

                performanceSpinner.getValueFactory().setValue(300);
            }else{
                error("Hiba történt a Hozzáadás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült a szerverhez csatlakozni");
        }

    }
}
