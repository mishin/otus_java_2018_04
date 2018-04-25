package ru.otus.l011;

import java.util.*;

import com.github.javafaker.Faker; //client's generator
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import javax.swing.*;

public class Main {

    private static final int CLIENT_COUNT = 100;
    private static final int MEASURE_COUNT = 10;

    private static void printFaker(Faker faker){
        StringBuffer fakerStringBuf = new StringBuffer();
        fakerStringBuf.append(faker.name().fullName())
                .append(" lives in ")
                .append(faker.address().fullAddress());
        System.out.println(fakerStringBuf);
    }

    public static void main(String... args){

        //List<String> names = Arrays.asList("John", "Adam", "Jane","David","Mike","Dmitry","Samuel");

        //Generation list of client with address
        List<Faker> clients = new ArrayList<>();
        List<String> names  = new ArrayList<>();
        //Collection<String> = new ImmutableSet<>();

        for (int i = 0; i<CLIENT_COUNT; i++) {
            Faker client = new Faker();
            clients.add(client);
            names.add(clients.get(i).name().firstName());
           // printFaker(client);
        }

        for (int i = 0; i<CLIENT_COUNT; i++) {
            Faker client = clients.get(i);
            //printFaker(client);
            //System.out.println(client.name().firstName());
        }

        //collect only unique client's name
        //ImmutableCollection<String> names_unique = ImmutableSet.copyOf(names).asList();
        calcTime(() -> ImmutableSet.copyOf(names).asList() ,"Uniqueness");

        List<String> reversed_names = new ArrayList<>();


        List<String> reverse_names = new ArrayList<>();
        Collections.shuffle((List<String>)names);
        calcTime(() -> reverse_names.addAll(Lists.reverse((List<String>)names)), "Reverse of list");


    }

    private static void calcTime(Runnable runnable, String... addArgs) {
        long startTime = System.nanoTime();
        for (int i = 0; i < MEASURE_COUNT; i++)
            runnable.run();
        long finishTime = System.nanoTime();
        long timeNs = (finishTime - startTime) / MEASURE_COUNT;
        System.out.println("Operation: " + addArgs[0]);
        System.out.println("Time spent: " + timeNs + "ns (" + timeNs / 1_000_000 + "ms)");
    }
}
