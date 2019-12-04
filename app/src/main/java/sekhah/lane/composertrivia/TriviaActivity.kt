package sekhah.lane.composertrivia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_trivia.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class TriviaActivity : AppCompatActivity() {
    private var userAnswer = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)

        // Database
        val composers = arrayOf("Beethoven", "Mozart", "Tchaikovsky", "Mahler", "Bach")
        val birthYears = arrayOf(1770, 1756, 1840, 1860, 1685)
        val deathYears = arrayOf(1827, 1791, 1893, 1911, 1750)

        // Random pick of a composer
        val random = Random.nextInt(0, composers.size)
        val composer = composers[random]

        // Random pick of birth or death question
        val questionAboutBirth = Random.nextInt(0, 2) == 0

        // Generate text for question and display it
        val question = "In what year ${if (questionAboutBirth) "was" else "did"} $composer ${if (questionAboutBirth) "born?" else "die?"}"
        questionTextView.text = question

        // This is the correct answer
        correctAnswer = if (questionAboutBirth) birthYears[random] else deathYears[random]

        // Generate buttons: 3 random years mixed with 1 real year
        // We choose a set instead of array or list because we want unique years
        val fourAnswers = mutableSetOf<Int>()
        fourAnswers.add(correctAnswer)
        while (fourAnswers.size < 4) {
            fourAnswers.add(Random.nextInt(1600, 2000)) // Years must from 1600 to 2000
        }

        // Shuffle the answers by arranging them in a nice sorted order
        val shuffledAnswers = fourAnswers.sorted()

        // Set answer buttons text to match the choices
        answer1Button.text = shuffledAnswers.elementAt(0).toString()
        answer2Button.text = shuffledAnswers.elementAt(1).toString()
        answer3Button.text = shuffledAnswers.elementAt(2).toString()
        answer4Button.text = shuffledAnswers.elementAt(3).toString()

        // Function to get the answer if any button is pressed
        fun submitAnswer() {
            answer1Button.isEnabled = false
            answer2Button.isEnabled = false
            answer3Button.isEnabled = false
            answer4Button.isEnabled = false
            finishButton.isEnabled = true

            // Is the answer correct?
            val userGotItRight = userAnswer == correctAnswer
            val message = if (userGotItRight) "Well done! Your answer of $userAnswer is correct!" else "Wrong! The actual year was $correctAnswer."

            // Display the message
            statusTextView.text = "${statusTextView.text}\n\n${message}"
        }

        answer1Button.setOnClickListener {
            userAnswer = answer1Button.text.toString().toInt()
            submitAnswer()
        }

        answer2Button.setOnClickListener {
            userAnswer = answer2Button.text.toString().toInt()
            submitAnswer()
        }

        answer3Button.setOnClickListener {
            userAnswer = answer3Button.text.toString().toInt()
            submitAnswer()
        }

        answer4Button.setOnClickListener {
            userAnswer = answer4Button.text.toString().toInt()
            submitAnswer()
        }

        finishButton.setOnClickListener {
            // Gather data to return to the MainActivity and return to it
            val intent = Intent()
            intent.putExtra("Question", question)
            intent.putExtra("CorrectAnswer", correctAnswer)
            intent.putExtra("UserAnswer", userAnswer)

            // Also send the time and date
            val sdf = SimpleDateFormat("h:mm:ssa - EEE MMM d, yyyy")
            val now = sdf.format(Date())
            intent.putExtra("TimeDate", now)
            setResult(1, intent)
            finish()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the current game state
        savedInstanceState.putString("Question", questionTextView.text.toString())
        savedInstanceState.putString("Answer1", answer1Button.text.toString())
        savedInstanceState.putString("Answer2", answer2Button.text.toString())
        savedInstanceState.putString("Answer3", answer3Button.text.toString())
        savedInstanceState.putString("Answer4", answer4Button.text.toString())
        savedInstanceState.putString("Status", statusTextView.text.toString())
        savedInstanceState.putBoolean("Button1IsEnabled", answer1Button.isEnabled)
        savedInstanceState.putBoolean("Button2IsEnabled", answer2Button.isEnabled)
        savedInstanceState.putBoolean("Button3IsEnabled", answer3Button.isEnabled)
        savedInstanceState.putBoolean("Button4IsEnabled", answer4Button.isEnabled)
        savedInstanceState.putBoolean("FinishButtonIsEnabled", finishButton.isEnabled)
        savedInstanceState.putInt("UserAnswer", userAnswer)
        savedInstanceState.putInt("CorrectAnswer", correctAnswer)

        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore the game state
        super.onRestoreInstanceState(savedInstanceState)

        questionTextView.text = savedInstanceState.getString("Question")
        answer1Button.text = savedInstanceState.getString("Answer1")
        answer2Button.text = savedInstanceState.getString("Answer2")
        answer3Button.text = savedInstanceState.getString("Answer3")
        answer4Button.text = savedInstanceState.getString("Answer4")
        statusTextView.text = savedInstanceState.getString("Status")
        answer1Button.isEnabled = savedInstanceState.getBoolean("Button1IsEnabled")
        answer2Button.isEnabled = savedInstanceState.getBoolean("Button2IsEnabled")
        answer3Button.isEnabled = savedInstanceState.getBoolean("Button3IsEnabled")
        answer4Button.isEnabled = savedInstanceState.getBoolean("Button4IsEnabled")
        finishButton.isEnabled = savedInstanceState.getBoolean("FinishButtonIsEnabled")
        userAnswer = savedInstanceState.getInt("UserAnswer")
        correctAnswer = savedInstanceState.getInt("CorrectAnswer")
    }

    override fun onBackPressed() {
        // Disable the back button
    }
}
