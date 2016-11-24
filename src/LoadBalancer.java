import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by leolinhares on 21/11/16.
 */
public class LoadBalancer implements LoadBalancerInterface{

    @Override
    public String getProxy() {
        int proxyID = 0;

        for (int i = 0; i < 2; i++){
            try {
                proxyID = i;
                // Establish connection with the storage nodes
                Registry registry = LocateRegistry.getRegistry(null);
                ProxyInterface stub = (ProxyInterface) registry.lookup("ProxyInterface"+proxyID);
                String value = stub.test();
                break;
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
        return Integer.toString(proxyID);
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
