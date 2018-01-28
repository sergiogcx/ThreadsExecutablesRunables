import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class executor {

    private static Stack<String> rss_sources = new Stack<String>();

    private static int count_matches(String input, String key) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){

            lastIndex = input.indexOf(key,lastIndex);

            if(lastIndex != -1){
                count ++;
                lastIndex += key.length();
            }
        }

        return count;
    }

    private static String read_url_string(String url_input_string) throws IOException {
        URL url = new URL(url_input_string);
        Scanner scan = new Scanner(url.openStream());
        String content = new String();
        while (scan.hasNext()) content += scan.nextLine();
        scan.close();

        return content;
    }

    public static void main(String[] args) {

        String[] url_sources = {
                "http://feeds.reuters.com/news/artsculture",
                "http://feeds.reuters.com/reuters/businessNews",
                "http://feeds.reuters.com/reuters/companyNews",
                "http://feeds.reuters.com/reuters/entertainment",
                "http://feeds.reuters.com/reuters/environment",
                "http://feeds.reuters.com/reuters/healthNews",
                "http://feeds.reuters.com/reuters/lifestyle",
                "http://feeds.reuters.com/news/wealth",
                "http://feeds.reuters.com/reuters/MostRead",
                "http://feeds.reuters.com/reuters/oddlyEnoughNews",
                "http://feeds.reuters.com/ReutersPictures",
                "http://feeds.reuters.com/reuters/peopleNews",
                "http://feeds.reuters.com/Reuters/PoliticsNews",
                "http://feeds.reuters.com/reuters/scienceNews",
                "http://feeds.reuters.com/reuters/sportsNews",
                "http://feeds.reuters.com/reuters/technologyNews",
                "http://feeds.reuters.com/reuters/topNews",
                "http://feeds.reuters.com/Reuters/domesticNews",
                "http://feeds.reuters.com/Reuters/worldNews"
        };

        // Now We populate the rss_sources list
        for(String s:url_sources) rss_sources.add(s);


        // Pring output Text
        System.out.println("Program Starts Here, not the threads are executed at this point.");




        // We will create a loop to remove all items from the news stack ...
        while(!rss_sources.empty()) {

            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(() -> {
                if(rss_sources.empty()) {
                    System.out.println("RSS Stack is Now Empty.");
                    return;
                }

                String url = rss_sources.pop();
                String name = Thread.currentThread().getName(); // We get our thread's name
                String data = null;
                int news_count = 0;
                System.out.println("New Thread Running:  " + name + "("+ url +")"); // We signal the thread is created

                try {
                    data = read_url_string(url);
                    news_count = count_matches(data, "<item>");
                    System.out.println("Thread Finished:  " + name + " (" + url + "), total news: " + news_count); // We signal if finishes
                }
                catch (Exception e) {
                    // If there is a problem, we can call the Stack Trace and see which function call the problem originated from
                    System.out.println("Could not read from url ("+url+"): " + e.toString());
                    e.printStackTrace();
                }
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








        // Pring output Text
        System.out.println("Program Finishes, but still waits for the rest of the threads to execute!");

    }


}
