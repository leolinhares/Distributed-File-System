import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface ProxyInterface extends Remote{

    String createFile(String filename) throws Exception;
    String readFile(String filename) throws RemoteException;

}
