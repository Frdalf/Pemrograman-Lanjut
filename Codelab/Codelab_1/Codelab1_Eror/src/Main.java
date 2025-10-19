public class Main {
    public static void main(String[] args) {
        String[] nama = {"Adi", "Budi", "Cahyo", "Diana", "Eva"};
        String namaTerpanjang = cariNamaTerpanjang(nama);
        System.out.println("Nama terpanjang adalah: " + namaTerpanjang);
    }

    public static String cariNamaTerpanjang(String[] array) {
        String namaMax = array[0];
        for (String nama : array) {
            if (nama.length() > namaMax.length()) {
                namaMax = nama;
            }
        }
        return namaMax;
    }
}