# Final Proyek Pemrograman Berorientasi Objek 2
<ul>
    <li>Mata Kuliah: Pemrograman Berorientasi Objek 2</li>
    <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

---

## Profil
<ul>
    <li>Nama: Azmi Syahri Ramadhan, Fahmi Fauziah Nur Fadillah, Salma Salamah</li>
    <li>NIM: 23552011068, 23552011314, 23552011424</li>
    <li>Studi Kasus: Sistem Informasi Pengajuan dan Pengelolaan Surat Desa (SIDESALITE)</li>
</ul>

---

## Judul Studi Kasus
<p>Sistem Informasi Pengajuan dan Pengelolaan Surat Desa (SIDESALITE)</p>

---

## Penjelasan Studi Kasus
<p>SIDESALITE adalah sistem informasi berbasis database yang dirancang untuk memfasilitasi proses pengajuan, pengelolaan, dan penerbitan surat resmi di tingkat desa. Tujuan utama sistem ini adalah untuk menggantikan proses manual menjadi lebih terstruktur dan efisien secara digital. Dalam sistem ini terdapat dua jenis pengguna utama:
    <ul>
        <li><b>Penduduk</b>: dapat melakukan login, mengisi data diri, dan mengajukan permohonan surat seperti Surat Keterangan Usaha.</li>
        <li><b>Petugas</b>: bertugas memverifikasi, memproses, dan menerbitkan surat yang diajukan oleh penduduk. Petugas juga dapat memberikan catatan pada proses permohonan.</li>
    </ul>
    Fitur utama sistem:
    <br>
    <b>Aktor 1: Petugas Desa</b>
    <ul>
        <li>Login</li>
        <li>Manajemen Data Penduduk (Tambah/Edit/Hapus/Lihat data penduduk)</li>
        <li>Manajemen Akun Penduduk (Tambah/Edit User login)</li>
        <li>Verifikasi Permohonan Surat</li>
        <li>Pencetakan Surat</li>
        <li>Manajemen Jenis Surat (Tambah/Edit jenis surat yang tersedia, e.g., Domisili, Tidak Mampu)</li>
    </ul>
    <b>Aktor 2: Penduduk (Warga)</b>
    <ul>
        <li>Login</li>
        <li>Ajukan Permohonan Surat (Pilih jenis surat, isi keperluan)</li>
        <li>Lihat Status Permohonan (Menunggu/verifikasi/sukses)</li>
    </ul>
    Struktur data disimpan dalam database MySQL dengan relasi antar tabel seperti: <b>users</b>, <b>penduduk</b>, <b>jenis_surat</b>, <b>permohonan_surat</b>, dan <b>surat</b>.
    <br>
    Contoh alur kerja:
    <ol>
        <li>Petugas membuka aplikasi & login.</li>
        <li>Petugas mengelola akun Penduduk:
            <ul>
                <li>Buat akun login untuk penduduk (User).</li>
                <li>Input data pribadi penduduk.</li>
            </ul>
        </li>
        <li>Penduduk login ke aplikasi (pakai username & password yang dibuat petugas).</li>
        <li>Penduduk mengajukan permohonan surat melalui form yang tersedia.</li>
        <li>Petugas memverifikasi permohonan surat.</li>
        <li>Petugas mencetak surat yang sudah diverifikasi.</li>
        <li>Semua surat dan permohonan tersimpan di database.</li>
        <li>Petugas dapat melihat laporan surat yang sudah dicetak.</li>
    </ol>
</p>

---

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<p>Sistem ini mengimplementasikan pewarisan melalui konsep role pengguna. Terdapat class dasar <b>User</b> yang memiliki atribut umum seperti username, password, dan role. Class ini diturunkan menjadi <b>Penduduk</b> dan <b>Petugas</b> yang memiliki fungsi tambahan sesuai tanggung jawab masing-masing. Misalnya, Penduduk memiliki kemampuan untuk mengajukan surat, sementara Petugas dapat memproses dan menerbitkan surat.</p>

### 2. Encapsulation
<p>Data penting seperti password, NIK, dan catatan permohonan dibatasi aksesnya. Setiap entitas memiliki akses terbatas terhadap data yang mereka miliki atau butuhkan. Hal ini menjaga keamanan data pengguna dan mencegah manipulasi yang tidak sah. Akses ke database dilakukan melalui metode yang ditentukan (misalnya, melalui form dan validasi sistem).</p>

### 3. Polymorphism
<p>Polimorfisme diterapkan melalui metode yang menyesuaikan perilaku tergantung pada role pengguna. Contoh: fungsi <code>tampilkanDataPermohonan()</code> menampilkan daftar seluruh permohonan untuk Petugas, namun hanya menampilkan permohonan milik sendiri jika diakses oleh Penduduk. Begitu juga untuk pengolahan data dan pembuatan surat, metode yang sama memiliki output yang berbeda tergantung pemanggilnya.</p>

### 4. Abstraction
<p>Sistem menyembunyikan kerumitan struktur database dari pengguna akhir. Pengguna tidak perlu mengetahui bagaimana data antar-tabel seperti users, penduduk, jenis_surat, permohonan_surat, dan surat saling terhubung melalui foreign key. Cukup dengan mengisi form permohonan atau klik tombol proses, semua logika sistem berjalan di belakang layar.</p>

---

## Demo Proyek
<ul>
    <li>Github: <a href="https://github.com/your-username/your-repo-name">Github</a></li>
    <li>Youtube: <a href="Link_Video_Youtube_Anda">Youtube</a></li>
</ul>
