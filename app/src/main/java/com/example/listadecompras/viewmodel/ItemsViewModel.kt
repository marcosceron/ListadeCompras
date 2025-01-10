package com.example.listadecompras.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.listadecompras.data.ItemsDatabase
import com.example.listadecompras.data.toModel

import androidx.lifecycle.viewModelScope
import com.example.listadecompras.data.ItemEntity
import com.example.listadecompras.model.ItemModel
import com.example.listadecompras.model.toEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ItemsViewModel(
    private val database: ItemsDatabase
): ViewModel() {

    val itemsLiveData = MutableLiveData<List<ItemModel>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAll()
        }
    }

    fun addItem(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = ItemEntity(id = 0, name = name)
            database.itemsDao().insert(entity)
            fetchAll()
        }
    }

    private suspend fun fetchAll() {
        val result = database.itemsDao().getAll().map {
            it.toModel(onRemove = ::removeItem)
        }
        itemsLiveData.postValue(result)
    }

    private fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val entity = item.toEntity()
            database.itemsDao().delete(entity)
            fetchAll()
        }
    }
}


