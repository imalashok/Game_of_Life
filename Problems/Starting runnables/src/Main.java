class Starter {

    public static void startRunnables(Runnable[] runnables) {
        for (Runnable runnable : runnables) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}