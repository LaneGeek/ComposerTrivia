package sekhah.lane.composertrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_statistics.*

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        // We extract the data we need from MainActivity
        val history = intent.getStringArrayExtra("History")

        // Render the list view
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, history)
    }
}
