import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class executor {
    public static void main(String[] args) {

        /*
            We first instantiate the executor object, and the thread  it will be running with.
        */

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("New Thread:  " + threadName);
        });


        /*
            We will now attempt to control the execution status
        */
        try {
            // First we attempt to shut it down
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            // If there is a failure it will be caught here
            System.err.println("tasks interrupted");
        }
        finally {
            // The final step, we then check if the executor is not terminated and output a message
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }

            // Finally, we terminate it for good...
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }

    }


}
