package src.lw02.unguided;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<String[]> orders = new LinkedList<>();
        LinkedList<String[]> Fstock = new LinkedList<>();
        LinkedList<String[]> Dstock = new LinkedList<>();
        LinkedList<String[]> success = new LinkedList<>();

        Queue<String[]> queue = new LinkedList<>();
        Stack<String[]> failed = new Stack<>();

        Scanner fajar = new Scanner(Main.class.getResourceAsStream("orders.txt"));

        while(fajar.hasNext()){
            String[] order = new String[4];
            order[0] = fajar.next();
            order[1] = fajar.next();
            order[2] = fajar.next();
            order[3] = fajar.next();
            orders.add(order);
        }
        fajar.close();

        Fstock.add(new String[]{"Bakso", "2"});
        Fstock.add(new String[]{"Sate", "1"});
        Fstock.add(new String[]{"Soto", "2"});

        Dstock.add(new String[]{"EsTeh", "4"});
        Dstock.add(new String[]{"EsJeruk", "2"});

        for (int i = 0; i < orders.size(); i++){
            queue.add(orders.get(i));
        }

        while (!queue.isEmpty()){
            String[] order = queue.poll();

            String food = order[1];
            String drink = order[2];

            String[] foodRecord = null;
            if (!food.equals("-")){
                for (int i = 0; i < Fstock.size(); i++){
                    if (Fstock.get(i)[0].equals(food)){
                        foodRecord = Fstock.get(i);
                    }
                }
            }

            String[] drinkRecord = null;
            if (!drink.equals("-")){
                for (int i = 0; i < Dstock.size(); i++){
                    if (Dstock.get(i)[0].equals(drink)){
                        drinkRecord = Dstock.get(i);
                    }
                }
            }

            boolean foodAvailable = true;
            boolean drinkAvailable = true;

            if (!food.equals("-")){
                if (foodRecord == null || Integer.parseInt(foodRecord[1]) <= 0){
                    foodAvailable = false;
                }
            }

            if (!drink.equals("-")){
                if (drinkRecord == null || Integer.parseInt(drinkRecord[1]) <= 0){
                    drinkAvailable = false;
                }
            }

            if (foodAvailable && drinkAvailable){

                if (!food.equals("-")){
                    int stock = Integer.parseInt(foodRecord[1]);
                    stock--;
                    foodRecord[1] = String.valueOf(stock);
                }

                if (!drink.equals("-")){
                    int stock = Integer.parseInt(drinkRecord[1]);
                    stock--;
                    drinkRecord[1] = String.valueOf(stock);
                }

                success.add(order);

            } else {
                failed.push(order);
            }
        }

        System.out.println("=== SUCCESSFULLY PROCESSED ORDERS ===");

        for (int i = 0; i < success.size(); i++){
            String[] order = success.get(i);

            System.out.println(
                order[0] + " " +
                order[1] + " " +
                order[2] + " " +
                order[3]
            );
        }

        System.out.println();

        System.out.println("=== REMAINING FOOD STOCK ===");

        for (int i = 0; i < Fstock.size(); i++){
            System.out.println(
                Fstock.get(i)[0] + ": " +
                Fstock.get(i)[1]
            );
        }

        System.out.println();

        System.out.println("=== REMAINING DRINK STOCK ===");

        for (int i = 0; i < Dstock.size(); i++){
            System.out.println(
                Dstock.get(i)[0] + ": " +
                Dstock.get(i)[1]
            );
        }

        System.out.println();

        System.out.println("=== FAILED ORDERS (LIFO) ===");

        while (!failed.isEmpty()){
            String[] order = failed.pop();

            System.out.println(
                order[0] + " " +
                order[1] + " " +
                order[2] + " " +
                order[3]
            );
        }
    }
}