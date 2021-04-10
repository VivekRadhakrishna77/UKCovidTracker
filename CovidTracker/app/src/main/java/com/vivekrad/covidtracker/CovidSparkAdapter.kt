package com.vivekrad.covidtracker

import android.graphics.RectF
import com.robinhood.spark.SparkAdapter

class CovidSparkAdapter(private val dailyData: List<NationData>) : SparkAdapter() {

    var metric  = Metric.dCASES
    var daysAgo = TimeScale.Max

    override fun getCount() = dailyData.size

    override fun getItem(index: Int) = dailyData[index]

    override fun getY(index: Int): Float {
        val chosenData = getItem(index)
        return when (metric){
            Metric.dCASES -> chosenData.dailyCases.toFloat()
            Metric.cCASES -> chosenData.cumulativeCases.toFloat()
            Metric.dDEATHS -> chosenData.dailyDeaths.toFloat()
            Metric.cDEATHS-> chosenData.cumulativeDeaths.toFloat()
        }

    }

    override fun getDataBounds(): RectF {
        var bounds = super.getDataBounds()
        if(daysAgo != TimeScale.Max){
            bounds.left = count - daysAgo.numDays.toFloat()
        }
        if(metric==Metric.cDEATHS) {
            bounds.right = (count-1).toFloat()
        }

        return bounds
    }

}
