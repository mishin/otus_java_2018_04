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

* */

import java.util.*;

public class ATM implements IATM{

    private final Integer MAX_DECKS = 3;
    private final Integer MAX_CAPACITY_PER_DECK = 5;
    private Integer balance = 0;
    private DeckATM[] decks = new DeckATM[MAX_DECKS];

    public ATM() {
        InitATM();
        recalcBalance();
    }

    // принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
    private void InitATM(){
        //ATM deck configuration
        decks[0] = new DeckATM(0, MAX_CAPACITY_PER_DECK, DeckNominal.ONE_THOUSAND ,Currency.RUB);
        decks[1] = new DeckATM(1, MAX_CAPACITY_PER_DECK, DeckNominal.HUNDRED,Currency.RUB);
        decks[2] = new DeckATM(2, MAX_CAPACITY_PER_DECK, DeckNominal.FIFTY,Currency.RUB);
    }


    @Override
    public void loadCash(Map<Integer, Integer> loadDeck, Currency currency) {


        for (Integer deck_index: loadDeck.keySet() ){
            System.out.printf("Deck %s loading..", deck_index);

            //if (decks[deck_index].getCurrency() == currency)
            //{
            try {
                    decks[deck_index].loadCash(loadDeck.get(deck_index), currency);
                    System.out.println(".Ok");
            } catch (InvalidCurrencyATMException | OverflowDeckCapacityException e) {
                    e.printStackTrace();
                    System.out.println(".Failed");
            }

        }

        printATM();
    }

    public void printATM() {
        System.out.println("ATM:");
        for (DeckATM d : decks) {
            System.out.format("ATM Decks #%s: Currency: %1s nominal: %4s capacity: %s count_notes: %s balance: %s",
                    d.getDeck_number(), d.getCurrency(), d.getNominal(), d.getCapacity(), d.getCount(), d.getBalance());

            System.out.println();
        }
        System.out.println("ATM balance: " + getBalance());
    }

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


    //return requested cash
    @Override
    public List withdrawCash(Integer withdrawAmount, Currency currency)
        throws InvalidAmountATMException, InvalidBalanceATMException {

        if (withdrawAmount % DeckNominal.FIFTY.getNominal() != 0) {
            //System.out.printf("Sorry, the requested amount must be a multiple of %s", DeckNominal.FIFTY.getNominal() );
            throw new InvalidAmountATMException("Sorry, the requested amount must be a multiple of " + 50);
        }
        else if (withdrawAmount > this.balance) {
            //System.out.println("ATM does not have enough money.");
            throw new InvalidBalanceATMException("ATM does not have enough money.");
        } else {

            return getMinimalNominal(withdrawAmount);
            //TODO: add get money from deck and change balance
        }
    }


    public List<Map<Integer,Integer>>  getMinimalNominal(Integer amount){
        return getMinimalNominal(amount,new ArrayList<>(), new HashSet<>());
    }

    protected List<Map<Integer,Integer>>  getMinimalNominal(Integer amount, List<Map<Integer,Integer>> resList ,Set<Integer> visited){

        int amountRest = 0;
        int div =0;
        Map<Integer,Integer> res = new HashMap<>();
//res every drill down is nulable

        //amountRest = amount;
        if (amount != 0) {
            for (DeckATM d : decks) {

                if (visited.contains(d.getNominal())) {
                    continue;
                }
                visited.add(d.getNominal());

                if(amount >= d.getNominal()){
                    div = amount / d.getNominal();
                        amountRest = amount - div * d.getNominal();
                        res.put(div, d.getNominal());
                        resList.add(res);
                        if (amountRest != 0)
                            getMinimalNominal(amountRest,resList, visited);

                }
            }
        }
        return resList;
    }



}
