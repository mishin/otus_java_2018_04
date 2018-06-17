/*
Понятие дизайна приложения и паттерна проектирования.
Концепты проектирования ООП: delegation, aggregation, composition, association,
coupling and cohesion. SOLID.

* ДЗ 06. Написать эмулятор АТМ
Написать эмулятор АТМ (банкомата).

Объект класса АТМ должен уметь
• принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
• выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
• выдавать сумму остатка денежных средств

*/

import java.util.*;

public class ATM implements IATM{

    private final Integer MAX_DECKS = 3;
    private final Integer MAX_CAPACITY_PER_DECK = 5;
    private Integer balance = 0;
    private DeckATM[] decks = new DeckATM[MAX_DECKS];

    public ATM() {
        initDecks();
        recalcBalance();
    }

    /**
     * Class @name initDecks Inititalize ATM's deck
     *
     */
    private void initDecks(){
        //setup ATM's deck configuration
        decks[0] = new DeckATM(0, MAX_CAPACITY_PER_DECK, DeckNominal.ONE_THOUSAND ,Currency.RUB);
        decks[1] = new DeckATM(1, MAX_CAPACITY_PER_DECK, DeckNominal.HUNDRED,Currency.RUB);
        decks[2] = new DeckATM(2, MAX_CAPACITY_PER_DECK, DeckNominal.FIFTY,Currency.RUB);
    }

    /**
     * Class @name loadCashbyIndex Loading some cash to ATM by number of deck and count, Currency
     *
     *  @param  deck     Map<DeckIndex,Count>
     *  @param  currency Currency
     */
    public void loadCashbyIndex(Map<Integer, Integer> deck, Currency currency) {

        for (Integer deck_index: deck.keySet() ){
            System.out.printf("Deck %s loading..", deck_index);

            try {
                    decks[deck_index].loadDeck(deck.get(deck_index), currency);
                    System.out.println(".Ok");
            } catch (InvalidCurrencyATMException | OverflowDeckCapacityException e) {
                    e.printStackTrace();
                    System.out.println(".Failed");
            }

        }

        printATM();
    }

    /**
     * Loading some cash to ATM by number of deck and count, Currency
     *
     *  @param  deck     Map<Nominal,Count>
     *  @param  currency Currency
     */
    @Override
    public void loadCash(Map<DeckNominal, Integer> deck, Currency currency) {

        for (DeckNominal nominal: deck.keySet() ){
            System.out.printf("Deck %s loading..", nominal);

            try {

                for (int i = 0; i < decks.length; i++) {
                    if ( decks[i].getNominal() == nominal.getNominal()) {
                        decks[i].loadDeck( deck.get(nominal), currency);
                        System.out.println(".Ok");
                    }
                }


            } catch (InvalidCurrencyATMException | OverflowDeckCapacityException e) {
                e.printStackTrace();
                System.out.println(".Failed");
            }

        }

        printATM();
    }

    /**
     * Unloading from ATM by number of deck and count, Currency
     *
     *  @param  deck     Map<Nominal,Count>
     *  @param  currency Currency
     */
    public void unloadCashByIndex(Map<Integer, Integer> deck, Currency currency) {

        for (Integer deck_index: deck.keySet() ){
            System.out.printf("Deck %s unloading..", deck_index);

            try {
                decks[deck_index].unloadDeck(deck.get(deck_index), currency);
                System.out.println(".Ok");
            } catch (InvalidCurrencyATMException | OverflowDeckCapacityException e) {
                e.printStackTrace();
                System.out.println(".Failed");
            }

        }

    }
    /**
     * Unloading from ATM by Nominal and count, Currency
     *
     *  @param  deck     Map<Nominal,Count>
     *  @param  currency Currency
     */
    public void unloadCash(Map<DeckNominal, Integer> deck, Currency currency){

        for (DeckNominal nominal: deck.keySet() ){
            System.out.printf("Deck %s unloading..", nominal);

            try {

                for (int i = 0; i < decks.length; i++) {
                    if ( decks[i].getNominal() == nominal.getNominal()) {
                        decks[i].unloadDeck( deck.get(nominal), currency);
                        System.out.println(".Ok");
                    }
                }
            } catch (InvalidCurrencyATMException | OverflowDeckCapacityException e) {
                e.printStackTrace();
                System.out.println(".Failed");
            }

        }
    }


    /**
     * Printing current state of ATM
     *
     */
    public void printATM() {
        System.out.println("ATM:");
        for (DeckATM d : decks) {
            System.out.format("ATM Decks #%s: Currency: %1s nominal: %4s capacity: %s count_notes: %s balance: %s",
                    d.getDeck_number(), d.getCurrency(), d.getNominal(), d.getCapacity(), d.getCount(), d.getBalance());

            System.out.println();
        }
        System.out.println("ATM balance: " + getBalance());
    }

    /**
     * Getting current balance of ATM
     *
     */
    @Override
    public Integer getBalance() {
        recalcBalance();
        return this.balance;
    }

    private void recalcBalance(){
        Integer balance = 0;
        for (DeckATM d : decks) {
            balance += d.getBalance();
        }
        this.balance  = balance;
    }


    /**
     * Withdraw some cash from ATM
     *
     * @param  withdrawAmount   requested cash
     * @param  currency Currency
     *
     * @throws InvalidAmountATMException  if requested can be multiple minimal nominal cut (50)
     * @throws InvalidBalanceATMException if ATM does not have enough money
     */
    @Override
    public void withdrawCash(Integer withdrawAmount, Currency currency)
        throws InvalidAmountATMException, InvalidBalanceATMException {

        if (withdrawAmount % DeckNominal.FIFTY.getNominal() != 0) {
            throw new InvalidAmountATMException("Sorry, the requested amount must be a multiple of " + DeckNominal.FIFTY.getNominal());
        }
        else if (withdrawAmount > this.balance) {
            throw new InvalidBalanceATMException("Sorry, ATM does not have enough money. Please try again latter.");
        } else {

            //get collection of different nominals and count
            Map<Integer,Integer> requestedCashMap = getMinimalNominal(withdrawAmount);

            unloadCashByIndex(requestedCashMap, Currency.RUB);

            recalcBalance();

            printATM();

        }
    }


    public Map<Integer,Integer>  getMinimalNominal(Integer amount){
        return getMinimalNominal(amount,new HashMap<>(), new HashSet<>());
    }

    private Map<Integer,Integer>  getMinimalNominal(Integer amount,
            Map<Integer,Integer> res, Set<Integer> visited){

        int amountRest = 0;
        int div = 0;

        if (amount != 0) {
            for (DeckATM d : decks) {

                if (visited.contains(d.getNominal())) {
                    continue;
                }
                visited.add(d.getNominal());

                if(amount >= d.getNominal()){
                    div = amount / d.getNominal();
                        amountRest = amount - div * d.getNominal();
                        res.put(d.getDeck_number(),div);
                        if (amountRest != 0)
                            getMinimalNominal(amountRest,res, visited);
                }
            }
        }
        return res;
    }



}
