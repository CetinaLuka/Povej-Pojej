package feri.itk.pojejinpovej.Util

import java.util.*
import java.util.concurrent.TimeUnit

object DateToStringConverter {
    fun convertDateToString(date: Date): String{
        val diff = Date().time - date.time
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val months = days/30
        val years = months/12

        var result = "pred "
        if (years>0){
            when(years){
                1.toLong()-> result+"1 letom"
                2.toLong() -> result+"2 letoma"
                else -> result+"$years leti"
            }
        }
        else if(months>0){
            when(months){
                1.toLong() -> result+"1 mesecem"
                2.toLong() -> result+"2 mesecema"
                else -> result+"$months meseci"
            }
        }
        else{
            when(days){
                0.toLong() -> {result = "danes"}
                1.toLong() -> result+"1 dnevom"
                2.toLong() -> result+"2 dnevoma"
                else -> result+"$days dnevi"
            }
        }
        return  result
    }
}