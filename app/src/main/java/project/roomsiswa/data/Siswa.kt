package project.roomsiswa.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(tableName = "tblSiswa")
//data class Siswa(
//    @PrimaryKey(autoGenerate = true)
//    val id : Int = 0,
//    val nama : String,
//    val alamat : String,
//    val telpon : String
//)

@Entity(
    tableName = "tblMenu",
    indices = [Index(value = ["menu"], unique = true)] // Menambahkan indeks unik pada kolom menu
)
data class Menu(
    @PrimaryKey
    val idmenu : Int,
    val menu : String,
    val harga : String,
    val ketersediaan : String,
    val kategori : String,
)


@Entity(
    tableName = "tblPesanan",
    foreignKeys = [ForeignKey(
        // foreign key nya diambil dari data class Menu
        entity = Menu::class,
        parentColumns = ["menu"],
        childColumns = ["idMenuForeignKey"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("idMenuForeignKey")] // Menambahkan index untuk idMenuForeignKey
)

data class Pesanan(
    @PrimaryKey
    val idpesanan : Int,
    val nama : String,
    val detail : String,
    val metode : String,
    val tanggal : String,
    val idMenuForeignKey: String // Foreign key reference to idmenu from Menu
)
