import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ATMDep {

    private final int MAX_CAPACITY_DEPATM = 10;
    private ATM[] atmdep = new ATM[MAX_CAPACITY_DEPATM];
    private int balance = 0;


    /**
     * @name loadRandomATMDep is try to generate random ATM department
     *
     * @param  Capacity   ATM department capacity
     *
     * @throws InvalidAmountATMException  if requested can be multiple minimal nominal cut (50)
     * @throws InvalidBalanceATMException if ATM does not have enough money
     */
    public void loadRandomATMDep(Integer Capacity){
        Random rand = new Random();

        for(int i = 0; i <= Capacity-1; i+=1) {
            atmdep[i] = new ATM();

            Map<DeckNominal, Integer> loadDeck = new HashMap<>();
            loadDeck.put(DeckNominal.ONE_THOUSAND, rand.nextInt(10));
            loadDeck.put(DeckNominal.HUNDRED, rand.nextInt(20));
            loadDeck.put(DeckNominal.FIFTY, rand.nextInt(30));
            atmdep[i].loadCash(loadDeck, Currency.RUB);
        }

    }

    /**
     * @name getBalanceAll - get summary balance whole ATM dep
     *
     */
    public Integer getBalanceAll(){
        for (IATM iatm: atmdep){
            balance += iatm.getBalance();
        }
        return balance;
    }

    /**
     * @name setInitState - rollback to the initial state
     *
     */
    public void setInitState() {

    }

    /**
     * @name print - print all info by each the ATM consist to ATM Department
     *
     */
    public void print() {
        System.out.println("ATM Department contains  ");
        for (ATM atm: atmdep){
            System.out.print("ATM GUID = "+atm.getGuid());
            atm.printATM();
        }
    }
}
