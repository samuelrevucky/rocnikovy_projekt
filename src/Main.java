import com.ib.client.EClientSocket;
import com.ib.client.EReader;
import com.ib.client.EReaderSignal;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Main {

    private static class Run implements Observer{

        private boolean isConnected = false;
        private int hasStrikes = 0;

        private void run() throws InterruptedException {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter market data type: * 1 RealTime, 2 Frozen, 3 Delayed, 4 Delayed-Frozen *");
            int marketDataType = 0;
            do {
                String tmp = scanner.nextLine();
                if (tmp.matches("[1-4]")) marketDataType = Integer.parseInt(tmp);
                else {
                    System.out.println("Wrong format! Re-enter!");
                }
            } while (marketDataType == 0);

            CallbackListener callbackListener = new CallbackListener();
            //Observable that updates isConnected parameter of this class
            callbackListener.addObserver(this);

            OptionChain optionChain = new OptionChain();

            EWrapperImpl wrapper = new EWrapperImpl(callbackListener, optionChain, marketDataType);
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

            Thread.sleep(1000);
            if (!isConnected) return;

            System.out.println("Enter symbol, currency and expiration date of the option, each on separate line:");
            String symbol = null;
            String currency = null;
            String exp = null;
            do {
                symbol = scanner.nextLine();
                currency = scanner.nextLine();
                exp = scanner.nextLine();
                if (!symbol.matches("[A-Z]{3,4}") || !currency.matches("[A-Z]{3}") || !exp.matches("[0-9]{8}")){
                    symbol = null;
                    currency = null;
                    exp = null;
                    System.out.println("Wrong format entered, enter all parameters again!");
                }
            } while (symbol == null);

            long time = System.currentTimeMillis();

            client.reqMarketDataType(marketDataType);
            int reqId = 1;
            OptChainRetriever.getContracts(client, reqId, symbol, currency, exp, optionChain);
            do{
                Thread.sleep(1000);
            } while (hasStrikes != reqId);
            OptChainRetriever.updatePrices(client, optionChain);
            Thread.sleep(20000);
            do{
                Thread.sleep(1000);
            } while (optionChain.numberOfQuotes() < optionChain.numberOfContracts()*2);

            time = System.currentTimeMillis() - time;
            System.out.println("cas nacitania dat : " + time);
            client.eDisconnect();
        }

        @Override
        public void update(Observable o, Object arg) {
            if (arg instanceof Boolean) isConnected = (boolean) arg;
            if (arg instanceof Integer) hasStrikes = (int) arg;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Run().run();
    }
}
