import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Inisialisasi Scanner untuk input pengguna
        Scanner scanner = new Scanner(System.in);

        // Inisialisasi Random untuk generasi angka/indeks acak
        Random rand = new Random();

        int pilihan; // Variabel untuk menyimpan pilihan menu

        // Menggunakan do-while loop agar menu tampil setidaknya satu kali
        do {
            // Menampilkan Menu
            System.out.println("\nMenu:");
            System.out.println("1. Menghasilkan Bilangan Bulat Acak");
            System.out.println("2. Mengambil Karakter Acak dari String");
            System.out.println("3. Keluar");
            System.out.print("Pilihan: ");

            // Membaca pilihan pengguna
            pilihan = scanner.nextInt();
            scanner.nextLine(); // Membersihkan buffer setelah nextInt()

            // Struktur switch-case untuk menangani pilihan
            switch (pilihan) {
                case 1:
                    // Opsi 1: Menghasilkan Bilangan Bulat Acak
                    System.out.print("Masukkan nilai minimum: ");
                    int min = scanner.nextInt();
                    System.out.print("Masukkan nilai maksimum: ");
                    int max = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan buffer lagi

                    // Menghasilkan bilangan acak dalam rentang [min, max]
                    // Rumus: rand.nextInt((max - min) + 1) + min
                    int bilanganAcak = rand.nextInt(max - min + 1) + min;

                    System.out.println("Bilangan bulat acak antara " + min + " dan " + max + ": " + bilanganAcak);
                    break;

                case 2:
                    // Opsi 2: Mengambil Karakter Acak dari String
                    System.out.print("Masukkan sebuah kata/kalimat: ");
                    String inputString = scanner.nextLine();

                    // Menghasilkan indeks acak dari 0 s/d (panjang string - 1)
                    int indeksAcak = rand.nextInt(inputString.length());

                    // Mengambil karakter pada indeks acak tersebut
                    char karakterAcak = inputString.charAt(indeksAcak);

                    System.out.println("Karakter acak dari string: " + karakterAcak);
                    break;

                case 3:
                    // Opsi 3: Keluar
                    System.out.println("Keluar dari program.");
                    break;

                default:
                    // Penanganan jika pilihan tidak valid
                    System.out.println("Pilihan tidak valid. Silakan masukkan 1, 2, atau 3.");
                    break;
            }

        } while (pilihan != 3); // Loop berlanjut selama pilihan bukan 3

        // Menutup scanner
        scanner.close();
    }
}