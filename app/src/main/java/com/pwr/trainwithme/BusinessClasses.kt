package com.pwr.trainwithme

import java.time.Duration
import java.util.*

interface Summarisable{
    val title : String
    val imageUrl : String
}

interface Detailable : Summarisable {
    val rating : Float
    val price : String?
}

class TrainerVM(private val trainer: Trainer) : Detailable{
    override val title: String
        get() = trainer.name
    override val imageUrl: String
        get() = trainer.imagesUrls[0]
    override val price: String
        get() = trainer.pricePerHour.toString()
    override val rating: Float
        get() = trainer.rating
}

class Trainer(
    val name: String,
    val surname: String,
    val description: String,
    val email: String,
    val phone: String,
    val pricePerHour: Float,
    val rating: Float = 0f,
    val imagesUrls: Array<String>
)

class SportFacade(private val sport: Sport) : Summarisable{
    override val title: String
        get() = sport.name
    override val imageUrl: String
        get() = sport.imageUrl

}

class Sport(
    val name: String,
    val imageUrl: String
)

class SportVM(private val sportCentre : SportCentre) : Detailable {
    override val title: String
        get() = sportCentre.name
    override val imageUrl: String
        get() = sportCentre.imageUrl
    override val price: String?
        get() = null
    override val rating: Float
        get() = sportCentre.rating

}

class SportCentre(
    val name: String,
    val imageUrl: String,
    val rating: Float
)

class TrainingVM(private val training: Training) : Summarisable{
    override val title: String
        get() = training.sport.name
    override val imageUrl: String
        get() = training.sport.imageUrl
}

class Training(
    val startDate: Date,
    val duration: Float,
    val maxTrainees: Int,
    val sport: Sport
)

class PassVM(private val pass: Pass) : Summarisable{
    override val title: String
        get() = pass.name
    override val imageUrl: String
        get() = pass.imageUrl
}

class Pass(
    val name: String,
    val imageUrl: String
)


