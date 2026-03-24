# 🚦 Sistem Lampu Lalu Lintas Cerdas V1.0

Simulasi sistem kontrol lampu lalu lintas adaptif berbasis **Object-Oriented Programming (OOP)** menggunakan Java. Sistem menghitung durasi lampu hijau secara dinamis berdasarkan kepadatan dan jenis kendaraan pada setiap lajur jalan.

---

## 📋 Deskripsi Kasus

Persimpangan jalan memiliki beberapa lajur dengan kepadatan kendaraan yang berbeda-beda. Sistem ini mensimulasikan:

- Pengelolaan **antrean kendaraan** pada tiap lajur secara independen
- Deteksi **jenis kendaraan** (Mobil, Motor, Bus) dengan ukuran fisik berbeda
- Perhitungan **rekomendasi durasi lampu hijau** secara adaptif
- Batas aman durasi: **minimum 10 detik**, **maksimum 60 detik**

### Skenario Simulasi

| Lajur | Kendaraan | Total Panjang | Durasi Hijau |
|---|---|---|---|
| Arah Utara (Jl. Manyar) | Mobil, Bus, Mobil, Motor | 24 meter | **12 detik** |
| Arah Selatan (Jl. Gebang) | Motor, Motor | 4 meter | **10 detik** (minimum) |

> **Kesimpulan:** Lajur Utara mendapat durasi lebih lama karena beban kendaraannya lebih besar.

---

## 🗂️ Class Diagram

<img width="3162" height="4430" alt="Untitled diagram-2026-03-24-193447" src="https://github.com/user-attachments/assets/f23d8b42-b81d-4763-8c8f-94122ad9821f" />

---

## 💻 Kode Program Java

```java
import java.util.ArrayList;
import java.util.List;

// --- 1. INTERFACE ---
interface KontrolLampu {
    int hitungDurasiHijau(); // Menghasilkan detik
}

// --- 2. ABSTRACT CLASS ---
abstract class Kendaraan {
    protected String id;
    protected int panjang; // dalam satuan meter

    public Kendaraan(String id) {
        this.id = id;
    }

    public abstract int getPanjang();
    public String getId() { return id; }
}

// --- 3. SUBCLASSES ---
class Mobil extends Kendaraan {
    public Mobil(String id) { super(id); }
    @Override
    public int getPanjang() { return 5; } // Mobil rata-rata 5 meter
}

class Motor extends Kendaraan {
    public Motor(String id) { super(id); }
    @Override
    public int getPanjang() { return 2; } // Motor 2 meter
}

class Bus extends Kendaraan {
    public Bus(String id) { super(id); }
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
        // Durasi hijau = Total panjang / 2 (1 detik per 2 meter kendaraan)
        // Minimal 10 detik, Maksimal 60 detik
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
        System.out.println("=== SISTEM LAMPU LALU LINTAS CERDAS V1.0 ===\n");

        LajurJalan lajurUtara  = new LajurJalan("Arah Utara (Jl. Manyar)");
        LajurJalan lajurSelatan = new LajurJalan("Arah Selatan (Jl. Gebang)");

        // Simulasi Lajur Utara (Padat)
        lajurUtara.tambahKendaraan(new Mobil("L-1234-AB"));
        lajurUtara.tambahKendaraan(new Bus("L-7788-BUS"));
        lajurUtara.tambahKendaraan(new Mobil("W-9901-XY"));
        lajurUtara.tambahKendaraan(new Motor("L-4421-SS"));

        // Simulasi Lajur Selatan (Sepi)
        lajurSelatan.tambahKendaraan(new Motor("L-5562-ZZ"));
        lajurSelatan.tambahKendaraan(new Motor("L-1122-AA"));

        lajurUtara.infoLajur();
        System.out.println("Rekomendasi Lampu Hijau: " + lajurUtara.hitungDurasiHijau() + " detik.");
        System.out.println("--------------------------------------------");
        lajurSelatan.infoLajur();
        System.out.println("Rekomendasi Lampu Hijau: " + lajurSelatan.hitungDurasiHijau() + " detik.");
        System.out.println("\n[KESIMPULAN] Sistem memberikan durasi lebih lama pada Lajur Utara karena beban jalan lebih besar.");
    }
}
```

---

## 🖥️ Screenshot Output

```
=== SISTEM LAMPU LALU LINTAS CERDAS V1.0 ===

Lajur: Arah Utara (Jl. Manyar) | Jumlah Kendaraan: 4
Rekomendasi Lampu Hijau: 12 detik.
--------------------------------------------
Lajur: Arah Selatan (Jl. Gebang) | Jumlah Kendaraan: 2
Rekomendasi Lampu Hijau: 10 detik.

[KESIMPULAN] Sistem memberikan durasi lebih lama pada Lajur Utara karena beban jalan lebih besar.
```

### Cara Menjalankan

```bash
# Compile
javac SmartTrafficSystem.java

# Run
java SmartTrafficSystem
```

---

## 🧩 Prinsip-Prinsip OOP yang Diterapkan

### 1. 🔒 Enkapsulasi (Encapsulation)
Atribut kelas disembunyikan dari akses luar menggunakan access modifier.

- `LajurJalan` memiliki `private namaArah` dan `private List<Kendaraan> antrean` — hanya bisa diakses melalui method publik `tambahKendaraan()` dan `infoLajur()`
- `Kendaraan` menggunakan `protected id` sehingga hanya subclass yang bisa mengaksesnya langsung
- Data internal terlindungi dari manipulasi langsung luar kelas

### 2. 🧬 Pewarisan (Inheritance)
Subclass mewarisi properti dan method dari abstract class `Kendaraan`.

- `Mobil`, `Motor`, dan `Bus` semuanya `extends Kendaraan` — mewarisi atribut `id` dan method `getId()`
- Setiap subclass hanya perlu mengimplementasikan `getPanjang()` sesuai karakteristiknya
- Menghindari duplikasi kode (prinsip DRY)

### 3. 🔄 Polimorfisme (Polymorphism)
Objek dari berbagai subclass diperlakukan secara seragam melalui tipe referensi parent.

- `List<Kendaraan>` di `LajurJalan` menyimpan `Mobil`, `Motor`, dan `Bus` dalam satu koleksi
- Pemanggilan `k.getPanjang()` secara otomatis memanggil implementasi yang tepat sesuai tipe objek nyatanya *(runtime polymorphism / dynamic dispatch)*
- Menambah jenis kendaraan baru tidak memerlukan perubahan logika `LajurJalan`

### 4. 🎭 Abstraksi (Abstraction)
Detail implementasi disembunyikan, hanya kontrak yang diekspos.

- `interface KontrolLampu` mendefinisikan kontrak `hitungDurasiHijau()` tanpa mengekspos caranya
- `abstract class Kendaraan` memaksa setiap subclass mendefinisikan `getPanjang()` sendiri
- `SmartTrafficSystem` berinteraksi dengan `LajurJalan` tanpa perlu tahu detail kalkulasinya

---

## ✨ Keunikan yang Membedakan

### 1. Algoritma Berbasis Panjang Fisik Kendaraan
Kebanyakan implementasi hanya menghitung **jumlah** kendaraan. Sistem ini menggunakan **total panjang fisik** (meter) sebagai dasar perhitungan:

```
Durasi Hijau = Total Panjang Kendaraan (meter) / 2
```

| Kendaraan | Panjang | Kontribusi Durasi |
|---|---|---|
| Motor | 2 meter | +1 detik |
| Mobil | 5 meter | +2.5 detik |
| Bus | 12 meter | +6 detik |

> Bus berkontribusi 6× lebih besar dari motor — lebih realistis secara fisik.

### 2. Arsitektur Berlapis yang Bersih
Pemisahan tanggung jawab yang tegas *(Separation of Concerns)*:

- **`KontrolLampu`** → Kontrak: strategi perhitungan bisa diganti tanpa ubah struktur
- **`Kendaraan`** → Blueprint: menjamin konsistensi antar semua tipe kendaraan
- **`LajurJalan`** → Agregator: mengelola kendaraan sekaligus menghitung durasi

### 3. Skalabilitas Tinggi (Open/Closed Principle)
- Tambah kendaraan baru (misal `Truk`, `Sepeda`) → cukup buat subclass baru
- Tambah lajur baru (misal Timur, Barat) → cukup instansiasi `LajurJalan` baru
- Tidak ada satu baris pun di kelas lain yang perlu diubah

### 4. Simulasi Identitas Kendaraan Realistis
Setiap kendaraan memiliki ID berformat **plat nomor Indonesia** (contoh: `L-1234-AB`, `W-9901-XY`), membuat simulasi lebih autentik dan mudah dilacak saat debugging.

---

## 📁 Struktur File

```
SmartTrafficSystem/
└── SmartTrafficSystem.java   # Semua kelas dalam satu file
```

---

*Dibuat sebagai implementasi konsep OOP di Java — 2026*
