import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // 1. Menerima Input Nama
            System.out.print("Masukkan Nama Mahasiswa: ");
            String nama = scanner.nextLine();

            // 3. Validasi Nama Kosong
            if (nama.trim().isEmpty()) {
                System.out.println("\nError: Nama mahasiswa tidak boleh kosong.");
                return; // Menghentikan program
            }

            // 2. Menerima Input Nilai
            System.out.print("Masukkan Nilai Ujian Akhir: ");
            int nilai = scanner.nextInt();

            // 4. Validasi Rentang Nilai
            if (nilai < 0 || nilai > 100) {
                System.out.println("\nError: Nilai harus dalam rentang 0 sampai 100.");
                return; // Menghentikan program
            }

            String status;

            // 5. Proses Penentuan Status
            if (nilai >= 60) {
                status = "Lulus";
            } else {
                status = "Tidak Lulus";
            }

            // 6. Menampilkan Output
            System.out.println("\n--- Hasil Kelulusan ---");
            System.out.println("Nama Mahasiswa: " + nama);
            System.out.println("Status: " + status);

        } catch (InputMismatchException e) {
            System.out.println("\nError: Input nilai tidak valid, harap masukkan angka.");
        } finally {
            scanner.close();
        }
    }
}

/*
Deskripsi Formal
P: { Input nama adalah String dan nilai adalah Integer }

C: Input(nama, nilai);
   if (nama.trim().isEmpty()) then
       Output("Error: Nama mahasiswa tidak boleh kosong.");
       stop;
   else if (nilai < 0 ∨ nilai > 100) then
       Output("Error: Nilai harus dalam rentang 0 sampai 100.");
       stop;
   else
       if (nilai ≥ 60) then
           status ← "Lulus"
       else
           status ← "Tidak Lulus";
       Output("Nama Mahasiswa: " + nama, "Status: " + status);

Q: { (nama.trim().isEmpty() ⇒ program berhenti dengan pesan error) ∧
     ( (nilai < 0 ∨ nilai > 100) ⇒ program berhenti dengan pesan error) ∧
     ( status = "Lulus" ⇔ (nilai ≥ 60 ∧ nilai ≤ 100) ) ∧
     ( status = "Tidak Lulus" ⇔ (nilai ≥ 0 ∧ nilai < 60) ) }
*/

/*
Deskripsi Informal
1. Program meminta pengguna untuk memasukkan nama mahasiswa.
2. Program meminta pengguna untuk memasukkan nilai ujian akhir.
3. Program memeriksa apakah input nama kosong (setelah spasi di awal/akhir dihilangkan).
    - Jika kosong, program akan menampilkan pesan error "Nama mahasiswa tidak boleh kosong" lalu berhenti.
4. Jika nama tidak kosong, program memeriksa apakah nilai berada di luar rentang 0–100.
    - Jika di luar rentang, program akan menampilkan pesan error "Nilai harus dalam rentang 0 sampai 100" lalu berhenti.
5. Jika nilai valid, program menentukan status kelulusan:
    - Jika nilai ujian akhir ≥ 60, maka status mahasiswa adalah "Lulus".
    - Jika nilai ujian akhir < 60, maka status mahasiswa adalah "Tidak Lulus".
6. Program menampilkan nama mahasiswa beserta status kelulusannya.
*/