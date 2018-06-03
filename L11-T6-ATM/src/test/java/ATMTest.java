
import org.junit.Test;
import org.junit.Before;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        loadDeck.put(0,4);
        loadDeck.put(1,5);
        loadDeck.put(2,5);
        atm.loadCash(loadDeck, Currency.RUB);
    }


    @Test
    public void test_withdrawOverflow(){
        //load ATM
        Map<Integer,Integer> loadDeck = new HashMap<>();
        loadDeck.put(0,4);
        loadDeck.put(1,5);
        loadDeck.put(2,5);
        atm.loadCash(loadDeck, Currency.RUB);

        //try to withdraw cash
        try {
            System.out.println(Arrays.toString(
                    atm.withdrawCash(5350, Currency.RUB).toArray())
            );
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test_getMinNom(){
        List<Map<Integer,Integer>> res = getMinimalNominal(2750);
        System.out.println( Arrays.toString(res.toArray()) );
    }

    @Test
    public void test_withdrawCash(){
        try {
            System.out.println(Arrays.toString(
                    atm.withdrawCash(1350, Currency.RUB).toArray())
            );
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test_withdrawCash_unacceptable(){
        try {
            System.out.println(Arrays.toString(
                    atm.withdrawCash(1460, Currency.RUB).toArray())
            );
        } catch (InvalidAmountATMException e) {
            e.printStackTrace();
        } catch (InvalidBalanceATMException e) {
            e.printStackTrace();
        }


    }


}