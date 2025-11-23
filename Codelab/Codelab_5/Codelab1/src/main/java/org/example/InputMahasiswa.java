package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InputMahasiswa {
    // Kita akan simpan file di folder resources agar rapi sesuai soal
    private static final String FILE_PATH = "src/main/resources/data_mahasiswa.xlsx";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> namaMahasiswaList = new ArrayList<>();

        // Memuat nama lama jika file sudah ada (agar validasi tetap jalan)
        loadExistingNames(namaMahasiswaList);

        System.out.println("Masukkan data mahasiswa. Ketik 'selesai' pada nama untuk mengakhiri");

        while (true) {
            System.out.print("Masukkan Nama: ");
            String nama = scanner.nextLine();

            if (nama.equalsIgnoreCase("selesai")) {
                break;
            }

            if (nama.isEmpty()) {
                System.out.println("Nama tidak boleh kosong!");
                continue;
            }

            // Validasi Nama Duplikat
            if (namaMahasiswaList.contains(nama)) {
                System.out.println("Nama sudah ada, masukkan nama yang berbeda !");
                continue;
            }

            System.out.print("Masukkan Semester: ");
            int semester = 0;
            try {
                semester = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Semester harus angka!");
                continue;
            }

            System.out.print("Masukkan Mata Kuliah: ");
            String mataKuliah = scanner.nextLine();

            // Simpan ke Excel dan ke List Memori
            saveToExcel(nama, semester, mataKuliah);
            namaMahasiswaList.add(nama);

            System.out.println("Data berhasil disimpan ke dalam file data_mahasiswa.xlsx !\n");
        }

        System.out.println("Terima kasih !");
    }

    private static void saveToExcel(String nama, int semester, String mataKuliah) {
        Workbook workbook;
        Sheet sheet;
        File file = new File(FILE_PATH);

        try {
            // Pastikan folder resources ada
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            if (file.exists()) {
                // Jika file ada, buka dan lanjutkan
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                // Jika file belum ada, buat baru
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Data Mahasiswa");

                // Buat Header
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Nama");
                headerRow.createCell(1).setCellValue("Semester");
                headerRow.createCell(2).setCellValue("Mata Kuliah");
            }

            // Isi Data di Baris Terakhir
            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRowNum + 1);
            row.createCell(0).setCellValue(nama);
            row.createCell(1).setCellValue(semester);
            row.createCell(2).setCellValue(mataKuliah);

            // Simpan File
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
            fos.close();

        } catch (IOException e) {
            System.out.println("Error menyimpan file: " + e.getMessage());
        }
    }

    private static void loadExistingNames(ArrayList<String> list) {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Cell cell = row.getCell(0);
                    if (cell != null) list.add(cell.getStringCellValue());
                }
            }
        } catch (IOException e) {
            // Abaikan error saat load awal
        }
    }
}