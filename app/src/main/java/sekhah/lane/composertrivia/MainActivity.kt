package sekhah.lane.composertrivia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var correctTotal = 0
    private var incorrectTotal = 0
    private val history = arrayListOf<String?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val questionTime = data?.getStringExtra("QuestionTime") ?: ""
        val correct = correctAnswer == userAnswer

        // Update the scorecard
        if (correct)
            correctTotal++
        else
            incorrectTotal++
        totalQuestionsTextView.text = (correctTotal + incorrectTotal).toString()
        correctTextView.text = correctTotal.toString()
        incorrectTextView.text = incorrectTotal.toString()
        percentageTextView.text = (correctTotal / (correctTotal + incorrectTotal).toFloat() * 100).toInt().toString()

        // Log entry to history
        val entry = "Time & Date: $questionTime\nQuestion: $question\nYour answer of $userAnswer was ${if (correct) "correct." else "incorrect."}"
        history.add(entry)
    }
}
