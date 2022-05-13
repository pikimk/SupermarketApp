import java.util.HashMap;
import java.util.Map;

public class ProductStorage {

    private Map<Product,Integer> allProducts;

    public ProductStorage(){
        allProducts = new HashMap<>();
        setInitialInventory();

    }

    public void addProduct(Product product,Integer quantity){
        allProducts.put(product,quantity);
    }

    private void setInitialInventory(){
        Product Soda = new Product("SODA",2.3F);
        Product Bread = new Product("BREAD", 1.1F);
        Product Wine = new Product("WINE",2.7F);
        addProduct(Soda,10);
        addProduct(Wine,10);
        addProduct(Bread,10);

    }

    public Map<Product,Integer> getAllProducts(){
        return allProducts;
    }

    public Product getProduct(String name) throws SoldOutException{
        //if it returns Object, product exists, else null
        for (Map.Entry<Product, Integer> entry : allProducts.entrySet()) {
            Product product = entry.getKey();
           if(product.getName().equals(name)){
               return product;
           }
        }
        throw new SoldOutException("Product Not Found!");


    };

    public Integer getQuantity(Product p ){
        return allProducts.get(p);
    }

    public void getFromStorage(Product p) throws SoldOutException{
        Integer quan = allProducts.get(p);
        if(quan > 0){
            allProducts.replace(p,quan -1);
        }else throw new SoldOutException("Item Sold Out!");
    }

    public void setQuantity(Product p, Integer quantity){
        allProducts.replace(p,quantity);
    }

}
