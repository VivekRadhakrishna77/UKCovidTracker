package com.vivekrad.covidtracker



import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CovidService {



    @GET("v1/data?filters=areaType%3Dnation%3BareaName%3Dengland&structure=%7B%22date%22%3A%22date%22%2C%22name%22%3A%22areaName%22%2C%22code%22%3A%22areaCode%22%2C%22dailyCases%22%3A%22newCasesByPublishDate%22%2C%22cumulativeCases%22%3A%22cumCasesByPublishDate%22%2C%22dailyDeaths%22%3A%22newDeathsByDeathDate%22%2C%22cumulativeDeaths%22%3A%22cumDeathsByDeathDate%22%7D")
    fun getNationalData(): Call<CovidData>

    // data?filters=areaType=nation;areaName=england&structure={"date":"date","name":"areaName","code":"areaCode","dailyCases":"newCasesByPublishDate","cumulativeCases":"cumCasesByPublishDate","dailyDeaths":"newDeathsByDeathDate","cumulativeDeaths":"cumDeathsByDeathDate"}

}


// API encoded link
// "https://api.coronavirus.data.gov.uk/v1/data?filters=areaType%3Dregion%3BareaName%3Deast+midlands&structure=%7B%22date%22%3A%22date%22%2C%22name%22%3A%22areaName%22%2C%22code%22%3A%22areaCode%22%2C%22casesDaily%22%3A%22newCasesBySpecimenDate%22%2C%22casesCumulative%22%3A%22cumCasesBySpecimenDate%22%2C%22deathsDaily%22%3A%22newDeathsByDeathDate%22%2C%22deathsCumulative%22%3A%22cumDeathsByDeathDate%22%7D"

// API Decoded link
// https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=nation;areaName=england&structure={"date":"date","name":"areaName","code":"areaCode","cases":{"daily":"newCasesByPublishDate","cumulative":"cumCasesByPublishDate"},"deaths":{"daily":"newDeathsByDeathDate","cumulative":"cumDeathsByDeathDate"}}

// https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=nation;areaName=england&structure={"date":"date","name":"areaName","code":"areaCode","dailyCases":"newCasesByPublishDate","cumulativeCases":"cumCasesByPublishDate","dailyDeaths":"newDeathsByDeathDate","cumulativeDeaths":"cumDeathsByDeathDate"}


// Regional data link
// https://api.coronavirus.data.gov.uk/v1/data?filters=areaType=region;areaName=${region}&structure={"date":"date","name":"areaName","code":"areaCode","cases":{"daily":"newCasesBySpecimenDate","cumulative":"cumCasesBySpecimenDate"},"deaths":{"daily":"newDeathsByDeathDate","cumulative":"cumDeathsByDeathDate"}}