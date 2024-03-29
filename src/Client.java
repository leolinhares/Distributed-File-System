import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by leolinhares on 21/11/16.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        try {
            // Establish connection with the Load Balancer
            Registry registry = LocateRegistry.getRegistry(null);
            LoadBalancerInterface lbstub = (LoadBalancerInterface) registry.lookup("LoadBalancer");

            // Get the proxy number from the Load Balancer and establish proxy connection
//            int proxyID = Integer.parseInt(lbstub.getProxy());
//            ProxyInterface proxyStub = (ProxyInterface) registry.lookup("ProxyInterface"+proxyID);

            do{
                int option;

                Scanner input_exit = new Scanner(System.in);
                System.out.println("\n----------------------------");
                System.out.println("\nOptions: \n");
                System.out.println("[1] - Create file");
                System.out.println("[2] - Read file");
                System.out.println("[0] - EXIT \n");
                System.out.print("Enter option: ");
                option = input_exit.nextInt();

                if (option == 1){
                    Scanner in = new Scanner(System.in);

                    System.out.println("\nEnter filename and the file contents: \n");
                    String filename = in.nextLine();
                    String contents = in.nextLine();
                    // Get the proxy number from the Load Balancer and establish proxy connection
                    int proxyID = Integer.parseInt(lbstub.getProxy());
                    ProxyInterface proxyStub = (ProxyInterface) registry.lookup("ProxyInterface"+proxyID);
                    System.out.println(proxyStub.createFile(filename, contents));

                }else if (option == 2){
                    System.out.println("\nFilename: \n");
                    Scanner in = new Scanner(System.in);
                    // Get the proxy number from the Load Balancer and establish proxy connection
                    int proxyID = Integer.parseInt(lbstub.getProxy());
                    ProxyInterface proxyStub = (ProxyInterface) registry.lookup("ProxyInterface"+proxyID);
                    System.out.println(proxyStub.readFile(in.nextLine()));
                }else{
                    break;
                }

            }while (true);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
