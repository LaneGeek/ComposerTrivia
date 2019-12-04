package sekhah.lane.composertrivia

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var correctTotal = 0
    private var incorrectTotal = 0
    private var history = arrayListOf<String?>()
    private var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get stored data and update the scorecard
        sharedPrefs = getSharedPreferences("Trivia", 0)
        correctTotal = sharedPrefs!!.getInt("CorrectTotal", 0)
        incorrectTotal = sharedPrefs!!.getInt("IncorrectTotal", 0)
        val historySet = sharedPrefs!!.getStringSet("History", setOf())
        // Shared preferences can only store a set, not a list so we have to convert back-and-forth
        historySet?.forEach { x -> history.add(x) }
        updateScorecard()

        // Go to TriviaActivity
        answerQuestionButton.setOnClickListener {
            val intent = Intent(this, TriviaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Go to HistoryActivity with history data
        viewHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putExtra("History", history)
            startActivity(intent)
        }

        // Reset all history
        deleteHistoryButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("HISTORY WILL BE ERASED!")
            builder.setMessage("Are you sure you want to do this?")
            builder.setPositiveButton("Yes") { _, _ ->
                correctTotal = 0
                incorrectTotal = 0
                history = arrayListOf()
                val editor = sharedPrefs?.edit()
                editor?.putInt("CorrectTotal", correctTotal)
                editor?.putInt("IncorrectTotal", incorrectTotal)
                editor?.putStringSet("History", history.toMutableSet())
                editor?.apply()
                updateScorecard()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.create().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Get the results from TriviaActivity
        val question = data?.getStringExtra("Question") ?: ""
        val correctAnswer = data?.getIntExtra("CorrectAnswer", 0)
        val userAnswer = data?.getIntExtra("UserAnswer", 0)
        val questionTime = data?.getStringExtra("TimeDate") ?: ""
        val correct = correctAnswer == userAnswer

        // Update the scores and scorecard
        if (correct)
            correctTotal++
        else
            incorrectTotal++
        updateScorecard()

        // Log entry to history
        history.add("Time & Date: $questionTime\nQuestion: $question\nYour answer of $userAnswer was ${if (correct) "correct." else "incorrect."}")

        // Save data
        val editor = sharedPrefs?.edit()
        editor?.putInt("CorrectTotal", correctTotal)
        editor?.putInt("IncorrectTotal", incorrectTotal)
        editor?.putStringSet("History", history.toMutableSet())
        editor?.apply()
    }

    private fun updateScorecard() {
        totalQuestionsTextView.text = (correctTotal + incorrectTotal).toString()
        correctTextView.text = correctTotal.toString()
        incorrectTextView.text = incorrectTotal.toString()
        percentageTextView.text = (correctTotal / (correctTotal + incorrectTotal).toFloat() * 100).toInt().toString()
    }
}
