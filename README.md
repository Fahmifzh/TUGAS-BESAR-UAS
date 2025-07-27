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
<p> <strong>SiDesaLite</strong> (Sistem Informasi Desa Lite) adalah aplikasi desktop berbasis JavaFX dan MySQL yang dikembangkan untuk mendigitalisasi layanan administrasi surat-menyurat di tingkat desa. Aplikasi ini bertujuan untuk meningkatkan efisiensi, transparansi, dan akuntabilitas, menggantikan proses manual yang lambat dan rentan kesalahan. </p> <p> Dalam sistem ini, terdapat dua jenis pengguna utama dengan hak akses dan fungsionalitas yang berbeda: </p> <ol> <li><strong>Penduduk:</strong> Warga desa yang dapat login menggunakan akun yang dibuat oleh petugas, lalu mengajukan permohonan surat, memantau statusnya, dan melihat hasil surat yang diterbitkan.</li> <li><strong>Petugas (termasuk Admin):</strong> Aparatur desa yang dapat memverifikasi permohonan surat, menerbitkan surat resmi, serta mengelola data penduduk, akun login, dan jenis surat.</li> </ol> <p> Fitur utama sistem ini meliputi: </p> <ul> <li><strong>Login & Otentikasi</strong> dengan validasi username dan password berdasarkan peran pengguna.</li> <li><strong>Manajemen Data Penduduk:</strong> CRUD data penduduk, termasuk input, edit, dan hapus data.</li> <li><strong>Manajemen Akun Penduduk:</strong> Membuat dan menghapus akun login berdasarkan data penduduk.</li> <li><strong>Pengelolaan Permohonan Surat:</strong> Verifikasi status permohonan, proses menjadi surat, serta update status seperti Menunggu, Diproses, Disetujui, atau Ditolak.</li> <li><strong>Manajemen Surat Diterbitkan:</strong> Melihat, mengedit, dan menghapus surat yang telah diterbitkan.</li> </li> <li><strong>Penduduk dapat mengajukan surat</strong> melalui form yang tersedia dan <strong>melihat statusnya secara real-time</strong>.</li> </ul> <p> Aplikasi ini menggunakan antarmuka modern dengan nuansa biru dominan yang bersih dan intuitif, serta memanfaatkan pola desain <strong>Object-Oriented Programming (OOP)</strong> dan <strong>DAO Pattern</strong> agar lebih terstruktur dan mudah dipelihara. </p>

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
