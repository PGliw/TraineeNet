package com.pwr.commonplatform.data.model.trainer

data class TrainerTrainingOverview(
    val trainingID: Long,
    val startDateTime: String,
    val endDateTime: String,
    val centreName: String,
    val photoUrl: String,
    val numberOfTrainees: Int,
    val traineesLimit: Int,
    val trainingStatus: String,
    val sportName: String
)