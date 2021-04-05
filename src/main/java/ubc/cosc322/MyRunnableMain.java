package ubc.cosc322;

public class MyRunnableMain {

    public static void main(String[] args) {
        TimerTest myRunnable = new TimerTest();

        Thread thread = new Thread(myRunnable);
        System.out.println("Timer start");
        try {
            Thread.sleep(10L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.start();
        System.out.println("started");
        try {
            Thread.sleep(10L * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myRunnable.doStop();
        System.out.println("Timer Stop!");
    }
}