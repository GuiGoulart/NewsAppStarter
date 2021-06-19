package severo.io.newsappstarter.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import severo.io.newsappstarter.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

internal object Util {

    @JvmStatic
    fun loadingDialog(ctx: Context?): Dialog {
        val loading = Dialog(ctx!!)
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loading.setContentView(R.layout.dialog_progress_bar)
        Objects.requireNonNull(loading.window)
            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)
        return loading
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDateFormat(
        date: String?,
        initDateFormat: String?,
        endDateFormat: String?
    ): String? {
        return try {
            val initDate = SimpleDateFormat(initDateFormat).parse(date.toString())
            val formatter = SimpleDateFormat(endDateFormat)
            formatter.format(initDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            "Erro ao obter data"
        }
    }

}

internal class UtilQueryTextListener(
    lifecycle: Lifecycle,
    private val utilQueryTextListener: (String?) -> Unit
) : SearchView.OnQueryTextListener, LifecycleObserver {

    private val coroutineScope = lifecycle.coroutineScope
    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(Constants.SEARCH_NEWS_DELAY)
                utilQueryTextListener(newText)
            }
        }
        return false
    }

}