package severo.io.newsappstarter.ui.activity

import android.app.Dialog
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import severo.io.newsappstarter.R
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.news.NewsPresenter
import severo.io.newsappstarter.ui.adapter.HomeAdapter
import severo.io.newsappstarter.util.Util

class HomeActivity : AbstractActivity(), ViewHome.View {

    private val homeAdapter by lazy { HomeAdapter() }
    private lateinit var presenter: NewsPresenter
    private var dialog: Dialog? = null

    override fun getLayout(): Int = R.layout.activity_home

    override fun onInject() {

        val dataSource = NewsDataSource()
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
        configRecycler()

    }

    private fun configRecycler(){
        with(rvNews) {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@HomeActivity, DividerItemDecoration.VERTICAL
                )
            )
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