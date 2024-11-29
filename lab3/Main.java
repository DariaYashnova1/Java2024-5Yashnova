import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        CheeseShop shop = new CheeseShop(10);
        Thread shopThread = new Thread(shop);
        for (int i = 0; i < 10; i++) {

            Buyer buyer;
            if (random.nextBoolean()){
                buyer = new HumbleBuyer(shop,"Humble " + i);
            }else{
                buyer = new BraveBuyer(shop,"Brave " + i);
            }
            buyer.start();
        }

        shopThread.start();


    }
}
