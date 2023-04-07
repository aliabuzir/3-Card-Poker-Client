import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Client extends Thread {
    private Socket socketClient;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String IPAddress;

    private String portNumber;

    private Consumer<Serializable> callback;

    PokerInfo pokerGame;

    Client(Consumer<Serializable> call, String IPAddress, String portNumber){

        callback = call;
        this.IPAddress = IPAddress;
        this.portNumber = portNumber;
    }

    public void run() {

        try {
            socketClient= new Socket(IPAddress, Integer.valueOf(portNumber));
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {
            callback.accept("Server not found.");
        }

        while(true) {

            try {
                PokerInfo message = (PokerInfo) in.readObject();
                callback.accept(message);
            }
            catch(Exception e) {
                callback.accept("Connection to the server has been lost.");
            }
        }

    }

    public void send(PokerInfo data) {

        try {
            out.writeObject(data);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
