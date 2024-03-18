package ru.papino.restaurant.data.datasource.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "baskets", indices = [Index("id")])
internal data class BasketProductEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "size") val size: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "details") val details: String,
    @ColumnInfo(name = "typeProduct") val typeProduct: String,
    @ColumnInfo(name = "linkCover") val linkCover: String,
    @ColumnInfo(name = "count") val count: Int
)
