package severo.io.newsappstarter.ui.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_news.view.*
import severo.io.newsappstarter.R
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.util.Util

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_news, parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(imageViewArticle)
            textViewTitle.text = article.title
            textViewDescription.text = Html.fromHtml(article.description, Html.FROM_HTML_MODE_COMPACT)
            textViewSource.text = Html.fromHtml("<b>${resources.getString(R.string.source)}</b> ${article.source?.name}", Html.FROM_HTML_MODE_COMPACT)
            textViewPublichedAt.text = Html.fromHtml("<b>${resources.getString(R.string.published_at)}</b> ${
                Util.convertDateFormat(article.publishedAt, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd/MM/yyy - HH:mm"
                ) }", Html.FROM_HTML_MODE_COMPACT)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(article)
                }
            }

        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
}