package com.muk.cmpletablefuture;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompletableFutureTaskChaining {

    private  static Map<Integer, String> nameMap = new HashMap<>();

    static  {
        nameMap.put(1, "Mukul");
        nameMap.put(2, "Alok");
        nameMap.put(3, "Satpal");
        nameMap.put(4, "Sheldon");
        nameMap.put(5, "Raj");
        nameMap.put(6, "Richard");
        nameMap.put(7, "Gillfoyle");
        nameMap.put(8, "Chris");
        nameMap.put(9, "Bruno");
        nameMap.put(10, "Nikhil");
    }

    private static List<Integer> getUserIds() {
        return Arrays.asList(new Integer [] {1,2,3,4,5,6,7,8,9,10});
    }

    private static List<Integer> getUserIds8() {
        return Arrays.stream(new Integer [] {1,2,3,4,5,6,7,8,9,10}).collect(Collectors.toList());
    }

    private static List<String> getUserNames(List<Integer> ids) {
        Function<Integer, String> getNameFunction = (id) -> nameMap.getOrDefault(id, "Mukul");
        return ids.parallelStream().map(id -> getUserName(id)).collect(Collectors.toList());
    }

    private static String getUserName(Integer id) {
        return nameMap.getOrDefault(id, "Mukul");
    }

    public static void main(String ... args) {

        CompletableFuture<List<Integer>> userIds = CompletableFuture.supplyAsync(() -> getUserIds());
        // The below line is same as that of the Future interface get method. So it waits for the result of IDs call to return.
        // By commenting this line we are saying that do it asynchronously and when the result of userIds as a whole is available then
        // run the Function to get the String user names on them.
        // userIds.join()

        //Now important point to note here is that we are applying getUserNames Functtional interface here which accepts a List<Integer> and retturns
        // List<String> as whole for those Ids.
        // Note we did not use the single id lookup here as we were interseted in getting all names.
        // Sinnce we are using thenApplyAsync method itt also returns a CompletableFuture and this call is also not synchronous.
        //
        // CompletableFuture<String> names = userIds.thenApplyAsync(integers ->getUserNames(integers));
        // is the equivalent of the below line.
        userIds.thenApplyAsync(integers ->getUserNames(integers)).thenAccept(System.out::println);

        // What if we use the thenAppply here instead of the thenApplyAsync . what happens then.Which thread would be running the thenAccept and
        // getUserNames methods
        // Prints the following on console

        /*ForkJoinPool.commonPool-worker-2
        [Mukul, Alok, Satpal, Sheldon, Raj, Richard, Gillfoyle, Chris, Bruno, Nikhil]
        [Mukul, Alok, Satpal, Sheldon, Raj, Richard, Gillfoyle, Chris, Bruno, Nikhil]*/
        userIds.thenApplyAsync(integers ->getUserNames(integers)).thenAccept((ids) -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(ids);
        });

        // Now lets change to thenapply method
        // Outputt of the complete program is, this reflects that thenApply executes tasks synchronously.

        /*ForkJoinPool.commonPool-worker-2
        [Mukul, Alok, Satpal, Sheldon, Raj, Richard, Gillfoyle, Chris, Bruno, Nikhil]
        [Mukul, Alok, Satpal, Sheldon, Raj, Richard, Gillfoyle, Chris, Bruno, Nikhil]
        using thenApply method, executing in main method
         main
        [Mukul, Alok, Satpal, Sheldon, Raj, Richard, Gillfoyle, Chris, Bruno, Nikhil]*/


        userIds.thenApply(integers -> getUserNames(integers)).thenAccept((ids) -> {
            System.out.println("using thenApply method, executing in main method");
            System.out.println(Thread.currentThread().getName());
            System.out.println(ids);
        });



        try {
            //If we don't put a sleep here main thread exists every time and nothing gets printed.
            // As Compleetable future uses fork join pool.
            Thread.sleep(500);
        }catch (InterruptedException ex ) {
            System.out.println(ex.getLocalizedMessage());
        }

    }
}
