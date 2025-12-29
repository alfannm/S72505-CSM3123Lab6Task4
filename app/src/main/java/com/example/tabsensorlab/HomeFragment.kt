package com.example.tabsensorlab

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

// HomeFragment displays saved user preferences such as
// username, theme, and font size
class HomeFragment : Fragment(R.layout.fragment_home) {

    // TextViews used to display title and preference values
    private lateinit var tvTitle: TextView
    private lateinit var tvUser: TextView
    private lateinit var tvTheme: TextView
    private lateinit var tvFont: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link UI components from fragment_home.xml
        tvTitle = view.findViewById(R.id.tvHomeTitle)
        tvUser = view.findViewById(R.id.tvHomeUser)
        tvTheme = view.findViewById(R.id.tvHomeTheme)
        tvFont = view.findViewById(R.id.tvHomeFont)
    }

    override fun onResume() {
        super.onResume()

        // Apply saved preferences whenever the fragment becomes visible
        applyPrefs()

        // Simple feedback to indicate the Home tab is opened
        Toast.makeText(requireContext(), "Home opened", Toast.LENGTH_SHORT).show()
    }

    // Reads saved preferences and applies them to the UI
    private fun applyPrefs() {

        // Access SharedPreferences using predefined file name
        val sp = requireActivity().getSharedPreferences(
            Prefs.FILE_NAME,
            Context.MODE_PRIVATE
        )

        // Retrieve saved values or use defaults if not found
        val username = sp.getString(Prefs.KEY_USERNAME, "Guest") ?: "Guest"
        val theme = sp.getString(Prefs.KEY_THEME, "Blue") ?: "Blue"
        val fontSize = sp.getInt(Prefs.KEY_FONT_SIZE, 18)

        // Update text content based on saved preferences
        tvUser.text = "User: $username"
        tvTheme.text = "Theme: $theme"
        tvFont.text = "Font Size: ${fontSize}sp"

        // Apply font size to all text elements
        val size = fontSize.toFloat()
        tvTitle.textSize = size
        tvUser.textSize = size
        tvTheme.textSize = size
        tvFont.textSize = size

        // Apply background and text colors based on selected theme
        val root = requireView()
        when (theme) {
            "Dark" -> {
                root.setBackgroundColor(0xFF121212.toInt())
                setTextColor(0xFFFFFFFF.toInt())
            }
            "Light" -> {
                root.setBackgroundColor(0xFFFFFFFF.toInt())
                setTextColor(0xFF000000.toInt())
            }
            else -> { // Default Blue theme
                root.setBackgroundColor(0xFFE3F2FD.toInt())
                setTextColor(0xFF0D47A1.toInt())
            }
        }
    }

    // Helper function to apply the same text color
    // to all TextViews in this fragment
    private fun setTextColor(color: Int) {
        tvTitle.setTextColor(color)
        tvUser.setTextColor(color)
        tvTheme.setTextColor(color)
        tvFont.setTextColor(color)
    }
}
