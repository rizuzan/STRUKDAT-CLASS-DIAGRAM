# 🚦 SmartTrafficSystem — Sistem Lampu Lalu Lintas Cerdas V1.0

Simulasi sistem lampu lalu lintas berbasis Java yang menghitung durasi lampu hijau secara dinamis berdasarkan kepadatan kendaraan di setiap lajur jalan.

---

## 📋 Daftar Isi

- [Deskripsi Proyek](#deskripsi-proyek)
- [Struktur Kode](#struktur-kode)
- [Penjelasan Komponen](#penjelasan-komponen)
- [Logika Kalkulasi](#logika-kalkulasi)
- [Cara Menjalankan](#cara-menjalankan)
- [Contoh Output](#contoh-output)
- [Diagram Arsitektur](#diagram-arsitektur)

---

## Deskripsi Proyek

`SmartTrafficSystem` adalah program Java yang mensimulasikan sistem lalu lintas cerdas (*smart traffic*). Alih-alih menggunakan durasi lampu hijau yang tetap (timer statis), sistem ini menghitung durasi secara dinamis berdasarkan **total panjang kendaraan** yang mengantri di setiap lajur.

Semakin panjang antrean kendaraan → semakin lama lampu hijau menyala.

---

## Struktur Kode

```
SmartTrafficSystem.java
│
├── 📌 interface KontrolLampu
│     └── hitungDurasiHijau()
│
├── 📦 abstract class Kendaraan
│     ├── class Mobil   → panjang: 5 meter
│     ├── class Motor   → panjang: 2 meter
│     └── class Bus     → panjang: 12 meter
│
├── 🛣️  class LajurJalan  (implements KontrolLampu)
│     ├── tambahKendaraan()
│     ├── hitungDurasiHijau()
│     └── infoLajur()
│
└── 🚀 class SmartTrafficSystem (main)
```

---

## Penjelasan Komponen

### 1. `interface KontrolLampu`

Kontrak yang wajib dipenuhi oleh semua kelas yang mengelola lampu lalu lintas.

```java
interface KontrolLampu {
    int hitungDurasiHijau(); // Menghasilkan durasi dalam detik
}
```

> Setiap implementor **wajib** mendefinisikan cara menghitung durasi hijau.

---

### 2. `abstract class Kendaraan`

Kelas dasar untuk semua jenis kendaraan. Menyimpan `id` kendaraan dan memaksa subclass untuk mendefinisikan panjangnya masing-masing.

```java
abstract class Kendaraan {
    protected String id;
    public abstract int getPanjang(); // Wajib diimplementasikan subclass
}
```

---

### 3. Subclass Kendaraan

Tiga jenis kendaraan dengan panjang masing-masing yang sudah ditentukan:

| Kelas   | Jenis    | Panjang |
|---------|----------|---------|
| `Mobil` | Kendaraan pribadi | **5 meter** |
| `Motor` | Sepeda motor | **2 meter** |
| `Bus`   | Kendaraan besar | **12 meter** |

Contoh deklarasi:

```java
class Bus extends Kendaraan {
    public Bus(String id) { super(id); }

    @Override
    public int getPanjang() { return 12; }
}
```

---

### 4. `class LajurJalan` *(inti sistem)*

Kelas ini merepresentasikan satu lajur jalan dan mengimplementasikan `KontrolLampu`.

```java
class LajurJalan implements KontrolLampu {
    private String namaArah;
    private List<Kendaraan> antrean = new ArrayList<>();

    public void tambahKendaraan(Kendaraan k) { ... }

    @Override
    public int hitungDurasiHijau() { ... }

    public void infoLajur() { ... }
}
```

**Method penting:**

| Method | Fungsi |
|--------|--------|
| `tambahKendaraan(Kendaraan k)` | Menambahkan kendaraan ke antrean lajur |
| `hitungDurasiHijau()` | Menghitung dan mengembalikan durasi lampu hijau (detik) |
| `infoLajur()` | Menampilkan nama lajur dan jumlah kendaraan |

---

## Logika Kalkulasi

Rumus utama untuk menghitung durasi lampu hijau:

```
Durasi (detik) = Total Panjang Kendaraan (meter) / 2
```

Dengan batasan:

```
Minimal  : 10 detik  (agar kendaraan tetap bisa jalan meski lajur sepi)
Maksimal : 60 detik  (agar tidak menghambat lajur lain terlalu lama)
```

**Implementasi dalam kode:**

```java
@Override
public int hitungDurasiHijau() {
    int totalPanjang = 0;
    for (Kendaraan k : antrean) {
        totalPanjang += k.getPanjang();
    }

    int durasi = totalPanjang / 2;

    if (durasi < 10) return 10;
    if (durasi > 60) return 60;
    return durasi;
}
```

**Contoh kalkulasi (Lajur Utara):**

```
Kendaraan  : Mobil (5) + Bus (12) + Mobil (5) + Motor (2) = 24 meter
Durasi     : 24 / 2 = 12 detik
Hasil      : 12 detik ✅ (dalam rentang 10–60)
```

**Contoh kalkulasi (Lajur Selatan):**

```
Kendaraan  : Motor (2) + Motor (2) = 4 meter
Durasi     : 4 / 2 = 2 detik → di bawah minimum!
Hasil      : 10 detik ✅ (dibulatkan ke minimum)
```

---

## Cara Menjalankan

### Prasyarat

- Java Development Kit (JDK) versi **8 atau lebih baru**

### Langkah Kompilasi dan Eksekusi

```bash
# 1. Simpan file sebagai SmartTrafficSystem.java

# 2. Kompilasi
javac SmartTrafficSystem.java

# 3. Jalankan
java SmartTrafficSystem
```

---

## Contoh Output

```
=== SISTEM LAMPU LALU LINTAS CERDAS V1.0 ===

Lajur: Arah Utara (Jl. Manyar) | Jumlah Kendaraan: 4
Rekomendasi Lampu Hijau: 12 detik.
--------------------------------------------
Lajur: Arah Selatan (Jl. Gebang) | Jumlah Kendaraan: 2
Rekomendasi Lampu Hijau: 10 detik.

[KESIMPULAN] Sistem memberikan durasi lebih lama pada Lajur Utara karena beban jalan lebih besar.
```

---

## Diagram Arsitektur

```
         ┌──────────────────────────────┐
         │      «interface»             │
         │      KontrolLampu            │
         │  + hitungDurasiHijau(): int  │
         └──────────────┬───────────────┘
                        │ implements
                        ▼
         ┌──────────────────────────────┐
         │        LajurJalan            │
         │  - namaArah: String          │
         │  - antrean: List<Kendaraan>  │
         │  + tambahKendaraan()         │
         │  + hitungDurasiHijau(): int  │
         │  + infoLajur()               │
         └──────────────┬───────────────┘
                        │ menggunakan
                        ▼
         ┌──────────────────────────────┐
         │    «abstract» Kendaraan      │
         │  # id: String                │
         │  + getPanjang(): int         │
         └───────┬──────────┬───────────┘
                 │          │
        ┌────────┘    ┌─────┘─────────┐
        ▼             ▼               ▼
   ┌─────────┐  ┌─────────┐     ┌─────────┐
   │  Mobil  │  │  Motor  │     │   Bus   │
   │  5 mtr  │  │  2 mtr  │     │ 12 mtr  │
   └─────────┘  └─────────┘     └─────────┘
```

---

## Konsep OOP yang Digunakan

| Konsep | Implementasi |
|--------|-------------|
| **Abstraksi** | `abstract class Kendaraan` dan `interface KontrolLampu` |
| **Pewarisan (Inheritance)** | `Mobil`, `Motor`, `Bus` mewarisi `Kendaraan` |
| **Polimorfisme** | `getPanjang()` berbeda di tiap subclass |
| **Enkapsulasi** | Field `antrean` bersifat `private` di `LajurJalan` |

---

## Kemungkinan Pengembangan

- [ ] Menambahkan lebih banyak jenis kendaraan (Truk, Ambulans, dll.)
- [ ] Sistem prioritas untuk kendaraan darurat
- [ ] Simulasi multi-lajur dengan sinkronisasi antar persimpangan
- [ ] Antarmuka grafis (GUI) untuk visualisasi lalu lintas
- [ ] Integrasi sensor real-time