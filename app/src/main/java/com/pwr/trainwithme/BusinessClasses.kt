package com.pwr.trainwithme

interface Summarisable{
    val title : String
    val imageUrl : String
}

class Trainer(
    val name: String,
    val surname: String,
    override val imageUrl: String,
    val description: String
) : Summarisable {
    override val title
    get() = name
}

class Sport(
    val name: String,
    override val imageUrl: String
) : Summarisable {
    override val title
    get() = name
}

