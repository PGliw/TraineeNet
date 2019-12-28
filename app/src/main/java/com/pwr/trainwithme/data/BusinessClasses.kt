package com.pwr.trainwithme.data

import java.text.DateFormat
import java.util.*

interface Summarisable{
    val id: String
    val title : String
    val imageUrl : String
}

data class Summary(
    override val id: String,
    override val title: String,
    override val imageUrl: String
) : Summarisable

data class TrainerOverview(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val photoUrl: String,
    val meanGrade: Float?,
    val opinionsCount: Int,
    val maxPrice: Float?,
    val minPrice: Float?,
    val sports: List<String>
)

data class TrainerDetails(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val photoUrl: String,
    val age: Int,
    val meanGrade: Float?,
    val opinionsCount: Int,
    val description: String,
    val imagesUrls: List<String>,
    val offers: List<OfferResponse>
)

data class CentreOverview(
    val id: Long,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val photoUrl: String
)

data class TimeSlot(
    val startDate: Date,
    val endDate: Date
){
    override fun toString(): String {
        val startStr = DateFormat.getTimeInstance().format(startDate)
        val endStr = DateFormat.getTimeInstance().format(endDate)
        return "$startStr - $endStr"
    }
}

data class TrainingSummary(
    val trainingID: Long,
    val startDateTime: String,
    val endDateTime: String,
    val centreName: String,
    val photoUrl: String,
    val numberOfTrainees: Int,
    val traineesLimit: Int,
    val trainingStatus: String
)


data class OfferResponse(
    val id: Long,
    val pricePerHour: Float,
    val trainerID: Long,
    val sportID: Long,
    val sportName: String
)

interface Detailable : Summarisable {
    val rating : Float
    val price : String?
}

class TrainerVM(private val trainer: Trainer) :
    Detailable {
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

class SportVM(private val sport: Sport):
    Summarisable {
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

class SportCentreVM(private val sportCentre : SportCentre) :
    Detailable {
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

class TrainingVM(private val training: Training) :
    Summarisable {
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

class PassVM(private val pass: Pass) :
    Summarisable {
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


