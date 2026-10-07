import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(Main.class.getResourceAsStream("registrations.txt"));
        Set<String> regis = new LinkedHashSet<>();
        int duplicates = 0;

        while (sc.hasNextLine()) {
            String SID = sc.nextLine();
            if (SID.isEmpty()) continue;

            if (regis.contains(SID)) {
                duplicates++;
            } else {
                regis.add(SID);
            }
        }
        sc.close();
        
        System.out.println("===== Event Check-In Results =====");
        Scanner fajar = new Scanner(Main.class.getResourceAsStream("checkins.txt"));

        Set<String> attend = new LinkedHashSet<>();
        int rejected = 0;

        while (fajar.hasNextLine()) {
            String ID = fajar.nextLine();
            if(regis.contains(ID)) {
                if (attend.contains(ID)) {
                    rejected++;
                    System.out.println(ID + ": Rejected (already checked in)");
                } else {
                    attend.add(ID);
                    System.out.println(ID + ": Checked in");
                };
            }
            else {
                System.out.println(ID + ": Rejected (not registered)");
                rejected++;
            };
        }
        fajar.close();
        
        System.out.println();
        System.out.println("===== Final Event Summary =====");
        System.out.println("Registered students: " + regis.size());
        System.out.println("Successful check-ins: " + attend.size());
        System.out.println("Absent students: " + (regis.size() - attend.size()));
        System.out.println("Rejected attempts: " + rejected);
    
    }
}
