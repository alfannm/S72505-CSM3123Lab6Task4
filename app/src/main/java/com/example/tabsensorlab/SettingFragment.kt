package com.example.tabsensorlab

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment

// SettingFragment allows the user to configure and save
// application preferences such as username, theme, and font size
class SettingFragment : Fragment(R.layout.fragment_setting) {

    // List of available theme options for the Spinner
    private val themes = listOf("Blue", "Light", "Dark")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link UI components from fragment_setting.xml
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val spTheme = view.findViewById<Spinner>(R.id.spTheme)
        val seekFont = view.findViewById<SeekBar>(R.id.seekFont)
        val tvFontLabel = view.findViewById<TextView>(R.id.tvFontLabel)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        // Spinner setup to display available theme options
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            themes
        )
        spTheme.adapter = adapter

        // SeekBar listener to update font size label dynamically
        // A minimum font size of 12sp is enforced
        seekFont.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                val size = if (progress < 12) 12 else progress
                tvFontLabel.text = "Font Size: $size"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Not required for this implementation
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Not required for this implementation
            }
        })

        // Load previously saved preferences into the form fields
        loadPrefsToForm(etUsername, spTheme, seekFont, tvFontLabel)

        // Save button click handler
        btnSave.setOnClickListener {

            // Save current form values to SharedPreferences
            savePrefs(etUsername, spTheme, seekFont)

            // Provide user feedback after saving
            Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
        }

        // Feedback message indicating Settings tab is opened
        Toast.makeText(requireContext(), "Settings opened", Toast.LENGTH_SHORT).show()
    }

    // Loads saved preferences and populates the settings form
    private fun loadPrefsToForm(
        etUsername: EditText,
        spTheme: Spinner,
        seekFont: SeekBar,
        tvFontLabel: TextView
    ) {

        // Access SharedPreferences using predefined file name
        val sp = requireActivity()
            .getSharedPreferences(Prefs.FILE_NAME, Context.MODE_PRIVATE)

        // Retrieve saved values or use defaults
        val username = sp.getString(Prefs.KEY_USERNAME, "")
        val theme = sp.getString(Prefs.KEY_THEME, "Blue")
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        // Populate UI fields with saved values
        etUsername.setText(username)
        spTheme.setSelection(themes.indexOf(theme).coerceAtLeast(0))
        seekFont.progress = fontSize
        tvFontLabel.text = "Font Size: $fontSize"
    }

    // Saves user input from the form into SharedPreferences
    private fun savePrefs(
        etUsername: EditText,
        spTheme: Spinner,
        seekFont: SeekBar
    ) {

        // Validate and prepare values for saving
        val username = etUsername.text
            .toString()
            .trim()
            .ifEmpty { "Guest" }

        val theme = spTheme.selectedItem.toString()
        val size = if (seekFont.progress < 12) 12 else seekFont.progress

        // Store values persistently using SharedPreferences
        val sp = requireActivity()
            .getSharedPreferences(Prefs.FILE_NAME, Context.MODE_PRIVATE)

        sp.edit()
            .putString(Prefs.KEY_USERNAME, username)
            .putString(Prefs.KEY_THEME, theme)
            .putInt(Prefs.KEY_FONT_SIZE, size)
            .apply()
    }
}
