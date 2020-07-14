public class Main {

    public static void main(String[] args) {

        WorkerThread wt1 = new WorkerThread();
        wt1.setName("worker-1");

        WorkerThread wt2 = new WorkerThread();
        wt2.setName("worker-2");

        wt1.start();
        wt2.start();
    }
}

// Don't change the code below
class WorkerThread extends Thread {

    private static final int numberOfLines = 3;

    @Override
    public void run() {
        final String name = Thread.currentThread().getName();

        if (!name.startsWith("worker-")) {
            return;
        }

        for (int i = 0; i < numberOfLines; i++) {
            System.out.println("do something...");
        }
    }
}