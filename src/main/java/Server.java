import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
    private int count = 1;
    public ArrayList<ClientThreads> clients;

    private Acceptor server;
    private Consumer<Serializable> callback;

    private String portNumber;


    Server(Consumer<Serializable> call, String portNumber){
        clients = new ArrayList<ClientThreads>();
        callback = call;
        server = new Acceptor();
        server.start();
        this.portNumber = portNumber;
    }

    public class Acceptor extends Thread {
        public void run() {

            try(ServerSocket mysocket = new ServerSocket(Integer.valueOf(portNumber))){
                //System.out.println("Server is waiting for a client!");


                while(true) {

                    if (clients.size() <= 4) {
                        ClientThreads client = new ClientThreads(mysocket.accept(), new PokerInfo(false, 0, 0));

                        callback.accept("client has connected to server: " + "client #" + count);
                        clients.add(client);
                        client.start();
                        count++;
                    }

                }
            }//end of try
            catch (BindException e) {
                callback.accept("Server can't listen to listed port. Do you already have a server running?");
            }
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    public class ClientThreads extends Thread {
        Socket connection;
        //int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        PokerInfo pokerGame;


        ClientThreads(Socket s, PokerInfo pokerGame){
            this.connection = s;
            this.pokerGame = pokerGame;
        }


        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
                send(pokerGame);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            while(true) {
                try {
                    String data = in.readObject().toString();
                    callback.accept("client: " + count + " sent: " + data);

                }
                catch(Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    count--;
                    break;
                }
            }
        }//end of run

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
}
