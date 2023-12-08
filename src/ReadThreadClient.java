import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;

public class ReadThreadClient implements Runnable{
    private ArrayList<String> inbox;
    NetworkUtil networkUtil;


    public ReadThreadClient(NetworkUtil network) {
        networkUtil = network ;
        this.inbox = new ArrayList<>();
    }

    @Override
    public void run() {
        try{
            while(true){
                Object received = networkUtil.read();
                if(received instanceof Message){
                    Message rec = (Message) received;
                    inbox.add(rec.toString());
                    System.out.println(rec);
                } else if (received instanceof ArrayList) {
                    ArrayList<String> receivedInbox = (ArrayList<String>) received;
                    for (String message : receivedInbox) {
                        System.out.println(message);
                    }
                } else{
                    System.out.println("Not an instance of message");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void printInbox() throws IOException {
        networkUtil.write("server,inbox");
    }
}
