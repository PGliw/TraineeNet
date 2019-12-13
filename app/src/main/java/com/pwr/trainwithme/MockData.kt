package com.pwr.trainwithme

object MockData {
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
        TrainerOfferFacade(
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
        TrainerOfferFacade(
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
        SportCentreFacade(
            SportCentre(
                "Surfpoint",
                "https://www.surfpoint.pl/wp-content/uploads/2015/04/team-biegnie-compressor.jpg",
                5.0f
            )
        ),
        SportCentreFacade(
            SportCentre(
                "Fitness Academy",
                "https://i.ytimg.com/vi/0-f1meMXtCI/maxresdefault.jpg",
                3.2f
            )
        )
    )

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

}