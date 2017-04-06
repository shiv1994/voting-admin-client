package system;

import AdminVoting.AdminVotingInterface;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.*;
import java.rmi.server.RMIClientSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;


import java.net.URL;

public class Main extends Application implements RMIClientSocketFactory, Serializable{

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Parent root = FXMLLoader.load(getClass().getResource("system/sample.fxml"));
        primaryStage.setTitle("Voting Admin Client");

        WebView mainWebView = new WebView();
        WebEngine mainEngine = mainWebView.getEngine();
        AdminVotingInterface stub;

        JSObject window = (JSObject) mainEngine.executeScript("window");

        try{
            stub = (AdminVotingInterface) Naming.lookup("rmi://localhost:5001/voting");
            window.setMember("stub", stub);

            Campaign campaign = new Campaign();
            window.setMember("campaign", campaign);

            ReportsManager reportsManager = new ReportsManager(stub);
            window.setMember("reportsManager", reportsManager);



            mainEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>(){

                        @Override
                        public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {

                            if(newState == Worker.State.SUCCEEDED){
                                System.out.println("changed");
                                JSObject window = (JSObject)mainEngine.executeScript("window");
                                window.setMember("campaign", campaign);
                                window.setMember("stub", stub);
                                window.setMember("reportsManager", reportsManager);
                            }
                        }
                    });
        }
        catch(Exception e){
            e.printStackTrace();
        }



        URL mainTemplate = getClass().getResource("template/index.html");
        mainEngine.load(mainTemplate.toExternalForm());
        StackPane layout = new StackPane();
        layout.getChildren().addAll(mainWebView);

        primaryStage.setScene(new Scene(layout, 500, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public Socket createSocket(String host, int port) throws  IOException{
        SocketFactory factory = SSLSocketFactory.getDefault();

        Socket socket = factory.createSocket(host, port);

        return socket;
    }

}
