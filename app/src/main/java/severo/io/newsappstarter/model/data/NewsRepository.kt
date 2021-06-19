package severo.io.newsappstarter.model.data

import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.db.ArticleDatabase

class NewsRepository (private val db: ArticleDatabase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

}