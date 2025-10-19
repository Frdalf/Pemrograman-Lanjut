import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        try {
            System.out.print("Masukkan jumlah angka: ");
            n = scanner.nextInt();

            // Menangani jika pengguna memasukkan angka negatif atau nol
            if (n <= 0) {
                System.out.println("Jumlah angka harus lebih dari 0.");
                scanner.close();
                return; // Keluar dari program
            }

            int[] angka = new int[n];
            System.out.println("Masukkan angka-angka:");
            for (int i = 0; i < n; i++) {
                // Perulangan yang sudah diperbaiki untuk menjumlahkan semua elemen array
                // Try-catch di dalam loop untuk setiap masukan angka
                System.out.print("Angka ke-" + (i + 1) + ": ");
                angka[i] = scanner.nextInt();
            }

            int total = 0;
            for (int satuAngka : angka) {
                total += satuAngka;
            }

            double rataRata = (double) total / n;
            System.out.println("Rata-rata adalah: " + rataRata);

        } catch (InputMismatchException e) {
            // ini akan dieksekusi jika input bukan integer
            System.out.println("Input invalid");
        } finally {
            // Blok finally akan selalu dieksekusi, baik ada error maupun tidak, untuk menutup scanner
            scanner.close();
        }
    }
}