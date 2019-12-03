package sekhah.lane.composertrivia

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var correctTotal = 0
    private var incorrectTotal = 0
    private val history = arrayListOf<String?>()
    private var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get stored data
        sharedPrefs = getSharedPreferences("Trivia", 0)
        correctTotal = sharedPrefs!!.getInt("CorrectTotal", 0)
        incorrectTotal = sharedPrefs!!.getInt("IncorrectTotal", 0)
        val a = sharedPrefs!!.getStringSet("History", setOf())
        a?.forEach { x -> history.add(x) }

        //textView5.text = a.toString()

        updateScorecard()

        // Go to TriviaActivity
        answerQuestionButton.setOnClickListener {
            val intent = Intent(this, TriviaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        //Go to StatisticsActivity
        viewStatisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            // Send the history
            intent.putExtra("History", history)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Get the results from TriviaActivity
        val question = data?.getStringExtra("Question") ?: ""
        val correctAnswer = data?.getIntExtra("CorrectAnswer", 0)
        val userAnswer = data?.getIntExtra("UserAnswer", 0)
        val questionTime = data?.getStringExtra("TimeDate") ?: ""
        val correct = correctAnswer == userAnswer

        // Check to see the question was asked and so the back button was not pressed
        if (question != "") {

            // Update the scores
            if (correct)
                correctTotal++
            else
                incorrectTotal++

            // Update the scorecard
            updateScorecard()

            // Log entry to history
            history.add("Time & Date: $questionTime\nQuestion: $question\nYour answer of $userAnswer was ${if (correct) "correct." else "incorrect."}")

            // Save data
            val editor = sharedPrefs?.edit()
            editor?.putInt("CorrectTotal", correctTotal)
            editor?.putInt("IncorrectTotal", incorrectTotal)
            // Shared preferences can only save a set not a list so we convert here
            //val a: MutableSet<String?> = history.toMutableSet()
            editor?.putStringSet("History", history.toMutableSet())
            editor?.apply()



        }
    }

    private fun updateScorecard() {
        totalQuestionsTextView.text = (correctTotal + incorrectTotal).toString()
        correctTextView.text = correctTotal.toString()
        incorrectTextView.text = incorrectTotal.toString()
        percentageTextView.text = (correctTotal / (correctTotal + incorrectTotal).toFloat() * 100).toInt().toString()
    }

}
