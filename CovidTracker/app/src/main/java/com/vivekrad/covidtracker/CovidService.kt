package com.vivekrad.covidtracker



import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CovidService {



    @GET("v1/data?filters=areaType%3Dnation%3BareaName%3Dengland&structure=%7B%22date%22%3A%22date%22%2C%22name%22%3A%22areaName%22%2C%22code%22%3A%22areaCode%22%2C%22dailyCases%22%3A%22newCasesByPublishDate%22%2C%22cumulativeCases%22%3A%22cumCasesByPublishDate%22%2C%22dailyDeaths%22%3A%22newDeathsByDeathDate%22%2C%22cumulativeDeaths%22%3A%22cumDeathsByDeathDate%22%7D")
    fun getNationalData(): Call<CovidData>



}


