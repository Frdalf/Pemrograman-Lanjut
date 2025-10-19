import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void validateNumber(double number) throws InvalidNumberException {
        if (number <= 0) {
            // Lemparkan exception jika angka adalah nol atau negatif
            throw new InvalidNumberException("Error: Angka yang dimasukkan harus positif.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Masukkan sebuah angka positif: ");
            double userInput = scanner.nextDouble(); // Baca input sebagai double

            // Panggil metode validasi
            validateNumber(userInput);

            // Pesan ini hanya akan muncul jika tidak ada exception yang dilempar
            System.out.println("Input valid! Angka yang Anda masukkan adalah: " + userInput);

        } catch (InvalidNumberException e) {
            // Menangkap custom exception kita
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            // Menangkap jika input bukan angka (misal: teks)
            System.out.println("Error: Input yang dimasukkan harus berupa angka.");
        } finally {
            // Selalu tutup scanner untuk menghindari kebocoran sumber daya
            scanner.close();
        }
    }
}