package com.pwr.commonplatform.data

import com.pwr.commonplatform.data.model.trainee.*
import java.util.*

object MockData {

    /**
     * Following properties return Producers (out variance annotation, <out T>)
     * That means you cannot add anything to such collection
     * You are only allowed to read from the collection, but
     * you are sure that the elements read are subtypes of T,
     * e.g. subtypes of Summarisable in case of <out Summarisable>
     */
    val trainersSummaries: List<Summarisable>
        get() = trainersVMs

    val trainersDetails: List<Detailable>
        get() = trainersVMs

    val centresSummaries: List<Summarisable>
        get() = sportCentresVMs

    val centresDetails: List<Detailable>
        get() = sportCentresVMs

    val sportsSummaries: List<Summarisable>
        get() = sportsVMs

    val trainingsSummaries: List<Summarisable>
        get() = trainingsVMs

    val passesSummaries: List<Summarisable>
        get() = passesVMs

    val timeSlots = listOf("12:00 - 13:30", "13:30 - 15:00", "15:00 - 16:30")

    val sports = listOf(
        Sport(
            "Tennis",
            "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        ),
        Sport(
            "Gym",
            "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        ),
        Sport(
            "Box",
            "https://images.pexels.com/photos/163403/box-sport-men-training-163403.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
        )
    )

    private val sportsVMs = sports.map {
        SportVM(
            it
        )
    }

    val trainers = listOf(
        Trainer(
            "John",
            "Doe",
            "Surfing instructor",
            "fpafkpakfwoa@coepia.pp",
            "000000000",
            20f,
            4.8f,
            arrayOf("https://images.pexels.com/photos/1546139/pexels-photo-1546139.jpeg?cs=srgb&dl=chlopak-czas-wolny-deska-surfingowa-dorosly-1546139.jpg&fm=jpg")
        ),
        Trainer(
            "Bob",
            "Smith",
            "Trainer",
            "fpafkpakfwoa@coepia.pp",
            "000000000",
            20f,
            4.8f,
            arrayOf("https://images.pexels.com/photos/1546139/pexels-photo-1546139.jpeg?cs=srgb&dl=chlopak-czas-wolny-deska-surfingowa-dorosly-1546139.jpg&fm=jpg")
        )
    )

    private val trainersVMs = trainers.map {
        TrainerVM(
            it
        )
    }

    val sportCentres = listOf(
        SportCentre(
            "Surfpoint",
            "https://www.surfpoint.pl/wp-content/uploads/2015/04/team-biegnie-compressor.jpg",
            5.0f
        ),
        SportCentre(
            "Fitness Academy",
            "https://i.ytimg.com/vi/0-f1meMXtCI/maxresdefault.jpg",
            3.2f
        )
    )

    private val sportCentresVMs = sportCentres.map {
        SportCentreVM(
            it
        )
    }

    val trainings = listOf(
        Training(
            trainers[0],
            Date(),
            Date(),
            sportCentres[0],
            4,
            sports[0],
            80f,
            Training.Status.ACCEPTED
        ),
        Training(
            trainers[1],
            Date(),
            Date(),
            sportCentres[1],
            1,
            sports[1],
            20f,
            Training.Status.PROPOSED
        )
    )

    private val trainingsVMs = trainings.map {
        TrainingVM(
            it
        )
    }

    val passes = listOf(
        Pass(
            "MultiSport",
            "https://fitness-legionowo.pl/wp-content/uploads/2017/06/MultiSportPlus.jpg"
        ),
        Pass(
            "FitProfit",
            "http://www.pcs-belchatow.pl/media/k2/items/cache/e9b0f857826ab994b1b1904c051bbf59_XL.jpg"
        )
    )

    private val passesVMs: List<Summarisable> = passes.map {
        PassVM(
            it
        )
    }

    val imagesUrls = listOf(
        "https://www.fit-academy.pl/wp-content/uploads/kompleksowy-1024x683-1.jpg",
        "https://www.fit-academy.pl/wp-content/uploads/IMG_4355-1024x683.jpg",
        "https://s5.dziennik.pl/pliki/11132000/11132862-jerzy-brzeczek-900-555.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSglmhcjYdbPXi-PJL4JBmZoRREdEmRPX9awimdsXqlUa7kuNxAWQ&s"
    )

    val sportNames = listOf(
        "Karate", "Judo", "Windsurfing", "Baseball", "Gym"
    )


}