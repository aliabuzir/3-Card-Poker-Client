import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
    int count = 1;
    ArrayList<ClientThreads> clients = new ArrayList<ClientThreads>();

    Acceptor server;
    private Consumer<Serializable> callback;


    Server(Consumer<Serializable> call){

        callback = call;
        server = new Acceptor();
        server.start();
    }

    public class Acceptor extends Thread {
        public void run() {

            try(ServerSocket mysocket = new ServerSocket(5555);){
                System.out.println("Server is waiting for a client!");


                while(true) {

                    if (clients.size() < 4) {
                        ClientThreads client = new ClientThreads(mysocket.accept(), count);
                        callback.accept("client has connected to server: " + "client #" + count);
                        clients.add(client);
                        client.start();
                        count++;
                    }

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }

    public class ClientThreads extends Thread {
        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThreads(Socket s, int count){
            this.connection = s;
            this.count = count;
        }


        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
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
                    break;
                }
            }
        }//end of run

    }
}
