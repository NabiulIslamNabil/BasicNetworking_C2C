import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadThreadServer implements Runnable {
    private HashMap<String, NetworkInformation> clientNetworkInformationMap;
    private NetworkInformation currentClientNetworkInfo;


    public ReadThreadServer(HashMap<String, NetworkInformation> clientMap, NetworkInformation currentClientInfo) {
        this.clientNetworkInformationMap = clientMap;
        this.currentClientNetworkInfo = currentClientInfo;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object received = currentClientNetworkInfo.getNetworkUtil().read();
                if (received instanceof Message) {
                    Message message = (Message) received;
                    String sender = message.getFrom();
                    String receiver = message.getTo();
                    String messageText = message.getText();

                    NetworkInformation receiverNetworkInfo = clientNetworkInformationMap.get(receiver);

                    if (receiverNetworkInfo != null) {
                        receiverNetworkInfo.getInbox().add("From: " + sender + " Message: " + messageText);
                        receiverNetworkInfo.getNetworkUtil().write(message);
                    } else {
                        System.out.println("Receiver not found!");
                    }
                } else if (received.equals("server,inbox")) {
                    ArrayList<String> inboxMessages = currentClientNetworkInfo.getInbox();
                    currentClientNetworkInfo.getNetworkUtil().write(inboxMessages);
                } else {
                    System.out.println("Not an instance of message");
                }

            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}