import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * RestaurantReceiptSimple — versi sederhana (console) untuk nota pemesanan.
 * Fitur minimal (tetap ada unsur unik):
 * - Pilih item & qty dari menu sederhana.
 * - Hitung Subtotal + PPN 10%.
 * - GRAND TOTAL dibulatkan ke kelipatan Rp100 (fitur unik & lokal Indonesia).
 * - Cetak struk rapi.
 *
 * Jalankan:  javac RestaurantReceiptSimple.java && java RestaurantReceiptSimple
 */

public class Main {

    // Model sederhana
    static class MenuItem {
        final int id; final String name; final int price; // rupiah
        MenuItem(int id, String name, int price){ this.id=id; this.name=name; this.price=price; }
    }
    static class OrderLine {
        final MenuItem item; final int qty;
        OrderLine(MenuItem item, int qty){ this.item=item; this.qty=qty; }
        int lineTotal(){ return item.price * qty; }
    }

    // Data menu ringkas
    static final List<MenuItem> MENU = List.of(
            new MenuItem(1, "Nasi Goreng",  18000),
            new MenuItem(2, "Mie Goreng", 16000),
            new MenuItem(3, "Ayam Bakar", 25000),
            new MenuItem(4, "Es Teh", 6000),
            new MenuItem(5, "Kopi Susu", 14000)
    );


    // Util
    static String rupiah(int v){ return String.format("%,d", v).replace(',', '.'); }
    static int roundTo100(int v){ return (int) (Math.round(v / 100.0) * 100); }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<OrderLine> lines = new ArrayList<>();

        System.out.println("=== Nota Restoran (Sederhana) ===\n");
        while (true){
            printMenu();
            System.out.print("Pilih ID menu (0=selesai): ");
            int id = readInt(sc);
            if (id==0) break;
            Optional<MenuItem> opt = MENU.stream().filter(m -> m.id==id).findFirst();
            if (opt.isEmpty()){ System.out.println("ID tidak ditemukan.\n"); continue; }
            System.out.print("Qty: ");
            int qty = Math.max(1, readInt(sc));
            lines.add(new OrderLine(opt.get(), qty));
            System.out.println("Ditambahkan: "+opt.get().name+" x"+qty+"\n");
        }

        // Hitung
        int subtotal = lines.stream().mapToInt(OrderLine::lineTotal).sum();
        int ppn = (subtotal * 10) / 100; // 10%
        int grandRaw = subtotal + ppn;
        int grandRounded = roundTo100(grandRaw); // fitur unik

        // Cetak struk
        System.out.println("\n================ STRUK =================");
        System.out.println("Waktu   : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")));
        System.out.println("---------------------------------------");
        System.out.printf("%-3s %-20s %4s %10s\n", "No", "Item", "Qty", "Total");
        System.out.println("---------------------------------------");
        int no=1; for (OrderLine l: lines){
            System.out.printf("%3d %-20s %4d %10s\n", no++, l.item.name, l.qty, rupiah(l.lineTotal()));
        }
        System.out.println("---------------------------------------");
        System.out.printf("%-20s %17s\n", "Subtotal", "Rp "+rupiah(subtotal));
        System.out.printf("%-20s %17s\n", "PPN 10%", "Rp "+rupiah(ppn));
        System.out.println("=======================================");
        System.out.printf("%-20s %17s\n", "GRAND TOTAL", "Rp "+rupiah(grandRounded));
        System.out.println("=======================================\n");
        System.out.println("Terima kasih!");
    }

    static void printMenu(){
        System.out.println("MENU:");
        for (MenuItem m: MENU){
            System.out.printf("%d) %-20s Rp %s\n", m.id, m.name, rupiah(m.price));
        }
        System.out.println();
    }

    static int readInt(Scanner sc){
        while(!sc.hasNextInt()){
            System.out.print("Masukkan angka: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
