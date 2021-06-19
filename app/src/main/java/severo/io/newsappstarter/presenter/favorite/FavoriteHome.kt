package severo.io.newsappstarter.presenter.favorite

import severo.io.newsappstarter.model.Article

interface FavoriteHome {

    interface Presenter{
        fun onSuccess(article: List<Article>)
    }

}