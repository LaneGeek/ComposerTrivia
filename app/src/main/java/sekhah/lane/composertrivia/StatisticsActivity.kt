package sekhah.lane.composertrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // Extract the data from MainActivity and reverse it so most recent is at top
        val history = intent.getStringArrayListExtra("History")?.reversed()

        // Render the list view
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, history as MutableList<String>)
    }
}
