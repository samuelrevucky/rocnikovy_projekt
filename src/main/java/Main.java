import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;

import java.util.Observable;
import java.util.Observer;

public class Main {

    private static class Run implements Observer{

        private boolean isConnected = false;

        private void run() throws InterruptedException {

            CallbackListener callbackListener = new CallbackListener();
            callbackListener.addObserver(this);
            EWrapperImpl wrapper = new EWrapperImpl(callbackListener);
            EClientSocket client = wrapper.getClient();
            EReaderSignal signal = wrapper.getSignal();

            client.eConnect("127.0.0.1", 7497, 1);

            EReader reader = new EReader(client, signal);
            reader.start();
            new Thread(() -> {
                while (client.isConnected()) {
                    signal.waitForSignal();
                    try {
                        reader.processMsgs();
                    } catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                    }
                }
            }).start();

            YFQuotes yfQuotes = new YFQuotes("https://finance.yahoo.com/quote/AAL/options?p=AAL");
            Thread.sleep(1000);
            if (!isConnected) return;

            client.eDisconnect();
        }

        @Override
        public void update(Observable o, Object arg) {
            isConnected = (boolean) arg;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Run().run();
    }
}
