//implementation of interface
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
public class AddRemImpl extends UnicastRemoteObject implements AddRem {
    public AddRemImpl() throws RemoteException {}
    public int[] addNum(int[] a) {
        Arrays.sort(a);
        return a;
    }
}