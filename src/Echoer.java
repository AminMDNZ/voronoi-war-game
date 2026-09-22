import classes.RequestHandler;
import classes.RespondHandler;
//import com.google.gson.Gson;

import javax.naming.ldap.Rdn;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Echoer extends Thread{
    private Socket socket;

    public Echoer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            int role = 0;
            while (true){
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                RequestHandler request = (RequestHandler) objectInputStream.readObject();
                String requestType = request.getRequestType();
                if(request.getRequestType().equals("Login")){

                }





                System.out.println("Server: " + request.getRequestType() + " " + request.getJsonObj());
//               objectInputStream.close();
            }
        }
        catch (IOException e){
            System.out.println("e: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e){

            }
        }
    }
}
