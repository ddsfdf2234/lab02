package com.example.lab02

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {


    private lateinit var star1: ImageButton
    private lateinit var star2: ImageButton
    private lateinit var star3: ImageButton
    private lateinit var star4: ImageButton
    private lateinit var star5: ImageButton
    private val stars = mutableListOf<ImageButton>()


    private lateinit var commentEditText: EditText
    private lateinit var clearButton: Button
    private lateinit var saveButton: Button

    private lateinit var mondayValue: TextView
    private lateinit var tuesdayValue: TextView
    private lateinit var wednesdayValue: TextView
    private lateinit var thursdayValue: TextView
    private lateinit var fridayValue: TextView
    private lateinit var saturdayValue: TextView
    private lateinit var sundayValue: TextView

    private var currentRating = 0
    private val weekMoods = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
        initWeekData()
    }

    private fun initViews() {

        star1 = findViewById(R.id.star1)
        star2 = findViewById(R.id.star2)
        star3 = findViewById(R.id.star3)
        star4 = findViewById(R.id.star4)
        star5 = findViewById(R.id.star5)

        stars.add(star1)
        stars.add(star2)
        stars.add(star3)
        stars.add(star4)
        stars.add(star5)


        commentEditText = findViewById(R.id.commentEditText)
        clearButton = findViewById(R.id.clearButton)
        saveButton = findViewById(R.id.saveButton)


        mondayValue = findViewById(R.id.mondayValue)
        tuesdayValue = findViewById(R.id.tuesdayValue)
        wednesdayValue = findViewById(R.id.wednesdayValue)
        thursdayValue = findViewById(R.id.thursdayValue)
        fridayValue = findViewById(R.id.fridayValue)
        saturdayValue = findViewById(R.id.saturdayValue)
        sundayValue = findViewById(R.id.sundayValue)
    }

    private fun setupClickListeners() {

        star1.setOnClickListener { setRating(1) }
        star2.setOnClickListener { setRating(2) }
        star3.setOnClickListener { setRating(3) }
        star4.setOnClickListener { setRating(4) }
        star5.setOnClickListener { setRating(5) }


        clearButton.setOnClickListener {
            clearForm()
        }


        saveButton.setOnClickListener {
            saveMood()
        }
    }

    private fun setRating(rating: Int) {
        currentRating = rating


        for (i in stars.indices) {
            if (i < rating) {
                stars[i].setImageResource(android.R.drawable.star_on)
            } else {
                stars[i].setImageResource(android.R.drawable.star_off)
            }
        }

        Toast.makeText(this, "Оценка: $rating", Toast.LENGTH_SHORT).show()
    }

    private fun clearForm() {

        setRating(0)


        commentEditText.text.clear()

        Toast.makeText(this, "Форма очищена", Toast.LENGTH_SHORT).show()
    }

    private fun saveMood() {
        if (currentRating == 0) {
            Toast.makeText(this, "Пожалуйста, выберите оценку настроения", Toast.LENGTH_LONG).show()
            return
        }

        val comment = commentEditText.text.toString()
        val dayOfWeek = getCurrentDayOfWeek()


        weekMoods[dayOfWeek] = currentRating
        updateStatistics()

        val message = if (comment.isNotEmpty()) {
            "Сохранено!\nОценка: $currentRating\nКомментарий: $comment"
        } else {
            "Сохранено!\nОценка: $currentRating"
        }

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()


        clearForm()
    }

    private fun getCurrentDayOfWeek(): String {
        val dayOfWeek = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            2 -> "ПН"
            3 -> "ВТ"
            4 -> "СР"
            5 -> "ЧТ"
            6 -> "ПТ"
            7 -> "СБ"
            1 -> "ВС"
            else -> "ПН"
        }
    }

    private fun initWeekData() {

        weekMoods["ПН"] = 4
        weekMoods["ВТ"] = 3
        weekMoods["СР"] = 5
        weekMoods["ЧТ"] = 2
        weekMoods["ПТ"] = 4
        weekMoods["СБ"] = 5
        weekMoods["ВС"] = 4

        updateStatistics()
    }

    private fun updateStatistics() {

        mondayValue.text = weekMoods["ПН"]?.toString() ?: "-"
        tuesdayValue.text = weekMoods["ВТ"]?.toString() ?: "-"
        wednesdayValue.text = weekMoods["СР"]?.toString() ?: "-"
        thursdayValue.text = weekMoods["ЧТ"]?.toString() ?: "-"
        fridayValue.text = weekMoods["ПТ"]?.toString() ?: "-"
        saturdayValue.text = weekMoods["СБ"]?.toString() ?: "-"
        sundayValue.text = weekMoods["ВС"]?.toString() ?: "-"


        updateColorForDay(mondayValue, weekMoods["ПН"])
        updateColorForDay(tuesdayValue, weekMoods["ВТ"])
        updateColorForDay(wednesdayValue, weekMoods["СР"])
        updateColorForDay(thursdayValue, weekMoods["ЧТ"])
        updateColorForDay(fridayValue, weekMoods["ПТ"])
        updateColorForDay(saturdayValue, weekMoods["СБ"])
        updateColorForDay(sundayValue, weekMoods["ВС"])
    }

    private fun updateColorForDay(textView: TextView, value: Int?) {
        val color = when (value) {
            1 -> "#FFCDD2"
            2 -> "#FFE0B2"
            3 -> "#FFF9C4"
            4 -> "#C8E6C9"
            5 -> "#B2DFDB"
            else -> "#E0E0E0"
        }
        textView.setBackgroundColor(Color.parseColor(color))
    }
}