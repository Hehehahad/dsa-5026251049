package src.lw01.unguided;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;

public class Main {
    public static void main(String [] args){
        List<WashService> washServices = new ArrayList<>();
        InputStream inputFile = Main.class.getResourceAsStream("src/lw01/unguided/washes.txt");
        try (Scanner scanner = new Scanner(inputFile)) {
            int record = scanner.nextInt();
            for (int i = 0; i < record; i++) {
                String type = scanner.next();
                String id = scanner.next();
                int days = scanner.nextInt();
                if (type.equals("CarWash")) {
                    washServices.add(new CarWash(id, days));
                } else if (type.equals("MotorcycleWash")) {
                    washServices.add(new MotorcycleWash(id, days));
                }
            }
            scanner.close();

            for (WashService service : washServices) {
                System.out.println(service.summary());
            }
          


    }
    
}
}