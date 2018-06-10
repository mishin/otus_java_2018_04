import java.util.List;
import java.util.Map;

public interface IATM {

    void withdrawCash(Integer requestedCash, Currency rub)
            throws InvalidAmountATMException, InvalidBalanceATMException;

    Integer getBalance();

    void loadCash(Map<Integer, Integer> loadDeck, Currency currency)
            throws InvalidCurrencyATMException, OverflowDeckCapacityException;
}
