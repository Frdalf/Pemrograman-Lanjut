import java.util.Scanner;
import java.lang.String;
import java.lang.StringBuilder;

public class Main {

    public static void main(String[] args) {
        // 1. Setup untuk mengambil input dari user
        Scanner scanner = new Scanner(System.in);

        // 2. Mengambil kalimat pertama dari user
        System.out.print("Masukkan sebuah kalimat: ");
        String kalimat = scanner.nextLine();

        // --- TUGAS 1: Mengambil kata kedua ---
        // Kita pecah kalimat berdasarkan spasi " "
        String[] kataArray = kalimat.split(" ");
        // Ambil elemen ke-1 (indeks kedua, karena indeks dimulai dari 0)
        String kataKedua = kataArray[1];
        System.out.println("Kata kedua : " + kataKedua);

        // --- TUGAS 2: Mengubah seluruh kalimat menjadi huruf kapital ---
        // Menggunakan metode toUpperCase() dari kelas String
        String hurufKapital = kalimat.toUpperCase();
        System.out.println("Huruf Kapital : " + hurufKapital);

        // --- TUGAS 3: Mengecek apakah kalimat mengandung kata "java" ---
        // Kita ubah dulu ke huruf kecil semua agar pencarian tidak case-sensitive
        // "Java" atau "JAVA" akan tetap terdeteksi
        boolean mengandungJava = kalimat.toLowerCase().contains("java");
        System.out.println("Apakah mengandung kata 'java'? : " + mengandungJava);

        // --- TUGAS 4: Menambahkan kata/kalimat baru di akhir ---
        // Mengambil input kedua dari user
        System.out.print("Masukkan kata/kalimat untuk ditambah di akhir: ");
        String tambahan = scanner.nextLine();

        // Menggabungkan string menggunakan operator +
        String kalimatBaru = kalimat + " " + tambahan;
        System.out.println("Setelah menambah kata/kalimat : " + kalimatBaru);

        // --- TUGAS 5: Membalik kalimat yang sudah diubah ---
        // Di sinilah kita menggunakan StringBuilder sesuai petunjuk (clue)
        // 1. Buat objek StringBuilder dari string kalimatBaru
        StringBuilder sb = new StringBuilder(kalimatBaru);

        // 2. Gunakan metode reverse()
        sb.reverse();

        // 3. Ubah kembali menjadi String
        String kalimatTerbalik = sb.toString();
        System.out.println("Kalimat terbalik: " + kalimatTerbalik);

        // 4. Tutup scanner untuk menghindari memory leak
        scanner.close();
    }
}