public class Product {
    private String name;
    private Float price;

    public Product(String name, Float price){
        this.name = name;
        this.price = price;

    }

    public String getName(){
        return name;
    }

    public Float getPrice(){
        return price;
    }
}
