package severo.io.newsappstarter.model.db

import androidx.room.*
import severo.io.newsappstarter.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) : Long

    @Query("SELECT * FROM article")
    fun getAll(): List<Article>

    @Delete
    suspend fun delete(article: Article)
}