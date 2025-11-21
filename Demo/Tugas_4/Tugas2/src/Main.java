import java.util.Scanner;
import java.util.Locale;
import java.util.Currency;
import java.util.Date;
import java.text.NumberFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. Input Jumlah Uang
            System.out.print("Masukkan jumlah uang: ");
            double uang = scanner.nextDouble();

            // 2. Input Tanggal
            System.out.print("Masukkan tanggal (dd-MM-yyyy): ");
            String tanggalInput = scanner.next();

            // Parsing string input menjadi object Date
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = inputFormat.parse(tanggalInput);

            System.out.println(); // Baris baru agar rapi

            // --- MENAMPILKAN DATA UNTUK 3 NEGARA (Sesuai Soal) ---

            // NEGARA 1: Indonesia
            Locale localeIndo = new Locale("id", "ID");
            tampilkanInfo(localeIndo, uang, date);

            // NEGARA 2: Jepang
            Locale localeJepang = Locale.JAPAN; // Bisa pakai konstanta atau new Locale("ja", "JP")
            tampilkanInfo(localeJepang, uang, date);

            // NEGARA 3: Amerika Serikat (Sebagai tambahan agar minimal 3)
            Locale localeUS = Locale.US;
            tampilkanInfo(localeUS, uang, date);

        } catch (ParseException e) {
            System.out.println("Format tanggal salah! Gunakan format dd-MM-yyyy (contoh: 10-12-2025)");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan input: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Method bantuan untuk mencetak info agar kodingan di main tidak berulang-ulang
    public static void tampilkanInfo(Locale currentLocale, double uang, Date date) {
        // Mengambil Currency (Mata Uang)
        Currency currency = Currency.getInstance(currentLocale);

        // Memformat Uang (NumberFormat)
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

        // Memformat Tanggal (DateFormat) - Menggunakan style LONG agar nama bulan muncul
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, currentLocale);

        // --- OUTPUT ---
        System.out.println("=== Locale: " + currentLocale.toString() + " ===");
        // Menampilkan nama negara dalam bahasa Inggris (agar sesuai contoh output 'Indonesia', bukan 'Indonesia')
        // Jika ingin nama negara dalam bahasa lokalnya, gunakan .getDisplayCountry(currentLocale)
        System.out.println("Country: " + currentLocale.getDisplayCountry(Locale.ENGLISH));

        System.out.println("Currency Code: " + currency.getCurrencyCode());
        System.out.println("Currency Symbol: " + currency.getSymbol(currentLocale));

        System.out.println("Formatted currency: " + currencyFormatter.format(uang));
        System.out.println("Formatted date: " + dateFormatter.format(date));
        System.out.println(); // Jarak antar negara
    }
}