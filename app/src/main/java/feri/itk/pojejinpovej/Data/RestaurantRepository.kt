package feri.itk.pojejinpovej.Data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import feri.itk.pojejinpovej.Data.Models.Restaurant
import feri.itk.pojejinpovej.Data.Models.Review

object RestaurantRepository {
    var reviews = arrayListOf(
        Review("0","Janez Novak","Zelo slaba kvaliteta hrane. Vse je bilo mrzlo in nezačinjeno. Kljub slabi hrani je ponudba zelo dobra in raznolika, natakarji pa so prijazni in ustrežljivi. Vrnem se ko menjajo kuharja.", 1.2, 3.3, 4.5),
        Review("1","Maja Čeh","Hrana je bila zelo dobra, jaz sem jedela lososa, fant pa postrv. Sveže sestavina in odlična predstavitev na krožniku. Tudi postrežba je dobra, le ponudba je precej omejena", 4.2, 3.3, 4.5),
        Review("2","Marko Janežič","Dobra restavracija, hitra postrežba in slastna hrana. Še pridemo.", 4.3, 4.0, 4.7),
        Review("3","Jaka Novak","Prvič sem jedel v tej restavraciji in verjetno tudi zadnjič. Hrana je bila podpovprečna, natakarji pa neprijazni in počasni.", 2.8, 3.1, 1.5),
        Review("4","Darko Glušič","Zalo zadovoljen s hrano in postrežbo. Nabor jedi je zelo omejen vendar se spremenijo vsk teden.", 4.9, 2.8, 4.2),
        Review("5","Marja Kranjec","Ni slabo ni pa preveč dobro. Povprečna restavracija s klasično hrano. Tudi cena je temu primerna", 3.2, 3.9, 3.5),
        Review("6","Brigita Šuštarič","Sem hodimo jest že odkar se je restavracija odprla. Nikoli še nisem bila razočarana, si pa želim da bi imeli kakšno dnevno ali tedensko ponudbo.", 5.0, 2.3, 4.5),
        Review("7","Eva Adamič","Nad hrano nimam pritožb, natakarji pa znajo biti neprijazni.", 4.8, 3.3, 3.5),
        Review("8","Gregor Kepler","Same pohvale, za hrano vedno sveže in okusno. Natakrji so prijazni in ustrežljivi vendar jih je premalo in ponavadi traja da uspejo vse postreči.", 4.1, 4.6, 3.5),
        Review("9","Herman Potočnik","Dobro se in še boljše pije. Natakarica je zelo prijazna in tudi proporoči jedi. Vse pohvale tudi kuharju za izvrstno hrano.", 4.5, 3.3, 4.5),
        Review("10","Nina Hubler","Velika izbira hrane, vendar pa postrežba in kvaliteta hrane razočarata.", 3.2, 4.3, 3.5)
    )
    private var restaurants = arrayListOf(
        Restaurant("Baščaršija", "https://dobregostilne.si/image/restavracija-bascarsija-n-750-750-922.jpg", 3.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Ancora", "https://content.selectbox.si/uploads/2014/10/darilni_paketi_restavracija_ancora_maribor_3.jpg", 3.74, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Papagayo", "https://s.inyourpocket.com/gallery/99354.jpg", 2.94, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy."),
        Restaurant("Alf", "", 2.25, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Q", "", 4.27, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Mango", "", 3.60, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Chuty's", "", 4.30, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews),
        Restaurant("Sarajevo", "", 2.80, "Poštna ulica 8", "2000", "Restaurant with popular traditional Bosnian kitchen and good location. Whatever you choose to eat you cant miss. Prices are not so bad if you are a tourist. Ambient is relaxing, you just have to find a free table,because it's busy.", reviews)
    )
    fun returnAllRestaurants(): MutableLiveData<List<Restaurant>> {
        val mutableData = MutableLiveData<List<Restaurant>>()
        mutableData.value = restaurants
        return mutableData
    }

    fun returnSuggestedRestaurants(): MutableLiveData<List<Restaurant>> {
        val mutableData = MutableLiveData<List<Restaurant>>()
        mutableData.value = restaurants
        return mutableData
    }

    fun likeReview(review: Review){
        findReview(review).reviewRating =+ 1
    }
    fun dislikeReview(review: Review){
        findReview(review).reviewRating =- 1
    }
    private fun findReview(review: Review): Review{
        for(r in reviews){
            if(r.id == review.id)
                return r
        }
        Log.i("repository", "review does not exist")
        return Review("x")
    }
}