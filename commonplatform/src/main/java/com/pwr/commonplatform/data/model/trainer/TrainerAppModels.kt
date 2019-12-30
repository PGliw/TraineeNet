package com.pwr.commonplatform.data.model.trainer

import com.pwr.commonplatform.data.model.trainee.Summary

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

data class TrainerTrainingDetails(
    val trainingID: Long,
    val startDateTime: String,
    val endDateTime: String,
    val centreID: Long,
    val centreName: String,
    val centrePhotoUrl: String,
    val centreLatitude: Float,
    val centreLongitude: Float,
    val numberOfTrainees: Int,
    val traineesLimit: Int,
    val trainingStatus: String,
    val sportName: String,
    val traineesSummaries: List<Summary>
)