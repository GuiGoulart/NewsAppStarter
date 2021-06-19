package severo.io.newsappstarter.model.data

import android.content.Context
import kotlinx.coroutines.*
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.db.ArticleDatabase
import severo.io.newsappstarter.network.RetrofitInstance
import severo.io.newsappstarter.presenter.favorite.FavoriteHome
import severo.io.newsappstarter.presenter.news.NewsHome
import severo.io.newsappstarter.presenter.search.SearchHome

class NewsDataSource(context: Context) {

    private var db: ArticleDatabase = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

    fun getBreakingNews(callback: NewsHome.Presenter){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews()
            if(response.isSuccessful){
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(terms: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(terms)
            if(response.isSuccessful){
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article){
        GlobalScope.launch (Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter){
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main){
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?){
        GlobalScope.launch(Dispatchers.Main) {
            article?.let { articleSafe ->
                newsRepository.delete(articleSafe)
            }
        }
    }

}