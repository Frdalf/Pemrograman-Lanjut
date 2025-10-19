import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] nama = {"Adi", "Budi1", "Cahyo", "Diana", "Eva"};

        List<String> namaTerpanjang = cariNamaTerpanjang(nama);

        if (namaTerpanjang.isEmpty()) {
            System.out.println("Tidak ada nama valid.");
        } else {
            System.out.println("Nama terpanjang adalah: " + String.join(", ", namaTerpanjang));
        }
    }

    // Mengembalikan semua nama dengan panjang maksimum
    public static List<String> cariNamaTerpanjang(String[] array) {
        List<String> hasil = new ArrayList<>();
        if (array == null || array.length == 0) return hasil;

        int max = -1;
        for (String n : array) {
            if (n == null) continue;          // lewati elemen null
            int len = n.length();
            if (len > max) {                  // ditemukan panjang baru yang lebih besar
                hasil.clear();
                hasil.add(n);
                max = len;
            } else if (len == max) {          // sama panjang -> tambahkan juga
                hasil.add(n);
            }
        }
        return hasil;
    }
}
