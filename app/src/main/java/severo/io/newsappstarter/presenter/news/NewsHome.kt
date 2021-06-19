package severo.io.newsappstarter.presenter.news

import android.content.Context
import severo.io.newsappstarter.model.NewsResponse

interface NewsHome {

    interface Presenter {
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }

}