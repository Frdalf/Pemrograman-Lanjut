import java.util.Scanner;

public class Main {

    // Custom checked exception
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    // Validator terpisah agar rapi & mudah diuji
    static void validateAge(int age) throws InvalidAgeException {
        if (age <= 0) {
            throw new InvalidAgeException("Usia harus lebih dari 0.");
        }
        if (age >= 120) {
            throw new InvalidAgeException("Usia harus kurang dari 120.");
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Masukkan usia Anda: ");
            String raw = sc.nextLine().trim();

            int age;
            try {
                age = Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                // Lempar custom exception jika bukan bilangan bulat
                throw new InvalidAgeException("Input bukan bilangan bulat: \"" + raw + "\"");
            }

            // Lempar InvalidAgeException jika tidak memenuhi batas
            validateAge(age);

            System.out.println("Usia valid: " + age);
        } catch (InvalidAgeException e) {
            // Tangkap dan tampilkan pesan kesalahan yang sesuai
            System.err.println("Kesalahan: " + e.getMessage());
            System.exit(1);
        }
    }
}
