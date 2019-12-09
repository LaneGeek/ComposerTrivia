package sekhah.lane.composertrivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Extract the data from MainActivity, sort by serial number and reverse it so most recent is at top
        var history = intent.getStringArrayListExtra("History")?.sorted()?.reversed()

        // Remove serial numbers (first 8 characters) for viewing
        history = history?.map { x -> x.drop(8) }

        // Render the list view
        if (history?.size != 0)
            listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, history as MutableList<String>)

        finishButton.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        // Disable the back button
    }
}
