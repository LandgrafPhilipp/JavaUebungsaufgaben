package collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {

    public static void main(String[] args) {
        // Aufgabe 1
        ArrayList<String> namen = new ArrayList<>();
        namen.add("Gnar");
        namen.add("Elise");
        namen.add("Zoe");
        namen.add("Jinx");
        namen.add("Leona");
        namen.add("Leblanc");
        namen.add("Sona");
        namen.add("Zoe");
        namen.add("Anivia");
        namen.add("Karma");
        namen.add("Zed");
        namen.add("Pyke");
        namen.add("Xerath");
        namen.add("Taric");
        namen.add("Pyke");

        namen.sort(String::compareTo);
        for(String name : namen)
            System.out.println(name);

        // Aufgabe 2
        namen.remove("Leblanc");

        // Aufgabe 3
        System.out.println("The size of the class is: " + namen.size());

        // Aufgabe 4
        Queue<String> bakery = new ConcurrentLinkedDeque<>();
        for(int i = namen.size()-5; i < namen.size(); i++)
            bakery.add(namen.get(i));

        while(!bakery.isEmpty())
            System.out.println(bakery.poll() + " is the next in Line");


        // Aufgabe 5
        if(bakery.isEmpty())
            System.out.println(bakery + " Everyone has been served");


        // Aufgabe 6
        HashSet<String> namenHashSet = new HashSet<>();
        namenHashSet.addAll(namen);
        for(String s : namenHashSet)
            System.out.println(s);

        // Aufgabe 7
        TreeMap<String, Integer> phoneNumbers = new TreeMap<>();
        phoneNumbers.put("Gnar", 123456);
        phoneNumbers.put("Elise", 123123);
        phoneNumbers.put("Zoe", 1231233);
        phoneNumbers.put("Jinx", 2139958);
        phoneNumbers.put("Leona", 12301230);
    }

}
