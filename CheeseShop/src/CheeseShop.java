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

    public  boolean chBoolInv() throws InterruptedException {
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

    public  void checkInv() throws InterruptedException {
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
            this.getCheese(newB);
        }
        if (humbles < 2) {
            String name = "Humble " + (int) (Math.random() * 100);
            HumbleBuyer newH = new HumbleBuyer(this, name);
            newH.start();
            this.getCheese(newH);

        }

    }


    public void getCheese(Buyer buyer) throws InterruptedException {
        if(!emtyPlaces()){
            resizeQueue();
        }
        queue.put(buyer);
        buyer.start();
        System.out.println(buyer.getName() + " get Cheese");

        printQueue();
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
        //System.out.println(queue.size());

    }

    public void serve() throws InterruptedException {
        Buyer buyer = queue.take();
        moveBraves();
        //Thread.sleep((long) (Math.random() * 2000)); // Имитация времени обслуживания

        System.out.println(buyer.getName() + " cheese");
        synchronized (buyer) {
            buyer.notify();
        }
        if(!chBoolInv()){
            checkInv();
        }

    }
    public boolean emtyPlaces(){
        return queue.size()<buyers_num;
    }

    public void resizeQueue(){
        BlockingQueue<Buyer> newQueue = new ArrayBlockingQueue<>(buyers_num+1); // Новый размер
        queue.drainTo(newQueue);
        buyers_num = buyers_num+1;
        queue = newQueue;
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

