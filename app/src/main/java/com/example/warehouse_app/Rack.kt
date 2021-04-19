package com.example.warehouse_app

class Rack {
    //model
    var itemName: String? = null
    var itemId: String? = null
    var itemQuantity: String? = null
    var rackId: String? = null
    var imageUrl: String? = null

    //Construtor
    constructor() {}
    constructor(
        itemName: String?,
        itemId: String?,
        itemQuantity: String?,
        rackId: String?,
        imageUrl: String?
    ) {
        this.itemName = itemName
        this.itemId = itemId
        this.itemQuantity = itemQuantity
        this.rackId = rackId
        this.imageUrl = imageUrl
    }


}