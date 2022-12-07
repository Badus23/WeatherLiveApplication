package com.mamchura.weatherlive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.LDAPCertStoreParameters;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button checkButton;

    @FXML
    private TextField citySearch;

    @FXML
    private Text maxTemp;

    @FXML
    private Text minTemp;

    @FXML
    private Text pressure;

    @FXML
    private Text errorMsg;

    @FXML
    private Text temp;

    private String API = "3e6030fe4875ebf68f53ae22c86a1232";

    @FXML
    void initialize() {
        checkButton.setOnAction(event -> {
            JsonNode obj = null;
            String secondResponse = null;
            try {
                String city = citySearch.getText().trim();
                RestTemplate template = new RestTemplate();
                String firstResponse = template.getForObject("http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&appid=" + API, String.class);
                ObjectMapper mapper = new ObjectMapper();
                if (!city.equals("")) {
                    obj = mapper.readTree(firstResponse);
                    JsonNode responseText = obj.iterator().next();
                    secondResponse = template.getForObject("https://api.openweathermap.org/data/2.5/weather?lat=" + responseText.get("lat").asDouble() + "&lon=" + responseText.get("lon").asDouble() + "&units=metric" + "&appid=" + API, String.class);
                    obj = mapper.readTree(secondResponse);
                }
                if (secondResponse != null) {
                    errorMsg.setText("");
                    temp.setText("Temperature: " + obj.get("main").get("temp"));
                    minTemp.setText("Min: " + obj.get("main").get("temp_min"));
                    maxTemp.setText("Max: " + obj.get("main").get("temp_max"));
                    pressure.setText("Pressure: " + obj.get("main").get("pressure"));
                }
            } catch (Exception e) {
                errorMsg.setText("Write the correct name of the city");
                temp.setText("");
                minTemp.setText("");
                maxTemp.setText("");
                pressure.setText("");
            }
        });
    }

    private static String getContent(String address) {
        StringBuffer buffer = new StringBuffer();

        URL url = null;
        try {
            url = new URL(address);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = reader.readLine()) != null) {
                buffer.append(str + "\n");
            }
        } catch (IOException e) {
            System.out.println("Wrong city");
        }
        return buffer.toString();
    }

}
