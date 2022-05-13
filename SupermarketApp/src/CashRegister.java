import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.DoublePredicate;

public class CashRegister {

    public static final Float COIN_0_1 = 0.1F;
    public static final Float COIN_0_5 = 0.5F;
    public static final Float COIN_1_0 = 1.0F;
    public static final Float COIN_2_0 = 2.0F;

    private Map<Float,Integer> totalCash;

   public CashRegister(){
      totalCash  = new HashMap<Float, Integer>();
      totalCash.put(COIN_0_1,3);
      totalCash.put(COIN_0_5,50);
      totalCash.put(COIN_1_0,50);
      totalCash.put(COIN_2_0,50);

        // key of map is the value of the coin, and the value of map is quantity.

    }

    public Float getTotalCash(){
        Float totalValue = 0.0F;
        for (Map.Entry<Float, Integer> entry : totalCash.entrySet()) {
            Float key = entry.getKey();
            Integer value = entry.getValue();
            totalValue += (key * value);
        }

        return totalValue;
    }


    public Map<Float,Integer> getCashInventory(){ return totalCash; };

    public Boolean isCoinValid(Float coin) throws PayNotAcceptedException{
        //if coin is valid
        if (totalCash.containsKey(coin)){
            return true;
        }else throw new PayNotAcceptedException("Coin not accepted");

    }

    public void insertToCashRegister(Map<Float,Integer> insertedMoney){
        //insert the money into register

        for (Map.Entry<Float, Integer> entry : insertedMoney.entrySet()) {
            Float key = entry.getKey();
            Integer value = entry.getValue();

            totalCash.computeIfPresent(key, (totalKey, totalValue) -> totalValue + value);

        }

    }

    public void removeFromRegister (Map<Float,Integer> insertedMoney){

        //in case that we cannot return change
        for (Map.Entry<Float, Integer> entry : insertedMoney.entrySet()) {
            Float key = entry.getKey();
            Integer value = entry.getValue();

            totalCash.computeIfPresent(key, (totalKey, totalValue) -> totalValue - value);
        }

    }

    public Map<Float,Integer> calculateChange(Float change)throws NotEnoughChangeException{

        Boolean canReturnChange = true;
        Float var_change = change;
        Map<Float,Integer> changeCoins = new HashMap<>();
        SortedSet<Float> coinTypes;
        coinTypes = new TreeSet<Float>(new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                return o2.compareTo(o1);
            }
        });
        coinTypes.addAll(totalCash.keySet());

        while (var_change > 0){

            canReturnChange = false;

            for (Float aFloat : coinTypes) {
                if(totalCash.get(aFloat) > 0 && aFloat <= var_change){
                    //yes, there is a coresponding coin
                    canReturnChange = true;
                   // find  way to get rid of giberish
                    var_change-=aFloat;
                    Double result = Math.round(var_change * 10.0F)/10.0;
                    var_change = result.floatValue();
                       // System.out.println(var_change);

                        if(!changeCoins.containsKey(aFloat)){
                            changeCoins.put(aFloat, 1);
                            totalCash.replace(aFloat, totalCash.get(aFloat) -1 );
                        }else{
                            changeCoins.put(aFloat,changeCoins.get(aFloat)+1);
                            totalCash.replace(aFloat, totalCash.get(aFloat) -1 );
                        }

                        break;
                }
            }

            if (!canReturnChange){
                insertToCashRegister(changeCoins);
                //return to register what we got so far
                throw new NotEnoughChangeException("Cannot return change");
            }
        }
        return changeCoins;

    };

    public Set<Float> getAcceptedCoins(){
        return totalCash.keySet();
    }


}

