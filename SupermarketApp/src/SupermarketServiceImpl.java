import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SupermarketServiceImpl implements SupermarketService{

        private static SupermarketServiceImpl instance;
        private CashRegister cashRegister;
        private ProductStorage storage;
        private Float change = 0f;
        Product p;
        private Map<Float,Integer> payedCoins;


            SupermarketServiceImpl(){
                cashRegister = new CashRegister();
                storage = new ProductStorage();
            }

        public static SupermarketServiceImpl getInstance(){
            if (instance == null){
                instance = new SupermarketServiceImpl();
            }
            return  instance;
        }

        public void printInitialStorage(){

            System.out.println("-------------------------------------------");
            System.out.println("Product Inventory: ");
            for (Map.Entry<Product, Integer> entry : storage.getAllProducts().entrySet()) {
                Product product = entry.getKey();
                Integer integer = entry.getValue();
                System.out.println(product.getName() +" Quantity: "+integer);
            }
            System.out.println("-------------------------------------------");

        }

        public void printInitialRegister(){

            System.out.println("-------------------------------------------");
            System.out.println("Cash Register Inventory: ");
            for(Map.Entry<Float,Integer> entry : cashRegister.getCashInventory().entrySet()){
                Float coinType = entry.getKey();
                Integer coinQuan = entry.getValue();
                System.out.println("Value: "+coinType+" Quantity: "+coinQuan);
            }
            System.out.println("-------------------------------------------");
        }



       public void buySomething(){
           System.out.println("What would you like to buy? \n Type the name of the desired product: ");
           for(Map.Entry<Product,Integer> entry : storage.getAllProducts().entrySet() ){
               Product p = entry.getKey();
               System.out.println(p.getName()+" (Price " + p.getPrice()+")");
           }

           Scanner s = new Scanner(System.in);
           String product = s.nextLine().toUpperCase();


           try{ p = storage.getProduct(product); }catch (Exception e){ e.printStackTrace();
               return;
           }

           System.out.println("You are trying to buy "+product);
           System.out.println("You you need to pay: "+p.getPrice());
           System.out.print("Accepted coins: ");
           cashRegister.getAcceptedCoins().forEach(aFloat -> {
               System.out.print(aFloat + ", ");
           });
           System.out.println();

           Float sum = 0F;
           payedCoins = new HashMap<>();

           while (sum < p.getPrice()){
               try{
                   Float coin = s.nextFloat();
                   sum+=coin;
                   coin = Math.round(coin * 10.0F)/10.0F;
                   sum = Math.round(sum * 10.0F)/10.0F;

                   //check
                   cashRegister.isCoinValid(coin);
                   Float dif = p.getPrice() - sum;
                   dif = Math.round(dif * 10.0F)/10.0F;

                   if(payedCoins.get(coin) != null) {
                       payedCoins.put(coin, payedCoins.get(coin) + 1);
                   }else{
                       payedCoins.put(coin, 1);
                   }

                   if(dif > 0) {
                       System.out.println("You paid " + sum + " in total, \n you still need to pay " + dif);
                   }else if(dif < 0){
                       //this is the change
                       change = Math.abs(dif);

                   }

               }catch (Exception e){
                   e.printStackTrace();
                   return;
               }


           };


       }



       //chek if we can return change
        public void returnChange(){
                if (change>0 || change != null){
                    try {
                        cashRegister.calculateChange(change);
                    }catch (Exception e){
                        e.printStackTrace();
                        return;
                    }
                }

        }

        public void outboundProduct(){
                try{
                    storage.getFromStorage(p);
                    cashRegister.insertToCashRegister(payedCoins);
                }catch (Exception e){
                    e.printStackTrace();
                }

        }








}
