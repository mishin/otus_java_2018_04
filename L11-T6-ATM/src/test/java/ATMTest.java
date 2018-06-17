import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class ATMTest extends ATM{

    private ATM atm = new ATM();

    @Before
    public void test_beforeInit() {
        atm.printATM();

    }

    @Test
    public void test_loadCashIncorrectCurrency(){
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,5);
        atm.loadCash(loadDeck, Currency.EUR);
    }

    @Test
    public void test_loadCashDeckOverflow(){
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,15);
        atm.loadCash(loadDeck, Currency.RUB);
    }

    //принимать банкноты разных номиналов
    @Test
    public void test_loadCash(){
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,4);
        loadDeck.put(DeckNominal.HUNDRED,5);
        loadDeck.put(DeckNominal.FIFTY,5);
        atm.loadCash(loadDeck, Currency.RUB);
    }


    @Test
    public void test_withdrawOverflow(){
        //load ATM
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,4);
        loadDeck.put(DeckNominal.HUNDRED,5);
        loadDeck.put(DeckNominal.FIFTY,5);
        atm.loadCash(loadDeck, Currency.RUB);

        //try to withdraw cash
        try {
            atm.withdrawCash(5350, Currency.RUB);
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_unloadDeckByDeckIndex(){
        //load ATM
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,4);
        loadDeck.put(DeckNominal.HUNDRED,5);
        loadDeck.put(DeckNominal.FIFTY,5);
        atm.loadCash(loadDeck, Currency.RUB);

        Map<Integer,Integer> unloadDeck = new HashMap<>();
        unloadDeck.put(0,1); //ONE_THOUSAND
        unloadDeck.put(1,2); //HUNDRED
        unloadDeck.put(2,3); //FIFTY
        atm.unloadCashByIndex(unloadDeck, Currency.RUB);
    }

    @Test
    public void test_unloadDeckByNominal(){

        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,1);
        loadDeck.put(DeckNominal.HUNDRED,2);
        loadDeck.put(DeckNominal.FIFTY,3);
        atm.loadCash(loadDeck, Currency.RUB);

        Map<DeckNominal,Integer> unloadDeck = new HashMap<>();
        unloadDeck.put(DeckNominal.ONE_THOUSAND,1);
        unloadDeck.put(DeckNominal.HUNDRED,2);
        unloadDeck.put(DeckNominal.FIFTY,3);
        atm.unloadCash(unloadDeck, Currency.RUB);
    }


    @Test
    public void test_getMinNom(){
        Map<Integer,Integer> res = getMinimalNominal(2750);
        System.out.println(res);
    }

    @Test
    public void test_withdrawCash(){

        //load ATM
        Map<DeckNominal,Integer> loadDeck = new HashMap<>();
        loadDeck.put(DeckNominal.ONE_THOUSAND,4);
        loadDeck.put(DeckNominal.HUNDRED,5);
        loadDeck.put(DeckNominal.FIFTY,5);
        atm.loadCash(loadDeck, Currency.RUB);

        //try to withdraw
        try {
            atm.withdrawCash(1350, Currency.RUB);
            assertEquals(3400, atm.getBalance(),0);
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_withdrawCash_unacceptable(){
        try {
            atm.withdrawCash(1460, Currency.RUB);
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }


    }


}