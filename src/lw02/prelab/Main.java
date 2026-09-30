package src.lw02.prelab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
 
public class Main {
 
    public static void main(String[] args) {
 
        // ============================================================
        // LANGKAH 1: Membaca dan menyimpan semua transaksi dari transactions.txt
        // ============================================================
        // Setiap transaksi disimpan sebagai String[]{nama, tipe, jumlah}
        // menggunakan LinkedList, sesuai urutan asli di dalam file.
        LinkedList<String[]> transactionList = new LinkedList<>();
 
        String fileName = "transactions.txt";
        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue; // lewati baris kosong
 
                String[] parts = line.split("\\s+");
                if (parts.length != 3) continue; // lewati baris yang formatnya salah
 
                // parts[0] = nama, parts[1] = tipe (DEPOSIT/WITHDRAW), parts[2] = jumlah
                transactionList.add(parts);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File '" + fileName + "' tidak ditemukan.");
            return;
        }
 
        // ============================================================
        // LANGKAH 2: Membuat data customer (nama + saldo) dalam LinkedList
        // ============================================================
        // customerList menyimpan urutan sesuai kemunculan pertama tiap nama
        // di data transaksi (tidak boleh ada nama customer yang duplikat).
        //
        // customerIndex adalah HashMap<nama, String[]> yang menunjuk ke
        // OBJEK ARRAY YANG SAMA dengan yang ada di customerList.
        // Ini BUKAN struktur data pengganti LinkedList, melainkan hanya
        // "indeks bantu" agar pencarian saldo customer bisa O(1),
        // bukan harus scan seluruh LinkedList (O(n)) setiap kali ada
        // transaksi baru. Dengan cara ini, total proses seluruh
        // transaksi tetap O(n), bukan O(n * m).
        LinkedList<String[]> customerList = new LinkedList<>();
        Map<String, String[]> customerIndex = new HashMap<>();
 
        for (String[] tx : transactionList) {
            String name = tx[0];
            if (!customerIndex.containsKey(name)) {
                // Customer baru ditemukan -> saldo awal = 0
                String[] customer = new String[]{name, "0"};
                customerList.add(customer);
                customerIndex.put(name, customer);
            }
        }
 
        // ============================================================
        // LANGKAH 3: Memindahkan transaksi dari LinkedList ke Queue
        // ============================================================
        // Queue memproses data dengan prinsip FIFO (First In First Out),
        // artinya transaksi yang lebih dulu masuk, lebih dulu diproses.
        Queue<String[]> transactionQueue = new LinkedList<>(transactionList);
 
        // LANGKAH 4: Stack untuk menyimpan transaksi WITHDRAW yang gagal
        // Stack bekerja dengan prinsip LIFO (Last In First Out),
        // artinya transaksi gagal yang paling terakhir dimasukkan,
        // akan menjadi yang pertama dikeluarkan (pop) saat ditampilkan.
        Stack<String[]> failedTransactions = new Stack<>();
 
        String[] tx;
        // poll() akan mengambil sekaligus menghapus elemen paling depan
        // dari Queue. Jika Queue sudah kosong, poll() mengembalikan null,
        // sehingga perulangan berhenti.
        while ((tx = transactionQueue.poll()) != null) {
            String name = tx[0];
            String type = tx[1];
            long amount = Long.parseLong(tx[2]);
 
            // Cari data customer terkait lewat HashMap (cepat, O(1))
            String[] customer = customerIndex.get(name);
            long balance = Long.parseLong(customer[1]);
 
            if (type.equalsIgnoreCase("DEPOSIT")) {
                // DEPOSIT selalu berhasil -> tambahkan ke saldo
                balance += amount;
                customer[1] = String.valueOf(balance);
 
            } else if (type.equalsIgnoreCase("WITHDRAW")) {
                if (amount > balance) {
                    // Saldo tidak cukup -> transaksi gagal,
                    // dimasukkan (push) ke Stack, saldo TIDAK berubah
                    failedTransactions.push(tx);
                } else {
                    // Saldo cukup -> kurangi saldo customer
                    balance -= amount;
                    customer[1] = String.valueOf(balance);
                }
            }
        }
 
        // ============================================================
        // LANGKAH 5: Menampilkan saldo akhir setiap customer
        // ============================================================
        System.out.println("=== Final Balances ===");
        for (String[] customer : customerList) {
            System.out.println(customer[0] + " : " + customer[1]);
        }
 
        System.out.println();
        System.out.println("=== Failed Transactions ===");
        // pop() mengeluarkan elemen paling atas Stack (LIFO),
        // sehingga transaksi gagal yang paling terakhir terjadi
        // akan ditampilkan lebih dulu.
        while (!failedTransactions.isEmpty()) {
            String[] failed = failedTransactions.pop();
            System.out.println(failed[0] + " " + failed[1] + " " + failed[2]);
        }
    }
}