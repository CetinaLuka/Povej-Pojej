package feri.itk.pojejinpovej.Util

import android.util.Log
import java.time.LocalDate
import java.util.*
import java.time.temporal.ChronoUnit.*
import java.util.concurrent.TimeUnit

object DateToStringConverter {
    fun convertDateToString(date: LocalDate): String{
        Log.i("date", date.toString())
        val days = DAYS.between(date, LocalDate.now())
        Log.i("date", days.toString())
        val months = days/30
        val years = months/12

        var result = ""
        if (years>0){
            when(years){
                1.toLong()-> result = "pred 1 letom"
                2.toLong() -> result = "pred 2 letoma"
                else -> result = "pred $years leti"
            }
        }
        else if(months>0){
            when(months){
                1.toLong() -> result = "pred 1 mesecem"
                2.toLong() -> result = "pred 2 mesecema"
                else -> result = "pred $months meseci"
            }
        }
        else{
            Log.i("date", "days")
            when(days){
                0.toLong() -> {result = "danes"}
                1.toLong() -> result = "pred 1 dnevom"
                2.toLong() -> result = " pred 2 dnevoma"
                else -> result = "pred $days dnevi"
            }
        }
        Log.i("date", result)
        return  result
    }
}