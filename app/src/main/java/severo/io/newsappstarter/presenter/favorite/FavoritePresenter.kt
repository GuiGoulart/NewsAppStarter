package severo.io.newsappstarter.presenter.favorite

import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource): FavoriteHome.Presenter {

    fun getAll(){
        this.dataSource.getAllArticle(this)
    }

    fun saveArticle(article: Article){
        this.dataSource.saveArticle(article)
    }

    fun deleteArticle(article: Article){
        this.dataSource.deleteArticle(article)
    }

    override fun onSuccess(article: List<Article>) {
        this.view.showArticles(article)
    }

}