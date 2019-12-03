package sekhah.lane.composertrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_trivia.*
import kotlin.random.Random

class TriviaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)

        // Our data
        val composers = arrayOf("Beethoven", "Mozart", "Tchaikovsky", "Mahler", "Bach")
        val birthYears = arrayOf(1770, 1756, 1840, 1860, 1685)
        val deathYears = arrayOf(1827, 1791, 1893, 1911, 1750)

        // Our random pick
        val random = Random.nextInt(0, composers.size)
        val composer = composers[random]

        // Pick at random birth or death question
        val questionAboutBirth = Random.nextInt(0, 2) == 0

        // Generate text for question
        val question = "In what year ${if (questionAboutBirth) "was" else "did"} ${composer} ${if (questionAboutBirth) "born?" else "die?"}"
        questionTextView.text = question
        val correctAnswer = if (questionAboutBirth) birthYears[random] else deathYears[random]

        statusTextView.text = correctAnswer.toString()


        finishButton.setOnClickListener { finish() }


    }
}
