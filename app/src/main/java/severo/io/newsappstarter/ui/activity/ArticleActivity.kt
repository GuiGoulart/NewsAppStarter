package severo.io.newsappstarter.ui.activity

import android.webkit.WebViewClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_article.*
import severo.io.newsappstarter.R
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.favorite.FavoritePresenter

class ArticleActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun getLayout(): Int = R.layout.activity_article

    override fun onInject() {
        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        floatingActionButton.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, R.string.article_saved_successful, Snackbar.LENGTH_LONG).show()
        }

    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {}

}