package severo.io.newsappstarter.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import severo.io.newsappstarter.R
import severo.io.newsappstarter.databinding.ActivityHomeBinding
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.news.NewsPresenter
import severo.io.newsappstarter.ui.adapter.HomeAdapter
import severo.io.newsappstarter.util.Util

class HomeActivity : AppCompatActivity(), ViewHome.View {

    private val homeAdapter by lazy {
        HomeAdapter()
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: NewsPresenter
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
        configRecycler()
        clickAdapter()

    }

    private fun configRecycler(){
        with(binding.rvNews) {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@HomeActivity, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter(){
        homeAdapter.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showProgressBar() {
        dialog = Util.loadingDialog(this)
        dialog!!.show()
    }

    override fun hideProgressBar() {
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showArticles(articles: List<Article>) {
        homeAdapter.differ.submitList(articles.toList())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.search_menu -> {
                Intent(this, SearchActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.favorite_menu -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}