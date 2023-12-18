package fr.unilasalle.androidtp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.sql.Date

@Entity(
    tableName = "order",
    foreignKeys = [
        ForeignKey(
            entity = Cart::class,
            parentColumns = ["id"],
            childColumns = ["cartId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "cartId")
    val cartId: Int,

    @ColumnInfo(name = "date")
    val date: String
) : Serializable