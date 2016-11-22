import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by leolinhares on 21/11/16.
 */
public class LoadBalancer implements LoadBalancerInterface{

    @Override
    public String createFile(String filename) {
        try {
            // Establish connection with the storage nodes
            Registry registry = LocateRegistry.getRegistry(null);
            int proxyID = 0;
            ProxyInterface stub = (ProxyInterface) registry.lookup("ProxyInterface"+proxyID);
            stub.createResource(filename, proxyID);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String readFile(String filename) {
        return null;
    }

    public static void main(String[] args) {
        try{
            LoadBalancer loadBalancer = new LoadBalancer();
            LoadBalancerInterface stub = (LoadBalancerInterface) UnicastRemoteObject.exportObject(loadBalancer,0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("LoadBalancer", stub);

            System.out.println("LoadBalancer ready\n\nLog:\n");
        }catch (Exception e){
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }
}
