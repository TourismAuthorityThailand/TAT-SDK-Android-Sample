package org.tat.sdksample.event.eventdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import org.tat.sdksample.R
import org.th.tatsdk.ServiceRequestListener
import org.th.tatsdk.common.TATLanguage
import org.th.tatsdk.event.TATEvents
import org.th.tatsdk.event.TATGetEventDetailParameter
import org.th.tatsdk.event.TATGetEventDetailResult

class EventDetailActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var name: View? = null
    private var province: View? = null
    private var eventType: View? = null
    private var tel: View? = null
    private var web: View? = null
    private var detail: View? = null
    private var tvNoResult: TextView? = null
    private var scrollView: ScrollView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        setupToolbar()

        progressBar = findViewById(R.id.progress)
        name = findViewById(R.id.name)
        province = findViewById(R.id.address)
        eventType = findViewById(R.id.type)
        tel = findViewById(R.id.tel)
        web = findViewById(R.id.url)
        detail = findViewById(R.id.detail)
        tvNoResult = findViewById(R.id.tv_event_detail_not_found)
        scrollView = findViewById(R.id.scrollView2)

        (name!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Name"
        (province!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Province"
        (eventType!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Event Type"
        (tel!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Tel."
        (web!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Website"
        (detail!!.findViewById<View>(R.id.tv_detail_title) as TextView).text = "Detail"

        val eventId = intent.getStringExtra("eventId")

        progressBar!!.visibility = View.VISIBLE
        val parameter = TATGetEventDetailParameter(eventId, TATLanguage.ENGLISH)
        TATEvents.getDetailAsync(parameter, object : ServiceRequestListener<TATGetEventDetailResult> {
            override fun onResponse(result: TATGetEventDetailResult?) {
                result?.let {
                    scrollView!!.visibility = View.VISIBLE
                    tvNoResult!!.visibility = View.GONE
                    progressBar!!.visibility = View.GONE

                    if (result.name != null && result.name.isNotEmpty()) {
                        (name!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = result.name
                    } else {
                        (name!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }

                    if (result.location != null && result.location.isNotEmpty()) {
                        (province!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = result.location
                    } else {
                        (province!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }

                    if (result.information != null && result.information?.eventType?.isNotEmpty()==true) {
                        (eventType!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = result.information?.eventType?.get(0)
                    } else {
                        (eventType!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }
                    if (result.information != null && result.information?.htmlDetail?.isNotEmpty() == true) {
                        (detail!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = htmlToString(result.information?.htmlDetail?:"")
                    } else {
                        (detail!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }

                    if (result.tatContact?.phones?.isNotEmpty() == true) {
                        (tel!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = result.tatContact?.phones?.get(0)
                    } else {
                        (tel!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }

                    if (result.tatContact?.urls?.isNotEmpty() == true) {
                        (web!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = result.tatContact?.urls?.get(0)
                    } else {
                        (web!!.findViewById<View>(R.id.tv_detail_content) as TextView).text = "-"
                    }
                }
            }

            override fun onError(errorMessage: String?, statusCode: Int) {
                progressBar!!.visibility = View.GONE
                scrollView!!.visibility = View.GONE
                tvNoResult!!.visibility = View.VISIBLE
                Toast.makeText(this@EventDetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupToolbar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setTitle(R.string.event_detail)
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun htmlToString(html: String): Spanned {
        return Html.fromHtml(html)
    }
}
