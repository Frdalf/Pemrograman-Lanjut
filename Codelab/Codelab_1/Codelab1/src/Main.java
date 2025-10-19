public class Main {
    public static void main(String[] args) {
        String[] nama = {"Adi", "Budi", "Cahyo", "Diana", "Eva"};
        String namaTerpanjang = cariNamaTerpanjang(nama);
        System.out.println("Nama terpanjang adalah: " + namaTerpanjang);
    }

    public static String cariNamaTerpanjang(String[] array) {
        // Guard clause: antisipasi array null / kosong
        if (array == null || array.length == 0) return "";

        // Jadikan elemen pertama sebagai kandidat awal
        String namaMax = array[0] == null ? "" : array[0].trim();

        // Mulai dari indeks 1 karena indeks 0 sudah dipakai kandidat awal
        for (int i = 1; i < array.length; i++) {          // <-- array.length (bukan length())
            String nama = array[i] == null ? "" : array[i].trim();
            if (nama.length() > namaMax.length()) {       // <-- String pakai length() (pakai tanda kurung)
                namaMax = nama;
            }
        }
        return namaMax;
    }
}
