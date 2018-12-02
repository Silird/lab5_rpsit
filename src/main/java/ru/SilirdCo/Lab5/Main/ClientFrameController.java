package ru.SilirdCo.Lab5.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.SilirdCo.Lab5.Util.ExceptionHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ClientFrameController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ClientFrameController.class);

    private final static int PORT = 6666;
    private final static String ADDRESS = "127.0.0.1";

    @FXML
    private TextField textA1;
    private DoubleTextField doubleA1;
    @FXML
    private Button but1;

    @FXML
    private TextField textR2;
    private DoubleTextField doubleR2;
    @FXML
    private Button but2;

    @FXML
    private TextField textA3;
    @FXML
    private TextField textB3;
    @FXML
    private TextField textC3;
    @FXML
    private TextField textD3;
    private DoubleTextField doubleA3;
    private DoubleTextField doubleB3;
    private DoubleTextField doubleC3;
    private DoubleTextField doubleD3;
    @FXML
    private Button but3;

    @FXML
    private TextField textR4;
    @FXML
    private TextField textH4;
    private DoubleTextField doubleR4;
    private DoubleTextField doubleH4;
    @FXML
    private Button but4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNumberFields();

        initListeners();
    }

    private void initNumberFields() {
        doubleA1 = new DoubleTextField(textA1);

        doubleR2 = new DoubleTextField(textR2);

        doubleA3 = new DoubleTextField(textA3);
        doubleB3 = new DoubleTextField(textB3);
        doubleC3 = new DoubleTextField(textC3);
        doubleD3 = new DoubleTextField(textD3);

        doubleR4 = new DoubleTextField(textR4);
        doubleH4 = new DoubleTextField(textH4);
    }

    private void initListeners() {
        but1.setOnAction(event -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ADDRESS);
                Socket socket = new Socket(ipAddress, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeInt(1);
                double a1;

                double result;

                a1 = doubleA1.getDouble();

                out.writeDouble(a1);
                out.flush();

                result = in.readDouble();
                showResult(String.valueOf(result));

                in.close();
                out.close();
                socket.close();
            }
            catch (IOException ex) {
                ExceptionHandler.handle(logger, ex);
            }
        });

        but2.setOnAction(event -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ADDRESS);
                Socket socket = new Socket(ipAddress, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeInt(2);
                double r2;

                double result;

                r2 = doubleR2.getDouble();

                out.writeDouble(r2);
                out.flush();

                result = in.readDouble();
                showResult(String.valueOf(result));

                in.close();
                out.close();
                socket.close();
            }
            catch (IOException ex) {
                ExceptionHandler.handle(logger, ex);
            }
        });

        but3.setOnAction(event -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ADDRESS);
                Socket socket = new Socket(ipAddress, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeInt(3);
                double a3;
                double b3;
                double c3;
                double d3;

                double result;

                a3 = doubleA3.getDouble();
                b3 = doubleB3.getDouble();
                c3 = doubleC3.getDouble();
                d3 = doubleD3.getDouble();

                out.writeDouble(a3);
                out.writeDouble(b3);
                out.writeDouble(c3);
                out.writeDouble(d3);
                out.flush();

                result = in.readDouble();
                showResult(String.valueOf(result));

                in.close();
                out.close();
                socket.close();
            }
            catch (IOException ex) {
                ExceptionHandler.handle(logger, ex);
            }
        });

        but4.setOnAction(event -> {
            try {
                InetAddress ipAddress = InetAddress.getByName(ADDRESS);
                Socket socket = new Socket(ipAddress, PORT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeInt(4);
                double r4;
                double h4;

                double result;

                r4 = doubleR4.getDouble();
                h4 = doubleH4.getDouble();

                out.writeDouble(r4);
                out.writeDouble(h4);
                out.flush();

                result = in.readDouble();
                showResult(String.valueOf(result));

                in.close();
                out.close();
                socket.close();
            }
            catch (IOException ex) {
                ExceptionHandler.handle(logger, ex);
            }
        });
    }

    private void showResult(String result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Результат: " + result);
        alert.showAndWait();
    }
}
