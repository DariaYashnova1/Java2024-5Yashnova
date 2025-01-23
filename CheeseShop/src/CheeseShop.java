import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

class CheeseShop implements Runnable {
    private BlockingQueue<Buyer> queue;
    int buyers_num ;
    public CheeseShop(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
        buyers_num = capacity;
    }

    public  void enterQueue(Buyer buyer) throws InterruptedException {
        queue.put(buyer);
    }

    public  void leaveQueue() throws InterruptedException {
        queue.take();
    }

    public  boolean chBoolInv() {
        //Thread.sleep(2000);
        int humbles = 0;
        int braves = 0;
        List<Buyer> queueList = new ArrayList<>(queue);
        for (Buyer buyer : queueList) {
            if (buyer instanceof BraveBuyer) {
                braves++;
            } else {
                humbles++;
            }
        }
        return humbles >= 2 && braves >= 2;
    }

    public void checkInv() {
        int humbles = 0;
        int braves = 0;
        List<Buyer> queueList = new ArrayList<>(queue);
        for (Buyer buyer : queueList) {
            if (buyer instanceof BraveBuyer) {
                braves++;
            } else {
                humbles++;
            }
        }
        if (braves < 2) {
            String name = "Brave " + (int) (Math.random() * 100);
            BraveBuyer newB = new BraveBuyer(this, name);
            newB.start();
        }
        if (humbles < 2) {
            String name = "Humble " + (int) (Math.random() * 100);
            HumbleBuyer newH = new HumbleBuyer(this, name);
            newH.start();
        }

    }


    private synchronized void moveBraves() throws InterruptedException {
        List<Buyer> queueList = new ArrayList<>(queue);
        for (int i = 1; i < queueList.size(); i++) {
            if (queueList.get(i) instanceof BraveBuyer && queueList.get(i - 1) instanceof HumbleBuyer) {
                // Меняем местами храброго и смиренного покупателя
                Buyer temp = queueList.get(i);
                queueList.set(i, queueList.get(i - 1));
                queueList.set(i - 1, temp);
            }
        }
        // Очищаем текущую очередь и добавляем обновленные элементы
        queue.clear();
        for (Buyer buyer : queueList) {
            queue.put(buyer);
        }

    }

    public  void printQueue() {
        List<Buyer> queueList = new ArrayList<>(queue);
        System.out.println();
        for (Buyer buyer : queueList) {
            System.out.printf(buyer.getName() + "<-");
        }
        System.out.println();
    }

    public void serve() throws InterruptedException {
        Buyer buyer = queue.peek();
        System.out.println(queue.size());
        if (buyer == null) return;
        synchronized (this){
            synchronized (buyer){
                buyer.notify();
            }
            System.out.println("Waiting shop");
            this.wait();
        }

        moveBraves();
        //Thread.sleep((long) (Math.random() * 2000)); // Имитация времени обслуживания

        if(!chBoolInv()){
            checkInv();
        }
        printQueue();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                serve(); // Обслуживаем покупателя
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Восстанавливаем состояние прерывания
            System.out.println("Cheese shop is closed.");
        }
    }
}

