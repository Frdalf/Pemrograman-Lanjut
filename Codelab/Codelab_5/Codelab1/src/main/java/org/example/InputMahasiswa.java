package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Kelas InputMahasiswa berfungsi untuk:
 * <ul>
 *     <li>Mengambil input data mahasiswa dari user melalui console</li>
 *     <li>Menyimpan data ke file Excel (.xlsx) menggunakan library Apache POI</li>
 *     <li>Melakukan validasi nama agar tidak terjadi duplikasi</li>
 * </ul>
 *
 * Struktur data yang disimpan:
 * <ol>
 *     <li>Nama</li>
 *     <li>Semester</li>
 *     <li>Mata Kuliah</li>
 * </ol>
 *
 * File Excel disimpan di path: {@code src/main/resources/data_mahasiswa.xlsx}
 */
public class InputMahasiswa {

    /**
     * Lokasi file Excel tempat data mahasiswa disimpan.
     * Diletakkan di folder resources agar struktur proyek tetap rapi.
     */
    private static final String FILE_PATH = "src/main/resources/data_mahasiswa.xlsx";

    /**
     * Metode utama (entry point) program.
     * <p>
     * Alur utama:
     * <ol>
     *     <li>Memuat nama-nama mahasiswa yang sudah tersimpan sebelumnya dari file Excel (jika ada)</li>
     *     <li>Meminta input data mahasiswa (nama, semester, mata kuliah) dari user</li>
     *     <li>Melakukan validasi:
     *         <ul>
     *             <li>Nama tidak boleh kosong</li>
     *             <li>Nama tidak boleh duplikat (sudah pernah diinput)</li>
     *             <li>Semester harus berupa angka (integer)</li>
     *         </ul>
     *     </li>
     *     <li>Menyimpan data yang valid ke file Excel</li>
     *     <li>Pengguna dapat menghentikan input dengan mengetikkan "selesai" pada kolom nama</li>
     * </ol>
     *
     * @param args argumen baris perintah (tidak digunakan dalam program ini)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // List untuk menyimpan nama-nama mahasiswa yang sudah pernah dimasukkan
        // digunakan untuk validasi agar tidak ada nama duplikat
        ArrayList<String> namaMahasiswaList = new ArrayList<>();

        // Memuat nama lama jika file sudah ada (agar validasi tetap jalan)
        loadExistingNames(namaMahasiswaList);

        System.out.println("Masukkan data mahasiswa. Ketik 'selesai' pada nama untuk mengakhiri");

        while (true) {
            System.out.print("Masukkan Nama: ");
            String nama = scanner.nextLine();

            // Jika user mengetik "selesai", keluar dari loop input
            if (nama.equalsIgnoreCase("selesai")) {
                break;
            }

            // Validasi nama tidak boleh kosong
            if (nama.isEmpty()) {
                System.out.println("Nama tidak boleh kosong!");
                continue;
            }

            // Validasi nama duplikat berdasarkan data yang sudah ada di list
            if (namaMahasiswaList.contains(nama)) {
                System.out.println("Nama sudah ada, masukkan nama yang berbeda !");
                continue;
            }

            System.out.print("Masukkan Semester: ");
            int semester;
            try {
                // Mengubah input semester menjadi integer
                semester = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                // Jika input bukan angka, tampilkan pesan error dan ulangi input
                System.out.println("Semester harus angka!");
                continue;
            }

            System.out.print("Masukkan Mata Kuliah: ");
            String mataKuliah = scanner.nextLine();

            // Simpan ke Excel dan ke List Memori
            saveToExcel(nama, semester, mataKuliah);
            namaMahasiswaList.add(nama); // Simpan nama ke list untuk validasi berikutnya

            System.out.println("Data berhasil disimpan ke dalam file data_mahasiswa.xlsx !\n");
        }

        System.out.println("Terima kasih !");
    }

    /**
     * Menyimpan satu record data mahasiswa ke dalam file Excel.
     * <p>
     * Jika file belum ada:
     * <ul>
     *     <li>Membuat workbook dan sheet baru dengan nama "Data Mahasiswa"</li>
     *     <li>Membuat baris header (Nama, Semester, Mata Kuliah)</li>
     * </ul>
     * Jika file sudah ada:
     * <ul>
     *     <li>Membuka file dan menambahkan data pada baris terakhir</li>
     * </ul>
     *
     * @param nama       nama mahasiswa
     * @param semester   semester mahasiswa (dalam bentuk angka)
     * @param mataKuliah nama mata kuliah yang diambil
     */
    private static void saveToExcel(String nama, int semester, String mataKuliah) {
        Workbook workbook;
        Sheet sheet;
        File file = new File(FILE_PATH);

        try {
            // Pastikan folder resources ada. Jika belum ada, otomatis dibuat.
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                // Jika file sudah ada, buka workbook yang sudah ada
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0); // Ambil sheet pertama
                fis.close();
            } else {
                // Jika file belum ada, buat workbook dan sheet baru
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Data Mahasiswa");

                // Buat baris header di baris pertama (index 0)
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nama");
                headerRow.createCell(1).setCellValue("Semester");
                headerRow.createCell(2).setCellValue("Mata Kuliah");
            }

            // Menentukan baris terakhir yang sudah terisi
            int lastRowNum = sheet.getLastRowNum();

            // Membuat baris baru setelah baris terakhir
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(nama);
            row.createCell(1).setCellValue(semester);
            row.createCell(2).setCellValue(mataKuliah);

            // Simpan perubahan ke file fisik
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();

        } catch (IOException e) {
            System.out.println("Error menyimpan file: " + e.getMessage());
        }
    }

    /**
     * Memuat nama-nama mahasiswa yang sudah tersimpan sebelumnya pada file Excel
     * dan menambahkannya ke dalam list yang diberikan sebagai parameter.
     * <p>
     * Metode ini digunakan untuk:
     * <ul>
     *     <li>Menjaga konsistensi data</li>
     *     <li>Mencegah input nama yang sama (duplikat) pada saat program dijalankan ulang</li>
     * </ul>
     *
     * @param list list yang akan diisi dengan nama-nama mahasiswa yang sudah ada pada file Excel
     */
    private static void loadExistingNames(ArrayList<String> list) {
        File file = new File(FILE_PATH);

        // Jika file belum ada, tidak ada data yang perlu dimuat
        if (!file.exists()) return;

        // try-with-resources akan otomatis menutup FileInputStream dan Workbook
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Mulai dari baris 1 karena baris 0 adalah header
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    // Ambil sel pertama (kolom Nama)
                    Cell cell = row.getCell(0);
                    if (cell != null) {
                        list.add(cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            // Jika terjadi error saat load awal, diabaikan agar program tetap bisa berjalan
            // Misalnya file sedang dikunci atau rusak.
        }
    }
}
