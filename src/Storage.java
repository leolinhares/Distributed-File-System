import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by leolinhares on 21/11/16.
 */
public class Storage implements StorageInterface {

    private int id;

    public Storage(int id) throws RemoteException {
        this.id = id;
        System.out.println("Storage " + id + " Initialized");
    }

    @Override
    public String createResource(String filename) throws RemoteException {
        // criar o arquivo solicitado
        return null;
    }

    @Override
    public String requestResource(String filename) throws RemoteException {
        // retornar a string com o nome do arquivo apos pesquisar na pasta
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Digite o numero do storage: ");
        Scanner in = new Scanner(System.in);
        try {
            Storage storage = new Storage(in.nextInt());
            StorageInterface stub = (StorageInterface) UnicastRemoteObject.exportObject(storage, 0);

            //Binding
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("StorageInterface" + storage.getId(), stub);

            System.out.println("Storage "+ storage.getId()+" ready\n\nLog:\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
}
