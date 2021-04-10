package com.vivekrad.covidtracker


import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class CovidData(
    @Expose
    val data : List<NationData>,
)

data class NationData (
    val date: String,
    val dailyCases: Int,
    val cumulativeCases: Int,
    val dailyDeaths: Int,
    val cumulativeDeaths: Int,
)

