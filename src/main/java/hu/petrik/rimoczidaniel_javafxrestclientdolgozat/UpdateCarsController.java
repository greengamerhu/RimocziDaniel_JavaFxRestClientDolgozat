package hu.petrik.rimoczidaniel_javafxrestclientdolgozat;


import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateCarsController extends Controller {

    @FXML
    private TextField companyInput;
    @FXML
    private TextField modelInput;
    @FXML
    private Spinner<Integer> performanceSpinner;
    @FXML
    private ChoiceBox<Boolean> choiceBoxHybrid;
    @FXML
    private Button updateButton;
    private Auto auto;

    public void setAuto(Auto auto) {
        this.auto = auto;
        this.companyInput.setText(auto.getGyarto());
        this.modelInput.setText(auto.getModel());
        this.performanceSpinner.getValueFactory().setValue(auto.getTeljesitmeny());
        this.choiceBoxHybrid.setValue(auto.isHybrid());
    }

    @FXML
    private void initialize() {
        choiceBoxHybrid.getItems().add(Boolean.TRUE);
        choiceBoxHybrid.getItems().add(Boolean.FALSE);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1200, 300);
        performanceSpinner.setValueFactory(valueFactory);

    }

    @FXML
    private void updateClick(ActionEvent actionEvent) {
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
        this.auto.setGyarto(comp);
        this.auto.setModel(model);
        this.auto.setTeljesitmeny(perf);
        this.auto.setHybrid(hybrid);
        Gson gson = new Gson();
        String json = gson.toJson(this.auto);
        try {
            String url = App.BASE_URL + "/" + this.auto.getId();
            Response response = RequestHandler.put(url, json);
            if(response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            }else{
                error("Hiba történt a módosítás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült a szerverhez csatlakozni");
        }


    }
}


