package severo.io.newsappstarter.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import severo.io.newsappstarter.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Util {

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