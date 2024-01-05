package project.roomsiswa.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblSiswa")
data class Siswa(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val nama : String,
    val alamat : String,
    val telpon : String
)

@Entity(tableName = "tblMapel")
data class Mapel(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val namamapel : String,
    val guru : String,
    val jam : String
)

@Entity(tableName = "tblPerilaku")
data class Perilaku(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val sanksi : String,
    val penghargaan : String,
)

data class CombinedData(
    val siswa: Siswa,
    val mapel: Mapel?,
    val perilaku: Perilaku?
)

