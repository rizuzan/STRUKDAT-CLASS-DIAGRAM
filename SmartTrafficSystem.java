import java.util.ArrayList;
import java.util.List;

// --- 1. INTERFACE ---
interface KontrolLampu {
    int hitungDurasiHijau(); // Menghasilkan detik
}

// --- 2. ABSTRACT CLASS ---
abstract class Kendaraan {
    protected String plat;
    protected int panjang; // dalam satuan meter

    public Kendaraan(String plat) {
        this.plat = plat;
    }

    public abstract int getPanjang();
    public String getId() { return plat; }
}

// --- 3. SUBCLASSES ---
class Mobil extends Kendaraan {
    public Mobil(String plat) { super(plat); }
    @Override
    public int getPanjang() { return 5; } // Mobil rata-rata 5 meter
}

class Motor extends Kendaraan {
    public Motor(String plat) { super(plat); }
    @Override
    public int getPanjang() { return 2; } // Motor 2 meter
}

class Bus extends Kendaraan {
    public Bus(String plat) { super(plat); }
    @Override
    public int getPanjang() { return 12; } // Bus 12 meter
}

// --- 4. SISTEM LAJUR (IMPLEMENTASI LOGIKA) ---
class LajurJalan implements KontrolLampu {
    private String namaArah;
    private List<Kendaraan> antrean = new ArrayList<>();

    public LajurJalan(String namaArah) {
        this.namaArah = namaArah;
    }

    public void tambahKendaraan(Kendaraan k) {
        antrean.add(k);
    }

    @Override
    public int hitungDurasiHijau() {
        int totalPanjang = 0;
        for (Kendaraan k : antrean) {
            totalPanjang += k.getPanjang();
        }

        // LOGIKA UNIK: 
        // Durasi hijau = Total panjang kendaraan dibagi 2 (asumsi 1 detik untuk 2 meter kendaraan)
        // Minimal 10 detik, Maksimal 60 detik.
        int durasi = totalPanjang / 2;
        
        if (durasi < 10) return 10;
        if (durasi > 60) return 60;
        return durasi;
    }

    public void infoLajur() {
        System.out.println("Lajur: " + namaArah + " | Jumlah Kendaraan: " + antrean.size());
    }
}

// --- 5. MAIN RUNNER ---
public class SmartTrafficSystem {
    public static void main(String[] args) {
        System.out.println("=== SISTEM LAMPU LALU LINTAS CERDAS ===\n");

        // Membuat 2 Lajur (Contoh: Utara dan Selatan)
        LajurJalan lajurUtara = new LajurJalan("Arah Utara (Jl. Manyar)");
        LajurJalan lajurSelatan = new LajurJalan("Arah Selatan (Jl. Gebang)");

        // Simulasi Lajur Utara (Sangat Padat)
        lajurUtara.tambahKendaraan(new Mobil("L-1234-AB"));
        lajurUtara.tambahKendaraan(new Bus("L-7788-BUS"));
        lajurUtara.tambahKendaraan(new Mobil("W-9901-XY"));
        lajurUtara.tambahKendaraan(new Motor("L-4421-SS"));

        // Simulasi Lajur Selatan (Sepi)
        lajurSelatan.tambahKendaraan(new Motor("L-5562-ZZ"));
        lajurSelatan.tambahKendaraan(new Motor("L-1122-AA"));

        // Output Simulasi
        lajurUtara.infoLajur();
        System.out.println("Rekomendasi Lampu Hijau: " + lajurUtara.hitungDurasiHijau() + " detik.");
        
        System.out.println("--------------------------------------------");
        
        lajurSelatan.infoLajur();
        System.out.println("Rekomendasi Lampu Hijau: " + lajurSelatan.hitungDurasiHijau() + " detik.");

        System.out.println("\n[KESIMPULAN] Sistem memberikan durasi lebih lama pada Lajur Utara karena beban jalan lebih besar.");
    }
}
