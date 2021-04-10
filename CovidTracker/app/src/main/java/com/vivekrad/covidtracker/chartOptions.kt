package com.vivekrad.covidtracker

enum class Metric {
    dCASES, cCASES, dDEATHS, cDEATHS
}

enum class TimeScale(val numDays: Int) {
    Week(7),
    Month(30),
    Max(-1)

}
