import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("==== Simple Calculator ====");
        boolean running = true;

        /**
         * Loop utama aplikasi kalkulator
         */
        while (running) {
            printMenu();
            int choice = readInt("Pilih menu [1-5]: ");
            switch (choice) {
                case 1: operate("Penjumlahan", (a, b) -> a + b); break;
                case 2: operate("Pengurangan", (a, b) -> a - b); break;
                case 3: operate("Perkalian", (a, b) -> a * b); break;
                case 4: divide(); break;
                case 5: running = false; break;
                default: System.out.println("Menu tidak valid. Coba lagi.\n");
            }
        }


        System.out.println("Terima kasih! Keluar aplikasi.");
    }
    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Penjumlahan (a + b)");
        System.out.println("2. Pengurangan (a - b)");
        System.out.println("3. Perkalian (a * b)");
        System.out.println("4. Pembagian (a / b)");
        System.out.println("5. Keluar");
    }


    private static void operate(String label, Operation op) {
        double a = readDouble("Masukkan angka pertama: ");
        double b = readDouble("Masukkan angka kedua : ");
        double result = op.apply(a, b);
        System.out.printf("%s => Hasil: %.6f\n", label, result);
    }


    private static void divide() {
        double a = readDouble("Masukkan pembilang (a): ");
        double b = readDouble("Masukkan penyebut (b) : ");
        if (Math.abs(b) < 1e-12) {
            System.out.println("Error: pembagian dengan nol tidak diperbolehkan.");
            return;
        }
        double result = a / b;
        System.out.printf("Pembagian => Hasil: %.6f\n", result);
    }


    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Input harus bilangan bulat. Coba lagi.");
            }
        }
    }


    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Input harus angka (mis. 12 atau 3.14). Coba lagi.");
            }
        }
    }


    // Functional interface sederhana untuk operasi biner
    @FunctionalInterface
    interface Operation {
        double apply(double a, double b);
    }
}