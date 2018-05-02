package ru.otus.l011;

import java.util.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.github.javafaker.Faker; //client's generator


/**
 * Created by Dmitry.
 *
 * Example for L01.1
 *
 */

public class Main {

    private static boolean debugMode = false;
    private static int clientCount = 20;
    private static int measureCount = 10;

    private static void printClient(Faker faker){
        StringBuilder fakerSB = new StringBuilder();
        fakerSB.append("Profile: ")
                .append(faker.name().fullName())
                .append(" lives in ")
                .append(faker.address().fullAddress());
        System.out.println(fakerSB);
    }

    public static void main(String... args){


        /* Arguments
        1 arg - Debug mode (default is false)
        2 arg - Number of client to be generated
        3 arg - Number of iteration to estimate
        */

        if (args.length > 0) {
            debugMode = args[0].equalsIgnoreCase("debug") ? true: false;
            try {
                // Parse the string argument into an integer value.
                clientCount = Integer.parseInt(args[1]);
                measureCount = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
                System.out.println("The 2nd and 3rd argument must be an integer.");
                System.exit(1);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Program args: ")
                .append(" debugMode: ").append(debugMode)
                .append(" clientCount: ").append(clientCount)
                .append(" measureCount: ").append(measureCount);
        System.out.println(stringBuilder);

        //Generation list of client with address
        List<Faker> clients = new ArrayList<>();
        List<String> names  = new ArrayList<>();

        for (int i = 0; i<clientCount; i++) {
            Faker client = new Faker();
            clients.add(client);
            names.add(clients.get(i).name().firstName());
            if (debugMode) { printClient(client);}
        }

        StringBuilder sb = new StringBuilder();
        String str = String.join(", ", names);
        sb.append("All of names: ")
                .append(str);
        System.out.print(sb);

        System.out.print("Operations: ");
        //estimate the operation called collect only unique client's name
        calcTime(() -> ImmutableSet.copyOf(names).asList() ,"Uniqueness");

        //estimate the operation called reverse name
        List<String> reverse_names = new ArrayList<>();
        Collections.shuffle((List<String>)names);
        calcTime(() -> reverse_names.addAll(Lists.reverse((List<String>)names)), "Reverse of list");

    }

    private static void calcTime(Runnable runnable, String... addArgs) {
        long startTime = System.nanoTime();
        for (int i = 0; i < measureCount; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / measureCount;

        System.out.println();
        System.out.println("Operation: " + addArgs[0]);
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
