package com.example.myfirstapp
import android.content.Context
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

val OPEN_WEATHER_MAP_API ="http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric"

public fun getJSON(context: Context, city: String): JSONObject? {
    try {
        val url: URL = URL(String.format(OPEN_WEATHER_MAP_API, city))
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.addRequestProperty("x-api-key", context.getString(R.string.open_weather_maps_app_id))
        val reader: BufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val json = StringBuffer(1024)
        var tmp = ""

        while (reader.readLine() != null) {
            tmp = reader.readLine()
            json.append(tmp).append("\n")
        }
        reader.close()
        val data: JSONObject = JSONObject(json.toString())
        if (data.getInt("cod") != 200)
            return null
        return data
    } catch (e: Exception) {
        return null
    }
}