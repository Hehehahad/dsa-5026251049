import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    // ===== Problem 1: List =====
    private static void problem1() {
        System.out.println("===== Problem 1 =====");
        Scanner sc = new Scanner(Main.class.getResourceAsStream("playlist.txt"));

        List<String> playlist = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("ADD ")) {
                String song = line.substring(4);
                playlist.add(song);
            } else if (line.startsWith("INSERT ")) {
                // format: INSERT <INDEX> <SONG>
                String[] parts = line.split(" ", 3);
                int index = Integer.parseInt(parts[1]);
                String song = parts[2];
                playlist.add(index, song);
            } else if (line.startsWith("REMOVE ")) {
                String song = line.substring(7);
                // remove(Object) hanya menghapus kemunculan pertama
                playlist.remove(song);
            }
        }
        sc.close();

        System.out.println("Total songs: " + playlist.size());
        for (int i = 0; i < playlist.size(); i++) {
            System.out.println((i + 1) + ": " + playlist.get(i));
        }
    }

    // ===== Problem 2: Set =====
    private static void problem2() {
        System.out.println("===== Problem 2 =====");
        Scanner sc = new Scanner(Main.class.getResourceAsStream("participants.txt"));

        // LinkedHashSet menjaga urutan kemunculan pertama
        Set<String> participants = new LinkedHashSet<>();
        int duplicates = 0;

        while (sc.hasNextLine()) {
            String name = sc.nextLine().trim();
            if (name.isEmpty()) continue;

            if (participants.contains(name)) {
                duplicates++;
            } else {
                participants.add(name);
            }
        }
        sc.close();

        System.out.println("Unique participants: " + participants.size());
        int no = 1;
        for (String name : participants) {
            System.out.println(no + ". " + name);
            no++;
        }
        System.out.println("Duplicate registrations: " + duplicates);
    }

    // ===== Problem 3: Map =====
    private static void problem3() {
        System.out.println("===== Problem 3 =====");
        Scanner sc = new Scanner(Main.class.getResourceAsStream("inventory.txt"));

        // LinkedHashMap menjaga urutan produk pertama kali muncul
        Map<String, Integer> stock = new LinkedHashMap<>();
        int failedSales = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            // format: <TYPE> <PRODUCT> <QUANTITY>
            String[] parts = line.split(" ");
            String type = parts[0];
            String product = parts[1];
            int qty = Integer.parseInt(parts[2]);

            if (type.equals("ADD")) {
                if (stock.containsKey(product)) {
                    stock.put(product, stock.get(product) + qty);
                } else {
                    stock.put(product, qty);
                }
            } else if (type.equals("SELL")) {
                if (stock.containsKey(product) && stock.get(product) >= qty) {
                    stock.put(product, stock.get(product) - qty);
                } else {
                    failedSales++;
                }
            }
        }
        sc.close();

        for (String product : stock.keySet()) {
            System.out.println(product + ": " + stock.get(product));
        }
        System.out.println("Failed sales: " + failedSales);
    }

    public static void main(String[] args) {
        problem1();
        System.out.println();
        problem2();
        System.out.println();
        problem3();
    }
}