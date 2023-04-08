package com.example.jsonviewer

public class Model{
    lateinit var itemid:String
    lateinit var name:String
    lateinit var active:String

    constructor(id: String,name:String,email:String) {
        this.itemid = itemid
        this.name = name
        this.active = active
    }

    constructor()
}