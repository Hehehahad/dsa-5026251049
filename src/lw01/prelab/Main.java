package src.lw01.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        List<PrintJob> printQueue = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("/jobs.txt"))) {
            while (scanner.hasNext()) {
                String kind = scanner.next();
                String ticket = scanner.next();
                int sheetCount = scanner.nextInt();

                if (kind.equals("MONO")) {
                    printQueue.add(new MonoPrint(ticket, sheetCount));
                } else if (kind.equals("COLOUR")) {
                    printQueue.add(new ColourPrint(ticket, sheetCount));
                }
            }
        }

        for (PrintJob job : printQueue) {
            System.out.println(job.summary());
        }
    }
}    