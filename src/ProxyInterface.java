import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface ProxyInterface extends Remote{

    String createResource(String filename, int id) throws RemoteException;
    String requestResource(String filename, int id) throws RemoteException;

}
