import com.ib.client.Contract;
import com.ib.client.EClientSocket;

public class OptChainRetriever {
    public static void getContracts(EClientSocket client, int reqId, String symbol, String currency, String lastTradeDate, OptionChain optionChain) throws InterruptedException {
        Contract contract = new Contract();
        contract.symbol(symbol);
        contract.secType("OPT");
        contract.exchange("SMART");
        contract.currency(currency);
        contract.lastTradeDateOrContractMonth(lastTradeDate);
        client.reqContractDetails(reqId, contract);
    }

    public static void updatePrices(EClientSocket client, OptionChain optionChain) throws InterruptedException {
        for (Option a : optionChain.calls().values()) {
            Contract callContract = new Contract();
            callContract.conid(a.conid());
            callContract.exchange("SMART");
            client.reqMktData((int) (a.strike() * 1000), callContract, "", true, false, null);
            Thread.sleep(11);
        }
        for (Option a : optionChain.puts().values()){
            Contract putContract = new Contract();
            putContract.conid(a.conid());
            putContract.exchange("SMART");
            client.reqMktData((int) (a.strike()*1000 + 1), putContract, "", true, false, null);
            Thread.sleep(11);
        }
    }
}
