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
//            shop.enterQueue(this);
//            System.out.println(getName() + " get Cheese");
            // ждём, когда дойдёт до нас
            synchronized (this){
                shop.enterQueue(this);
                System.out.println(getName() + " get Cheese");
                wait();
            }
            shop.leaveQueue();
            System.out.println(getName() + " cheese");
            synchronized (shop){
                shop.notify();
                System.out.println("Waiting shop not");
            }
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
