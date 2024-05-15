package com.example.wazitoecommerce.models

class Book {
    var book:String = ""
    var author:String = ""
    var genre:String = ""
    var imageUrl:String = ""
    var id:String = ""

    constructor(name: String, author: String, genre: String, imageUrl: String, id: String) {
        this.book = name
        this.author = author
        this.genre = genre
        this.imageUrl = imageUrl
        this.id = id
    }

    constructor()
}