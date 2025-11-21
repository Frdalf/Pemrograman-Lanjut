import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// 1. Membuat class Film untuk merepresentasikan data (Objek)
class Film {
    private String judul;
    private int tahun;

    public Film(String judul, int tahun) {
        this.judul = judul;
        this.tahun = tahun;
    }

    public String getJudul() {
        return judul;
    }

    public int getTahun() {
        return tahun;
    }

    // Method toString untuk memudahkan menampilkan output sesuai format soal
    @Override
    public String toString() {
        return "Judul: " + judul + ", Tahun: " + tahun;
    }
}

public class Main {
    public static void main(String[] args) {
        // CLUE 1: Menggunakan ArrayList untuk menyimpan data film
        ArrayList<Film> daftarFilm = new ArrayList<>();

        // === TAMBAHAN: DATA FILM BAWAAN (INITIAL DATA) ===
        // Data ini akan langsung muncul saat program dijalankan
        daftarFilm.add(new Film("Joker", 2019));
        daftarFilm.add(new Film("Avengers: Endgame", 2019));
        daftarFilm.add(new Film("Ada Apa Dengan Cinta 2", 2016));
        daftarFilm.add(new Film("Spiderman: No Way Home", 2021));

        Scanner scanner = new Scanner(System.in);
        int pilihan = 0;

        do {
            System.out.println("\n=== Menu Manajemen Film ===");
            System.out.println("1. Tambah Film Baru");
            System.out.println("2. Urutkan Berdasarkan Nama Film (A-Z)");
            System.out.println("3. Urutkan Berdasarkan Tahun (Ascending)");
            System.out.println("4. Keluar Program");
            System.out.print("Masukkan pilihan (1-4): ");

            // Validasi input agar program tidak error jika user memasukkan bukan angka
            try {
                pilihan = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
                continue;
            }

            switch (pilihan) {
                case 1:
                    // Fitur Tambah Film
                    System.out.print("Masukkan judul film: ");
                    String judul = scanner.nextLine();

                    System.out.print("Masukkan tahun rilis: ");
                    int tahun = 0;
                    try {
                        tahun = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Tahun harus angka! Data gagal ditambahkan.");
                        break;
                    }

                    daftarFilm.add(new Film(judul, tahun));
                    System.out.println("Film berhasil ditambahkan.");
                    break;

                case 2:
                    // CLUE 2 & 3: Menggunakan Collections dan Comparator untuk Nama
                    if (daftarFilm.isEmpty()) {
                        System.out.println("Daftar film kosong.");
                    } else {
                        Collections.sort(daftarFilm, new Comparator<Film>() {
                            @Override
                            public int compare(Film f1, Film f2) {
                                // Mengurutkan string (A-Z) mengabaikan huruf besar/kecil
                                return f1.getJudul().compareToIgnoreCase(f2.getJudul());
                            }
                        });

                        System.out.println("\n=== Daftar Film (Urut Nama A-Z) ===");
                        for (Film f : daftarFilm) {
                            System.out.println(f);
                        }
                    }
                    break;

                case 3:
                    // CLUE 2 & 3: Menggunakan Collections dan Comparator untuk Tahun
                    if (daftarFilm.isEmpty()) {
                        System.out.println("Daftar film kosong.");
                    } else {
                        Collections.sort(daftarFilm, new Comparator<Film>() {
                            @Override
                            public int compare(Film f1, Film f2) {
                                // Mengurutkan integer (Ascending)
                                return Integer.compare(f1.getTahun(), f2.getTahun());
                            }
                        });

                        System.out.println("\n=== Daftar Film (Urut Tahun Ascending) ===");
                        for (Film f : daftarFilm) {
                            System.out.println(f);
                        }
                    }
                    break;

                case 4:
                    System.out.println("Keluar dari program.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 4);

        scanner.close();
    }
}