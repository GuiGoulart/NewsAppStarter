package severo.io.newsappstarter.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import severo.io.newsappstarter.R
import severo.io.newsappstarter.databinding.ActivityArticleBinding
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.favorite.FavoritePresenter

class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var binding: ActivityArticleBinding

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        binding.floatingActionButton.setOnClickListener {
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