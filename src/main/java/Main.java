import com.ib.client.*;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        /**
         * The class serves as a mechanism through which the brokerage delivers information to this application.
         */
        EWrapperImpl wrapper = new EWrapperImpl();

        /**
         * The class used to send messages to the brokerage.
         */
        EClientSocket client = wrapper.getClient();

        /**
         * A class that uses Java Monitor.
         * Used to synchronize threads waiting for messages from brokerage and receiving messages from brokerage.
         */
        EReaderSignal signal = wrapper.getSignal();

        client.eConnect("127.0.0.1", 7497, 1);   //initialize connection to brokerage on socket 7497
                                                         //if trading in live mode, needs to be changed to 7496

        if (!client.isConnected()) throw new RuntimeException("Unable to connect to TWS");

        /**
         * Thread that receives and processes messages from brokerage and passes them to the wrapper class to handle.
         */
        EReader reader = new EReader(client, signal);
        reader.start();
        handleMessages(reader, signal);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter symbol");
        String symbol = scanner.next();
        System.out.println("Enter date in YYYYMMDD format");
        String expDate = scanner.next();
        System.out.println("Enter url");
        String url = scanner.next();

        //"https://finance.yahoo.com/quote/AAL/options?p=AAL"
        OptionChain chain = YFQuotes.getYFQuotes(url);
        HashMap<Option, Integer> map = chain.longIronBtfly();

        int nextOrderId = wrapper.currentOrderId;
        for (Option x : map.keySet()) {
            client.placeOrder(nextOrderId++,
                    new Contract() {{
                        symbol(symbol);
                        secType("OPT");
                        exchange("SMART");
                        currency("USD");
                        lastTradeDateOrContractMonth(expDate);
                        right(x.getRight().toString());
                        strike(x.getStrike());
                        multiplier("100");
                    }},
                    new Order() {{
                        if (map.get(x) < 0) action("SELL");
                        else action("BUY");
                        orderType("MKT");
                        totalQuantity(Math.abs(map.get(x)));
                    }});
            handleMessages(reader, signal);
        }

        client.eDisconnect();
    }

    private static void handleMessages(EReader reader, EReaderSignal signal) {
        signal.waitForSignal();
        try {
            reader.processMsgs();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
