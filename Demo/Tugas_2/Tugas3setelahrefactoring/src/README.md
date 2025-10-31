# Program Konversi Suhu

Program ini adalah aplikasi konsol Java sederhana yang berfungsi untuk mengkonversi nilai suhu antara tiga unit populer: Celsius (C), Fahrenheit (F), dan Kelvin (K).

Program ini dibuat untuk memenuhi Tugas 2 mata kuliah Pemrograman Lanjut.

## Fitur

* Menerima input pengguna dalam format gabungan (angka dan unit), contoh: `25C`, `77F`, `300K`.
* Secara otomatis mendeteksi unit input (C, F, atau K).
* Mengkonversi nilai input ke dua unit lainnya.
* Menampilkan hasil konversi dengan format dua angka di belakang koma.

## Cara Penggunaan

1.  **Kompilasi Program:**
    Buka terminal atau command prompt, navigasikan ke direktori file, dan kompilasi file `.java`:
    ```bash
    javac TemperatureConverter.java
    ```

2.  **Jalankan Program:**
    Setelah kompilasi berhasil, jalankan program:
    ```bash
    java TemperatureConverter
    ```

3.  **Masukkan Input:**
    Program akan meminta Anda memasukkan suhu. Ketik nilai dan unitnya (tanpa spasi), lalu tekan Enter.
    ```
    --- Program Konversi Suhu ---
    Masukkan suhu (misal: 25C, 77F, 300K): 25C
    ```

## Contoh Output

**Input: `25C`**
25.00 Celsius = 77.00 Fahrenheit 25.00 Celsius = 298.15 Kelvin

**Input: `98.6F`**
98.60 Fahrenheit = 37.00 Celsius 98.60 Fahrenheit = 310.15 Kelvin

**Input: `0K`**
0.00 Kelvin = -273.15 Celsius 0.00 Kelvin = -459.67 Fahrenheit


## Dibuat Oleh

* Farid Al Farizi