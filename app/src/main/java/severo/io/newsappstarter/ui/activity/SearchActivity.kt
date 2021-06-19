package severo.io.newsappstarter.ui.activity

import android.app.Dialog
import android.content.Intent
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import severo.io.newsappstarter.R
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.search.SearchPresenter
import severo.io.newsappstarter.ui.adapter.HomeAdapter
import severo.io.newsappstarter.util.Util
import severo.io.newsappstarter.util.UtilQueryTextListener

class SearchActivity : AbstractActivity(), ViewHome.View {

    private val searchAdapter by lazy {
        HomeAdapter()
    }

    private lateinit var presenter: SearchPresenter
    private var dialog: Dialog? = null

    override fun getLayout(): Int = R.layout.activity_search

    override fun onInject() {

        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this, dataSource)
        configRecycler()
        search()
        clickAdapter()

    }

    private fun search() {
        searchView.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){
                newText -> newText?.let { query ->
                    if (query.isNotEmpty()){
                        presenter.search(query)
                    }else{
                        presenter.search("")
                    }
                }
            }
        )
    }

    private fun configRecycler(){
        with(rvSearch) {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter(){
        searchAdapter.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showProgressBar() {
        dialog = Util.loadingDialog(this)
        dialog!!.show()
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun hideProgressBar() {
        if (dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun showArticles(articles: List<Article>) {
        searchAdapter.differ.submitList(articles.toList())
    }
}