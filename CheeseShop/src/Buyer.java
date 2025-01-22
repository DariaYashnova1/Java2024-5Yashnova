abstract class Buyer extends Thread {
    protected final CheeseShop shop;

    public Buyer(CheeseShop shop, String name) {
        super(name);
        this.shop = shop;
    }

    @Override
    public void run() {
        try {
            // зайти в очередь
            // ждём, когда дойдёт до нас
            synchronized (this){
                wait();
            }
            this.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


class BraveBuyer extends Buyer {
    public BraveBuyer(CheeseShop shop, String name) {
        super(shop, name);
    }
}

class HumbleBuyer extends Buyer {
    public HumbleBuyer(CheeseShop shop, String name) {
        super(shop, name);
    }
}
