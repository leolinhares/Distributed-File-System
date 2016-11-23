import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by leolinhares on 21/11/16.
 */
public interface ProxyInterface extends Remote{

    String createFile(String filename, String contents) throws RemoteException, NotBoundException;
    String readFile(String filename) throws RemoteException;

}
