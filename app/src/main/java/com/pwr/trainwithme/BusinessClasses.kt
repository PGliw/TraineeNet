package com.pwr.trainwithme

import java.util.*

interface Summarisable{
    val id: String
    val title : String
    val imageUrl : String
}

interface Detailable : Summarisable {
    val rating : Float
    val price : String?
}

class TrainerVM(private val trainer: Trainer) : Detailable{
    override val id: String
        get() = trainer.id ?: trainer.firstName // TODO change ONLY to Trainer.id
    override val title: String
        get() = trainer.firstName
    override val imageUrl: String
        get() = trainer.photoUrl ?: trainer.imagesUrls[0]
    override val price: String
        get() = trainer.pricePerHour.toString()
    override val rating: Float
        get() = trainer.rating
}

// TODO refactor trainer class
class Trainer(
    val firstName: String,
    val lastName: String,
    val description: String,
    val email: String = "", // TODO
    val phone: String = "", // TODO
    val pricePerHour: Float,
    val rating: Float = 0f,
    val imagesUrls: Array<String> = arrayOf(), // TODO
    val photoUrl: String? = null,
    val id: String? = null // TODO
)

class SportVM(private val sport: Sport): Summarisable{
    override val id: String
        get() = sport.name // TODO change to SportCentre.id
    override val title: String
        get() = sport.name
    override val imageUrl: String
        get() = sport.imageUrl
}

class Sport(
    val name: String,
    val imageUrl: String
)

class SportCentreVM(private val sportCentre : SportCentre) : Detailable {
    override val id: String
        get() = sportCentre.name // TODO change to SportCentre.id
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
    // TODO fix tight coupling (calling like training.sport.firstName)
    override val id: String
        get() = training.sport.name // TODO change to training.id
    override val title: String
        get() = training.sport.name
    override val imageUrl: String
        get() = training.sport.imageUrl
}

class Training(
    val trainer: Trainer,
    val startDate: Date,
    val endDate: Date,
    val centre: SportCentre,
    val maxTrainees: Int,
    val sport: Sport,
    val totalPrice: Float,
    val status: Status
){
    enum class Status{
        NOT_DEFINED,
        PROPOSED,
        DENIED,
        ACCEPTED,
        CANCELLED,
        IN_PROGRESS,
        FINISHED
    }
}

class PassVM(private val pass: Pass) : Summarisable{
    override val id: String
        get() = pass.name // TODO change to pass.id
    override val title: String
        get() = pass.name
    override val imageUrl: String
        get() = pass.imageUrl
}

class Pass(
    val name: String,
    val imageUrl: String
)


