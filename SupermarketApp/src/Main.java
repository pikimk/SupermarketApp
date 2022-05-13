import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] argv) {

        SupermarketServiceImpl market = SupermarketServiceImpl.getInstance();

        market.printInitialStorage();
        market.printInitialRegister();
        market.buySomething();
        market.returnChange();
        market.outboundProduct();
        market.printInitialStorage();
        market.printInitialRegister();


    }

}
