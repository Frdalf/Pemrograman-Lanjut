import java.util.Scanner;

public class TemperatureConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Program Konversi Suhu ---");
        System.out.print("Masukkan suhu (misal: 25C, 77F, 300K): ");
        String input = scanner.nextLine().toUpperCase();

        // Ekstrak nilai dan unit
        double value = Double.parseDouble(input.replaceAll("[^\\d.-]", ""));
        String unit = input.replaceAll("[\\d.-]", "");

        double result;
        String fromUnit = "";
        String toUnit = "";

        if (unit.equals("C")) {
            // Konversi dari Celsius ke Fahrenheit
            result = (value * 9 / 5) + 32;
            fromUnit = "Celsius";
            toUnit = "Fahrenheit";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

            // Konversi dari Celsius ke Kelvin
            result = value + 273.15;
            toUnit = "Kelvin";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

        } else if (unit.equals("F")) {
            // Konversi dari Fahrenheit ke Celsius
            result = (value - 32) * 5 / 9;
            fromUnit = "Fahrenheit";
            toUnit = "Celsius";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

            // Konversi dari Fahrenheit ke Kelvin
            result = (value - 32) * 5 / 9 + 273.15;
            toUnit = "Kelvin";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

        } else if (unit.equals("K")) {
            // Konversi dari Kelvin ke Celsius
            result = value - 273.15;
            fromUnit = "Kelvin";
            toUnit = "Celsius";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

            // Konversi dari Kelvin ke Fahrenheit
            result = (value - 273.15) * 9 / 5 + 32;
            toUnit = "Fahrenheit";
            System.out.printf("%.2f %s = %.2f %s\n", value, fromUnit, result, toUnit);

        } else {
            System.out.println("Unit tidak valid. Gunakan C, F, atau K.");
        }

        scanner.close();
    }
}