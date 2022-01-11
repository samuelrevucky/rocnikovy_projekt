import java.util.Observable;

public class CallbackListener extends Observable {

    void setConnection(boolean state) {
        setChanged();
        notifyObservers(state);
    }
}
