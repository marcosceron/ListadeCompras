package com.example.listadecompras.model

import com.example.listadecompras.data.ItemEntity

data class ItemModel(
    val id: Long,
    val name: String,
    val onRemove: (ItemModel) -> Unit
)

fun ItemModel.toEntity(): ItemEntity {
    return ItemEntity(
        id = this.id,
        name = this.name
    )
}