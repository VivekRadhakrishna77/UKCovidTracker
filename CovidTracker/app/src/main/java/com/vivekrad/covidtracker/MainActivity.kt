package com.vivekrad.covidtracker

import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.robinhood.spark.animation.LineSparkAnimator
import com.vivekrad.covidtracker.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.util.*


private const val TAG = "MainActivity"

private const val BASE_URL = "https://api.coronavirus.data.gov.uk/"


class MainActivity : AppCompatActivity() {

    private lateinit var currentData: List<NationData>
    private lateinit var adapter: CovidSparkAdapter
    private lateinit var binding: ActivityMainBinding
    //val counties = listOf("Bedfordshire",	"Berkshire",	"Bristol",	"Buckinghamshire",	"Cambridgeshire",	"Cheshire",	"Cornwall",	"County Durham",	"Cumbria",	"Derbyshire",	"Devon",	"Dorset",	"East Riding of Yorkshire",	"East Sussex",	"Essex",	"Gloucestershire",	"Greater London",	"Greater Manchester",	"Hampshire",	"Herefordshire",	"Hertfordshire",	"Isle of Wight",	"Kent",	"Lancashire",	"Leicestershire",	"Lincolnshire",	"Merseyside",	"Norfolk",	"North Yorkshire",	"Northamptonshire",	"Northumberland",	"Nottinghamshire",	"Oxfordshire",	"Rutland",	"Shropshire",	"Somerset",	"South Yorkshire",	"Staffordshire",	"Suffolk",	"Surrey",	"Tyne and Wear",	"Warwickshire",	"West Midlands",	"West Sussex",	"West Yorkshire",	"Wiltshire",	"Worcestershire")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val gson = GsonBuilder().setDateFormat("dd-MM-yyyy").create()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()



        val covidNationalService = retrofit.create(CovidService::class.java)


        // Fetch National Data
        covidNationalService.getNationalData().enqueue(object : Callback<CovidData> {

            override fun onFailure(call: Call<CovidData>, t: Throwable) {
                Log.e(TAG, "On Failure $t")
            }

            override fun onResponse(call: Call<CovidData>, response: Response<CovidData>) {
                Log.i(TAG, "on Response $response")

                // Covert Data to DataClass List
                val rawData = gson.toJsonTree(response.body()).asJsonObject.get("data").asJsonArray

                val collectionType = object : TypeToken<Collection<NationData?>?>() {}.type


                var rawNationalData = gson.fromJson<List<NationData>>(rawData, collectionType)

                val nationalData = rawNationalData.reversed()


                setupEventListeners()


                if (nationalData == null) {
                    Log.w(TAG, "Did not receive Valid response body")
                    return
                }

                updateDisplayWithData(nationalData)

            }

        })

    }






    private fun setupEventListeners() {

        @Override
        //add a listener for the user scrubbing over it.

        binding.sparkView.isScrubEnabled = true
        binding.sparkView.setScrubListener { itemData ->
            if(itemData is NationData) {
                updateInfoForDate(itemData)

            }


        }
        binding.RadioGroupTimescale.setOnCheckedChangeListener { _, checkedId ->
            adapter.daysAgo = when (checkedId){
                R.id.radioButtonWeek -> TimeScale.Week
                R.id.radioButtonMonth -> TimeScale.Month
                else -> TimeScale.Max
            }
            adapter.notifyDataSetChanged()
        }

        binding.RadioGroupMetric.setOnCheckedChangeListener { _, checkedId ->

            when(checkedId){
                R.id.radioButtonCumulativeCases -> updateDisplayMetric(Metric.cCASES)
                R.id.radioButtonDailyDeaths -> updateDisplayMetric(Metric.dDEATHS)
                R.id.radioButtonCumulativeDeaths -> updateDisplayMetric(Metric.cDEATHS)
                else -> updateDisplayMetric(Metric.dCASES)
            }

        }

    }

    private fun updateDisplayMetric(metric: Metric) {
        //update Colour of chart
        val colorRes = when (metric){
            Metric.dCASES -> R.color.purple_500
            Metric.cCASES -> R.color.teal_700
            Metric.dDEATHS -> R.color.orange
            Metric.cDEATHS -> R.color.red
        }
        @ColorInt val colorInt = ContextCompat.getColor(this, colorRes)
        binding.sparkView.lineColor = colorInt
        binding.tvMetricLabel.setTextColor(colorInt)
        binding.tvDateLabel.setTextColor(colorInt)
        // update metric on adapter
        adapter.metric = metric
        adapter.notifyDataSetChanged()



        // reset date and value shown in textboxes
        updateInfoForDate(currentData[currentData.size - 2])
    }


    private fun updateDisplayWithData(dailyData: List<NationData>) {
        currentData = dailyData

        //create new sparkAdapter
        adapter = CovidSparkAdapter(dailyData)

        binding.sparkView.adapter = adapter
        // update radio buttons to select defaults
        binding.radioButtonDailyCases.isChecked = true
        binding.radioButtonMax.isChecked = true


        updateDisplayMetric(Metric.dCASES)

    }


    private fun updateInfoForDate(covidData: NationData) {

        val numCases = when (adapter.metric){
            Metric.dCASES -> covidData.dailyCases
            Metric.cCASES -> covidData.cumulativeCases
            Metric.dDEATHS -> covidData.dailyDeaths
            Metric.cDEATHS -> covidData.cumulativeDeaths

        }

        binding.tvMetricLabel.text = NumberFormat.getInstance().format(numCases)

        val date = covidData.date
        this.binding.tvDateLabel.text = date


    }


}
