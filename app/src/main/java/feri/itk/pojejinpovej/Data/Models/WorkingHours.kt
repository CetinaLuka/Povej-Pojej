package feri.itk.pojejinpovej.Data.Models

import android.util.Log
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

data class WorkingHours(
    var mon: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm"))),
    var tue: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm"))),
    var wed: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm"))),
    var thu: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm"))),
    var fri: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("20:00", DateTimeFormatter.ofPattern("H:mm"))),
    var sat: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm"))),
    var sun: DailyHours = DailyHours(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("H:mm")), LocalTime.parse("21:00", DateTimeFormatter.ofPattern("H:mm")))
) {
    fun workingHoursToday(): String{
        val todaysHours = returnTodaysHours()
        return todaysHours.opens.toString()+"-"+todaysHours.closes.toString()
    }
    fun isOpenNow(): Boolean{
        val todaysHours = returnTodaysHours()
        val currentTime = LocalTime.now()
        if((currentTime >= todaysHours.opens && currentTime < todaysHours.closes)){
            return true
        }
        else{
            return true
        }
    }
    fun returnTodaysHours(): DailyHours{
        val dayOfTheWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        when(dayOfTheWeek){
            1 -> {
                return sun
            }
            2 -> {
                return mon
            }
            3 -> {
                return tue
            }
            4 -> {
                return wed
            }
            5 -> {
                return thu
            }
            6 -> {
                return fri
            }
            7 -> {
                return sat
            }
            else ->{
                return mon
            }
        }
    }
}