/*
* ATM's deck
* */

public class DeckATM {

    private Integer deck_number;
    private Integer capacity;
    private DeckNominal nominal;
    private Integer count;
    private Currency currency;



    public DeckATM(Integer deck_number, Integer capacity, DeckNominal nominal, Currency currency){
        this.deck_number = deck_number;
        this.capacity = capacity;
        this.nominal = nominal;
        this.currency = currency;
        this.count = 0;
    }

    public Currency getCurrency(){
        return this.currency;
    }

    public int getBalance() {
        return count * nominal.getNominal();
    }

    public int getNominal() {
        return nominal.getNominal();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCount() {
        return count;
    }

    public void loadCash(int loadCount, Currency currency)
            throws InvalidCurrencyATMException, OverflowDeckCapacityException {

        if (this.currency != currency){
            throw new InvalidCurrencyATMException("Invalid loading currency");
        }

        if (capacity - count < loadCount) {
            throw new OverflowDeckCapacityException("Overflow capacity of deck");

        } else count += loadCount;
    }


    public Integer getDeck_number() {
        return deck_number;
    }

}
