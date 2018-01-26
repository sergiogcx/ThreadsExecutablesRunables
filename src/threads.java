import java.util.concurrent.TimeUnit;
import java.util.Random;

public class threads {
    public static void main(String[] args) {
        // Pring output Text
        System.out.println("Program Starts Here, notice the threads are created in almost perfect ascending order.");

        /*
            First, we declaring the task runnable object and populate it with the thread name and print lines ...
        */

        Runnable task = () -> {
            try {
                Random r = new Random(); // This will allocate a random object class to generate our wait time
                String name = Thread.currentThread().getName(); // We get our thread's name
                int wait_length_random = r.nextInt(10-1) + 1; // we generate a number between 1-10 (seconds of wait)
                System.out.println("New Thread Running:  " + name); // We signal the thread is created
                TimeUnit.SECONDS.sleep(wait_length_random); // ACTUAL WAIT USING RANDOM VALUE, this simulates random work
                System.out.println("Thread Finished:  " + name + ", after waiting (seconds): " + wait_length_random); // We signal if finishes
            }
            catch (InterruptedException e) {
                // If there is a problem, we can call the Stack Trace and see which function call the problem originated from
                e.printStackTrace();
            }
        };


        // Pring output Text
        System.out.println("Program Starts Here, not the threads are executed at this point.");

        // We will create 200 threads and start them immediately ...
        for(int n = 0; n < 200; n++) {
            // We now instantiate the thread using the runnable object
            Thread thread = new Thread(task);

            // The thread is started and running concurrently ...
            thread.start();
        }

        // Pring output Text
        System.out.println("Program Finishes, but still waits for the rest of the threads to execute!");

    }


}
