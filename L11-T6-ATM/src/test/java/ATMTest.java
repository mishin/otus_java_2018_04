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
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,5);
        atm.loadCash(loadDeck, Currency.EUR);
    }

    @Test
    public void test_loadCashDeckOverflow(){
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,15);
        atm.loadCash(loadDeck, Currency.RUB);
    }

    //принимать банкноты разных номиналов
    @Test
    public void test_loadCash(){
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,4); //ONE_THOUSAND
        loadDeck.put(1,5); //HUNDRED
        loadDeck.put(2,5); //FIFTY
        atm.loadCash(loadDeck, Currency.RUB);
    }


    @Test
    public void test_withdrawOverflow(){
        //load ATM
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,4); //ONE_THOUSAND
        loadDeck.put(1,5); //HUNDRED
        loadDeck.put(2,5); //FIFTY
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
    public void test_unloadDeck(){
        //load ATM
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,4); //ONE_THOUSAND
        loadDeck.put(1,5); //HUNDRED
        loadDeck.put(2,5); //FIFTY
        atm.loadCash(loadDeck, Currency.RUB);

        Map<Integer,Integer> unloadDeck = new HashMap<>();
        unloadDeck.put(0,1); //ONE_THOUSAND
        unloadDeck.put(1,2); //HUNDRED
        unloadDeck.put(2,3); //FIFTY
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
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,4); //ONE_THOUSAND
        loadDeck.put(1,5); //HUNDRED
        loadDeck.put(2,5); //FIFTY
        atm.loadCash(loadDeck, Currency.RUB);

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