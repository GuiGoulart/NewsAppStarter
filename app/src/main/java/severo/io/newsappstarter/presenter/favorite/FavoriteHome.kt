package severo.io.newsappstarter.presenter.favorite

import severo.io.newsappstarter.model.Article

interface FavoriteHome {

    fun showArticles(article: List<Article>)

}