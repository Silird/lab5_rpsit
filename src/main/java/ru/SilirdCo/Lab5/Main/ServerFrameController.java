package ru.SilirdCo.Lab5.Main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class ServerFrameController implements Initializable {
    private static final Logger logger = LoggerFactory.getLogger(ServerFrameController.class);

    @FXML
    private VBox vBox;
    @FXML
    private ScrollPane scroll;
    private TextFlow textFlow;
    private boolean needScroll = true;

    private final static String SERVER_WAIT = "Server is waiting for connection...";
    private final static String CLIENT_CONNECT = "Client connected to the server";
    private final static String CLIENT_SEND = "Client sent these data: ";
    private final static String SERVER_SEND = "Server is sending the response: ";
    private final static String SERVER_WAIT_NEW_DATA = "Server is waiting for new data...";
    private final static String CLOSE = "Connection with the client is closed";
    private final static int PORT = 6666;

    private static double square(double a) {
        return a*a;
    }

    private static double circle(double r) {
        return Math.PI*r*r;
    }

    private static double perimeter(double a, double b, double c, double d) {
        return a + b + c + d;
    }

    private static double volume(double r, double h) {
        return h*circle(r);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLog();

        CompletableFuture.runAsync(this::startServer);
    }

    private void startServer() {
        Server s = new Server();
        try {
            ServerSocket ss = new ServerSocket(PORT);

            boolean cont = true;

            while (cont) {
                appendLog(SERVER_WAIT);
                Socket socket = ss.accept();
                appendLog(CLIENT_CONNECT);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                int id = in.readInt();

                switch (id) {
                    case 1:
                        double a1;

                        double result1;

                        a1 = in.readDouble();
                        appendLog(CLIENT_SEND + a1);

                        result1 = square(a1);
                        appendLog(SERVER_SEND + result1);
                        out.writeDouble(result1);
                        out.flush();

                        cont = true;
                        break;
                    case 2:
                        double r2;

                        double result2;

                        r2 = in.readDouble();
                        appendLog(CLIENT_SEND + r2);

                        result2 = circle(r2);
                        appendLog(SERVER_SEND + result2);
                        out.writeDouble(result2);
                        out.flush();

                        cont = true;
                        break;
                    case 3:
                        double a3;
                        double b3;
                        double c3;
                        double d3;

                        double result3;

                        a3 = in.readDouble();
                        appendLog(CLIENT_SEND + a3);
                        appendLog(SERVER_WAIT_NEW_DATA);
                        b3 = in.readDouble();
                        appendLog(CLIENT_SEND + b3);
                        appendLog(SERVER_WAIT_NEW_DATA);
                        c3 = in.readDouble();
                        appendLog(CLIENT_SEND + c3);
                        appendLog(SERVER_WAIT_NEW_DATA);
                        d3 = in.readDouble();
                        appendLog(CLIENT_SEND + d3);

                        result3 = perimeter(a3, b3, c3, d3);
                        appendLog(SERVER_SEND + result3);
                        out.writeDouble(result3);
                        out.flush();

                        cont = true;
                        break;
                    case 4:
                        double r4;
                        double h4;

                        double result4;

                        r4 = in.readDouble();
                        appendLog(CLIENT_SEND + r4);
                        appendLog(SERVER_WAIT_NEW_DATA);
                        h4 = in.readDouble();
                        appendLog(CLIENT_SEND + h4);

                        result4 = volume(r4, h4);
                        appendLog(SERVER_SEND + result4);
                        out.writeDouble(result4);
                        out.flush();

                        cont = true;
                        break;
                    default:
                        cont = false;
                        break;
                }

                in.close();
                out.close();
                socket.close();
            }

            ss.close();
            System.out.println(CLOSE);
            Platform.exit();
            System.exit(0);
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }

    private void initLog() {
        textFlow = new TextFlow();
        vBox.getChildren().add(textFlow);

        vBox.heightProperty().addListener(((observable, oldValue, newValue) -> {
            if (needScroll) {
                vBox.layout();
                scroll.setVvalue(1.0d);
                needScroll = false;
            }
        }));
    }

    private void appendLog(String log) {
        Platform.runLater(() -> {
            Text text1 = new Text(log + "\n");;

            if (scroll.getVvalue() == 1d) {
                needScroll = true;
            }

            textFlow.getChildren().add(text1);
        });
    }
}
