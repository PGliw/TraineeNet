package com.pwr.trainwithme

import java.util.*

object MockData {

    /**
     * Following properties return Producers (out variance annotation, <out T>)
     * That means you cannot add anything to such collection
     * You are only allowed to read from the collection, but
     * you are sure that the elements read are subtypes of T,
     * e.g. subtypes of Summarisable in case of <out Summarisable>
     */
    val trainersSummaries : Array<out Summarisable>
        get() = trainers

    val trainersDetails : Array<out Detailable>
        get() = trainers

    val centresSummaries : Array<out Summarisable>
        get() = sportCentres

    val centresDetails : Array<out Detailable>
        get() = sportCentres

    val sportsSummaries : Array<out Summarisable>
        get() = sports

    val trainingsSummaries : Array<out Summarisable>
        get() = trainings

    val passesSummaries: Array<out Summarisable>
        get() = passes

    val timeSlots = arrayOf("12:00 - 13:30", "13:30 - 15:00", "15:00 - 16:30")

    private val sports = arrayOf(
        SportFacade(
            Sport(
                "Tennis",
                "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            )
        ),
        SportFacade(
            Sport(
                "Gym",
                "https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            )
        ),
        SportFacade(
            Sport(
                "Box",
                "https://images.pexels.com/photos/163403/box-sport-men-training-163403.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
            )
        )
    )

    private val trainers = arrayOf(
        TrainerVM(
            Trainer(
                "John",
                "Doe",
                "Surfing instructor",
                "fpafkpakfwoa@coepia.pp",
                "000000000",
                20f,
                4.8f,
                arrayOf("https://images.pexels.com/photos/1546139/pexels-photo-1546139.jpeg?cs=srgb&dl=chlopak-czas-wolny-deska-surfingowa-dorosly-1546139.jpg&fm=jpg")
            )
        ),
        TrainerVM(
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
    )
    private val sportCentres = arrayOf(
        SportVM(
            SportCentre(
                "Surfpoint",
                "https://www.surfpoint.pl/wp-content/uploads/2015/04/team-biegnie-compressor.jpg",
                5.0f
            )
        ),
        SportVM(
            SportCentre(
                "Fitness Academy",
                "https://i.ytimg.com/vi/0-f1meMXtCI/maxresdefault.jpg",
                3.2f
            )
        )
    )

    private val trainings = arrayOf(
        TrainingVM(
            Training(
                Date(), 1.5f, 10, Sport(
                    "Tennis",
                    "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                )
            )
        ),
        TrainingVM(
            Training(
                Date(), 2.0f, 10, Sport(
                    "Tennis",
                    "https://images.pexels.com/photos/1432039/pexels-photo-1432039.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
                )
            )
        )
    )

    private val passes = arrayOf(
        PassVM(
            Pass(
                "MultiSport",
                "https://fitness-legionowo.pl/wp-content/uploads/2017/06/MultiSportPlus.jpg"
            )
        ),
        PassVM(
            Pass(
                "FitProfit",
                "http://www.pcs-belchatow.pl/media/k2/items/cache/e9b0f857826ab994b1b1904c051bbf59_XL.jpg"
            )
        )
    )
}