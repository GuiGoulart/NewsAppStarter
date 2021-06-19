package severo.io.newsappstarter.ui.adapter

import android.annotation.SuppressLint
import android.text.Html
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_news.view.*
import severo.io.newsappstarter.R
import severo.io.newsappstarter.databinding.AdapterNewsBinding
import severo.io.newsappstarter.model.Article
import severo.io.newsappstarter.util.Util
import kotlin.coroutines.coroutineContext

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binging: AdapterNewsBinding) : RecyclerView.ViewHolder(binging.root)

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
            AdapterNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        with(holder){
            with(differ.currentList[position]){
                Glide.with(holder.itemView.context).load(urlToImage).into(binging.imageViewArticle)
                binging.textViewTitle.text = title
                binging.textViewDescription.text = Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT)
                binging.textViewSource.text = Html.fromHtml("Fonte:</b> ${source?.name}", Html.FROM_HTML_MODE_COMPACT)
                binging.textViewPublichedAt.text = Html.fromHtml("<b>Publicado em:</b> ${
                    Util.convertDateFormat(publishedAt, "yyyy-MM-dd'T'HH:mm:ss'Z'", "dd/MM/yyy - HH:mm"
                    ) }", Html.FROM_HTML_MODE_COMPACT)

                onItemClickListener?.let { click ->
                    click(this)
                }

            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
}