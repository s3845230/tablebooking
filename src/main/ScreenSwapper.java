package main;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;


class ScreenSwapper {
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;
    private static ScreenSwapper uniqueInstance;

    private ScreenSwapper() throws Exception{
        addScreen("booking", FXMLLoader.load(getClass().getResource("ui/booking.fxml")));
        addScreen("login", FXMLLoader.load(getClass().getResource("ui/login.fxml")));
    };
    public static ScreenSwapper getInstance() throws Exception {
        if (uniqueInstance == null) {
            uniqueInstance = new ScreenSwapper();
        }
        return uniqueInstance;
    }

    public void setScene(Scene main){
        this.main = main;
    }

    protected void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name){
        screenMap.remove(name);
    }

    protected void activate(String name){
        main.setRoot( screenMap.get(name) );
    }
}