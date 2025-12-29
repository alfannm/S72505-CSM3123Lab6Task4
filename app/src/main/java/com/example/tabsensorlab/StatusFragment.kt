package com.example.tabsensorlab

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

// StatusFragment displays real-time light sensor data
// and applies user-selected preferences to the UI
class StatusFragment : Fragment(R.layout.fragment_status), SensorEventListener {

    // Sensor-related components
    // sensorManager manages access to device sensors
    // lightSensor represents the ambient light sensor
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    // UI components
    // progressBar visualizes light intensity
    // tvLux displays the current lux value
    private lateinit var progressBar: ProgressBar
    private lateinit var tvLux: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link UI components from fragment_status.xml
        progressBar = view.findViewById(R.id.lightProgress)
        tvLux = view.findViewById(R.id.tvLuxValue)

        // Initialize SensorManager using activity context
        sensorManager = requireActivity()
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Retrieve the device's default light sensor
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Handle devices without a light sensor
        if (lightSensor == null) {
            tvLux.text = "Light sensor not available"
            progressBar.progress = 0
        }
    }

    override fun onResume() {
        super.onResume()

        // Apply saved theme and font size preferences to the UI
        applyPrefsToUi()

        // Register light sensor listener when fragment becomes visible
        lightSensor?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        // Feedback message indicating Status tab is opened
        Toast.makeText(requireContext(), "Status opened", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()

        // Unregister sensor listener to stop updates and save battery
        sensorManager.unregisterListener(this)
    }

    // Called whenever the light sensor provides new data
    override fun onSensorChanged(event: SensorEvent?) {

        // Ensure the event is from the light sensor
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {

            // Extract lux value from sensor event
            val lux = event.values[0].toInt()

            // Update text display with current light intensity
            tvLux.text = "$lux Lux"

            // Update progress bar while limiting value to range 0–300
            progressBar.progress = lux.coerceIn(0, 300)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Accuracy changes are not required for this lab
    }

    // Applies saved theme and font size preferences to this fragment
    private fun applyPrefsToUi() {

        // Access SharedPreferences using predefined file name
        val sp = requireActivity()
            .getSharedPreferences(Prefs.FILE_NAME, Context.MODE_PRIVATE)

        // Retrieve saved values or use defaults
        val theme = sp.getString(Prefs.KEY_THEME, "Blue") ?: "Blue"
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        // Apply font size to lux TextView
        tvLux.textSize = fontSize.toFloat()

        // Get root view of the fragment for background styling
        val root = requireView()

        // Apply background and text color based on selected theme
        when (theme) {
            "Dark" -> {
                root.setBackgroundColor(0xFF121212.toInt())
                tvLux.setTextColor(0xFFFFFFFF.toInt())
            }
            "Light" -> {
                root.setBackgroundColor(0xFFFFFFFF.toInt())
                tvLux.setTextColor(0xFF000000.toInt())
            }
            else -> { // Default Blue theme
                root.setBackgroundColor(0xFFF1F8E9.toInt())
                tvLux.setTextColor(0xFF1B5E20.toInt())
            }
        }
    }
}
