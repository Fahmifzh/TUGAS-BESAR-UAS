# Final Proyek Pemrograman Berorientasi Obyek 2

<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: Azmi Syahri Ramadhan, Fahmi Fauziah Nur Fadillah, Salma Salamah</li>
  <li>NIM: 23552011068, 23552011314, 23552011424</li>
  <li>Studi Kasus: Sistem Informasi Pelayanan Surat Desa (SiDesaLite)</li>
</ul>

## Judul Studi Kasus
<p>SiDesaLite – Aplikasi Sistem Informasi Pelayanan Surat Desa Berbasis JavaFX</p>

## Penjelasan Studi Kasus
<p>
SiDesaLite adalah aplikasi desktop berbasis JavaFX yang memfasilitasi proses pelayanan surat di tingkat desa. Penduduk dapat mengajukan surat, dan petugas desa dapat memverifikasi, mengelola, serta menerbitkan surat tersebut. Sistem ini membantu mempercepat alur birokrasi surat menyurat dan mendukung digitalisasi pelayanan desa.
</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>
Contoh penggunaan inheritance adalah class `User` sebagai superclass yang diturunkan ke `Petugas` dan `Penduduk`, sehingga meminimalkan duplikasi kode umum seperti `id`, `username`, dan `role`.
</p>

### 2. Encapsulation
<p>
Encapsulation diterapkan dengan cara menyembunyikan atribut class menggunakan access modifier `private`, dan menyediakan akses melalui getter dan setter. Contohnya pada class `Penduduk` dan `Permohonan`.
</p>

### 3. Polymorphism
<p>
Polymorphism digunakan pada interface `CrudDao<T>` yang diimplementasikan oleh class DAO lain seperti `PendudukDao`, `PermohonanDao`, dll, sehingga mendukung kode yang fleksibel dan reusable.
</p>

### 4. Abstract
<p>
Abstraksi dilakukan dengan memisahkan logika penyimpanan data menggunakan DAO pattern, sehingga UI tidak langsung terhubung ke database dan memudahkan pengelolaan kode.
</p>

## Demo Proyek
<ul>
  <li>Github: <a href="https://github.com/username/SiDesaLite">Github</a></li>
  <li>Youtube: <a href="https://youtu.be/linkdemo">Youtube</a></li>
</ul>
