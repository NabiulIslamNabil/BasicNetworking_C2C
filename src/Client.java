import util.NetworkUtil;

import java.util.Scanner;

public class Client {
    private String clientName;
    public Client(String serverAddress, int serverPort) {
        try {
            System.out.print("Enter name of the client: ");
            Scanner sc = new Scanner(System.in);
            this.clientName = sc.nextLine();

            NetworkUtil networkUtil = new NetworkUtil(serverAddress, serverPort);
            networkUtil.write(this.clientName);

            ReadThreadClient readThread = new ReadThreadClient(networkUtil);
            Thread readClient = new Thread(readThread);
            readClient.start();

            WriteThreadClient writeThread = new WriteThreadClient(networkUtil, this.clientName);
            Thread writeClient = new Thread(writeThread);
            writeClient.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        Client client = new Client(serverAddress, serverPort);
    }
}


