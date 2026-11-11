import java.util.Scanner;

public class Main {

    public static void bubbleSort(int[] arr, int[] copy) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] < arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        

            if (i == 1) {
                for (int k = 0; k < arr.length; k++) {
                    copy[k] = arr[k];
                }
            }
        }
    }


    public static int[] binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int steps = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            steps++;

            if (arr[mid] == target) {
                return new int[]{mid, steps};
            } else if (target > arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return new int[]{-1, steps};
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(Main.class.getResourceAsStream("parcels.txt"));

        int N = input.nextInt();
        int[] parcels = new int[N];
        int[] afterTwo = new int[N];

        for (int i = 0; i < N; i++) {
            parcels[i] = input.nextInt();
        }

        int M = input.nextInt();
        int[] targets = new int[M];

        for (int i = 0; i < M; i++) {
            targets[i] = input.nextInt();
        }

        bubbleSort(parcels, afterTwo);

        System.out.println("==== After 2 Passes ====");

        for (int i = 0; i < N; i++) {
            System.out.print(afterTwo[i]);

            if (i < N - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();

        System.out.println("==== Fully Sorted ====");

        for (int i = 0; i < N; i++) {
            System.out.print(parcels[i]);

            if (i < N - 1) {
                System.out.print(" ");
            }
        }

        System.out.println();

        System.out.println("==== Search Results ====");

        for (int i = 0; i < M; i++) {
            int[] result = binarySearch(parcels, targets[i]);

            if (result[0] != -1) {
                System.out.println(
                    targets[i] + ": FOUND at index " + result[0]
                    + " (steps: " + result[1] + ")"
                );
            } else {
                System.out.println(
                    targets[i] + ": NOT FOUND (steps: " + result[1] + ")"
                );
            }
        }

        input.close();
    }
}

