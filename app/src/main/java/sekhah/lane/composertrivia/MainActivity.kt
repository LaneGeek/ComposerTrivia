package sekhah.lane.composertrivia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val history = arrayListOf<String?>()
        history.add("First")
        history.add("Second")

        textView5.text = history.toString()








        answerQuestionButton.setOnClickListener {
            val intent = Intent(this, TriviaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        viewStatisticsButton.setOnClickListener {
            val intent = Intent(this, StatisticsActivity::class.java)
            intent.putExtra("History", history)
            startActivity(intent)
        }
    }
}
