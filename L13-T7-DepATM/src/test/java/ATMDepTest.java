import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;


public class ATMDepTest extends ATMDep {


    private ATMDep atmdep = new ATMDep();

    @Before
    public void test_beforeInit() {
        System.out.print("ATM Department balance is ");
        atmdep.loadRandomATMDep(10);

    }

    @Test
    public void test_getATMDep_print(){

        atmdep.print();

    }


    @Test
    public void test_getBalanceAll(){

        System.out.print("ATM Department balance is ");
        System.out.println(atmdep.getBalanceAll());

    }

    @Test
    public void test_setInitState(){

        atmdep.setInitState();

    }


}