package com.pwr.trainwithme

import java.time.Duration
import java.util.*

interface Summarisable{
    val title : String
    val imageUrl : String
}

interface Offer : Summarisable {
    val price : String
    val rating : Float
}

interface Contactable{
    val phone: String
    val email: String
}

class Trainer(
    val name: String,
    val surname: String,
    override val imageUrl: String,
    val description: String,
    val pricePerHour: Float,
    override val rating: Float = 0f
) : Offer {
    override val title
    get() = name

    override val price
    get() = pricePerHour.toString()


}

class Sport(
    val name: String,
    override val imageUrl: String
) : Summarisable {
    override val title
    get() = name
}

class SportObject(
    val name: String,
    override val imageUrl: String
) : Summarisable {
    override val title
    get() = name
}

class Training(
    val startDate: Date,
    val duration: Float,
    val maxTrainees: Int,
    val sport: Sport
) : Summarisable {
    override val title: String
        get() = "${sport.name}, $startDate "

    override val imageUrl: String
        get() = sport.imageUrl
}

class Reading(
    val name: String,
    val value: Float,
    val unit: String
)

class Pass(
    val name: String,
    override val imageUrl: String
) : Summarisable{
    override val title: String
    get() = name
}
