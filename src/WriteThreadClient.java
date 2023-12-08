import util.NetworkUtil;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadClient implements Runnable{
    NetworkUtil networkUtil;
    String name;
    public WriteThreadClient(NetworkUtil networkUtil, String name){
        this.networkUtil = networkUtil ;
        this.name = name ;
    }
    @Override
    public void run() {
        try{
            Scanner sc = new Scanner(System.in);
            while(true){
                String string = sc.nextLine();
                if(string.equals("server,inbox")){
                    networkUtil.write("server,inbox");
                }else{
                    String[] parts = string.split(", ");
                    if(parts.length == 2 ){
                        String receiver = parts[0].trim();
                        String msg = parts[1].trim();
                        Message message = new Message(name, receiver, msg);
                        networkUtil.write(message);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    public void setNetworkUtil(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
