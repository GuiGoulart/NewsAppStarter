package severo.io.newsappstarter.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import severo.io.newsappstarter.R
import severo.io.newsappstarter.databinding.ActivityFavoriteBinding
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.model.data.NewsDataSource
import severo.io.newsappstarter.presenter.ViewHome
import severo.io.newsappstarter.presenter.favorite.FavoritePresenter
import severo.io.newsappstarter.ui.adapter.HomeAdapter

class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite {

    private val favoriteAdapter by lazy {
        HomeAdapter()
    }

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var presenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        configRecycler()
        clickAdapter()

        val itemTouchPerCallback =  object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = favoriteAdapter.differ.currentList[position]
                presenter.deleteArticle(article)
                Snackbar.make(
                    viewHolder.itemView, R.string.article_delete_successful, Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)){
                        presenter.saveArticle(article)
                        favoriteAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()

    }

    private fun configRecycler(){
        with(binding.rvFavorite) {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter(){
        favoriteAdapter.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showArticles(articles: List<Article>) {
        favoriteAdapter.differ.submitList(articles.toList())
    }

}