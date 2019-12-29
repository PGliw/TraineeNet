package com.pwr.trainwithme.training_proposal

import android.app.Application
import androidx.lifecycle.*
import com.pwr.trainwithme.TrainingNetApplication
import com.pwr.trainwithme.data.Summarisable
import com.pwr.trainwithme.data.TraineeTrainingDTO
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.util.*

class TrainingProposalViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "TrainingProposalVM"
    }

    /**
     * setting day causes change to dayLiveData which can be observed by UI
     */
    var day = Date()
        set(value) {
            field = value
            _dayLiveData.value = value
        }
    private val _dayLiveData = MutableLiveData<Date>(Date())
    val dayLiveData: LiveData<Date> = _dayLiveData

    // private val _timeSlotsLiveData = MutableLiveData<>

    private val _isTimeSlotSet = MutableLiveData<Boolean>(false)
    val isTimeSlotSet: LiveData<Boolean> = _isTimeSlotSet

    private var startDateTime: DateTime? = null
    private var endDateTime: DateTime? = null
    val timeRangeFormatted: String?
        get() {
            return if (startDateTime == null || endDateTime == null) null
            else {
                "${startDateTime?.toString()} - ${endDateTime?.toString()}"
            }
        }

    // TODO replace it with actual time slots
    fun setTimeSlot(position: Int?) {
        if (position == null) {
            startDateTime = null
            endDateTime = null
            _isTimeSlotSet.value = false
        } else {
            val date = LocalDate(day)
            val hours = listOf(7, 9, 11, 13, 15, 17)
            val chosenHour = when (position) {
                in 0..hours.size -> hours[position]
                else -> hours[0]
            }
            val startDT = DateTime().withDate(date).withTime(chosenHour, 0, 0, 0)
            val endDT = DateTime(startDT).plusHours(2)
            startDateTime = startDT
            endDateTime = endDT
            _isTimeSlotSet.value = true
        }
    }

    // TODO replace it with actual time slots
    val timeSlots = listOf(
        "7:00 - 9:00",
        "9:00 - 11:00",
        "11:00 - 13:00",
        "13:00 - 15:00",
        "15:00 - 17:00",
        "17:00 - 19:00"
    )

    /**
     *  setting trainerID != null causes fetching trainerDetails if they (trainingDetails) are observed
     */
    fun setTrainerIdAndNameAndPhotoUrl(id: Long, name: String, photoUrl: String) {
        trainerID = id
        trainerName = name
        trainerPhotoUrl = photoUrl
    }
    var trainerID: Long? = null
        private set(value) {
            if (value != null) trainerIdLiveData.value = value
            field = value
        }
    var trainerName: String? = null
        private set
    var trainerPhotoUrl: String? = null
        private set
    private val trainerIdLiveData = MutableLiveData<Long>()


    /**
     *  setting sportID != null causes changing sportLiveData which causes changes to UI
     */
    fun setSportIdAndName(id: Long, name: String) {
        sportID = id
        sportName = name
    }

    var sportID: Long? = null
        private set(value) {
            if (value != null) _sportIdLiveData.value = value
            field = value
        }
    var sportName: String? = null
        private set
    private val _sportIdLiveData = MutableLiveData<Long>()
    val sportIdLiveData: LiveData<Long> = _sportIdLiveData


    /**
     *  setting centreID != null causes changing sportLiveData which causes changes to UI
     */
    fun setCentreIdAndName(id: Long, name: String) {
        centreID = id
        centreName = name
    }
    var centreID: Long? = null
        private set(value) {
            if (value != null) _centreIdLiveData.value = value
            field = value
        }
    var centreName: String? = null
        private set
    private val _centreIdLiveData = MutableLiveData<Long>()
    val centreIdLiveData: LiveData<Long> = _centreIdLiveData

    private val dataSource = (application as TrainingNetApplication).dataSource

    fun sendTrainingProposal() = dataSource.load {
        val formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        if (startDateTime == null || endDateTime == null) throw NullPointerException("dates = null")
        val startDateTimeStr = formatter.print(startDateTime)
        val endDateTimeStr = formatter.print(endDateTime)
        val dto = TraineeTrainingDTO(
            startDateTime = startDateTimeStr,
            endDateTime = endDateTimeStr,
            sportID = sportID ?: throw NullPointerException("sportID = null"),
            trainerID = trainerID ?: throw NullPointerException("trainerID = null"),
            centreID = centreID ?: throw java.lang.NullPointerException("centreID = null")
        )
        dataSource.trainingNetAPI.postTraineeTraining(dto)
    }

    val trainerDetails = trainerIdLiveData.switchMap {
        liveData {
            emitSource(dataSource.load {
                dataSource.trainingNetAPI.getTrainerDetails(it)
            })
        }
    }

    // fetch upcoming trainings for the trainer TODO - later
    private val trainings = trainerIdLiveData.switchMap {
        liveData {
            emitSource(dataSource.load {
                dataSource.trainingNetAPI.getTrainerUpcomingTrainings(it)
            })
        }
    }

    // centres overviews
    val centresOverviews = trainerIdLiveData.switchMap {
        liveData {
            emitSource(dataSource.load {
                dataSource.trainingNetAPI.getTrainerCentres(it)
            })
        }
    }

    // trainers overviews
    val trainersOverviews = dataSource.load {
        dataSource.trainingNetAPI.getTrainersOverviews()
    }

    // trainers cards
    val trainersSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getTrainersSummaries()
    }

    // sports cards
    val sportsSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getSportsSummaries()
    }

    // centres cards
    val centresSummaries = dataSource.load<List<Summarisable>> {
        dataSource.trainingNetAPI.getCentresSummaries()
    }
}
