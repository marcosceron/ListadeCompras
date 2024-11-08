package com.example.listadecompras

data class ItemModel (val name: String, val onRemove:(ItemModel) -> Unit)