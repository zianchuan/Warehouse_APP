package com.example.warehouse_app

class DatabaseModel(
    var itemName: String = "",
    var itemId: String = "",
    var image: String = "",
    var itemDescription: String = "",
    var itemQuantity: String = "",
    var rackId: String = "",
    var putAwayDateTime: Long = 0
) {
}