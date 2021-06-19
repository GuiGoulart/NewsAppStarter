package severo.io.newsappstarter.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import severo.io.newsappstarter.model.NewsResponse
import severo.io.newsappstarter.util.Constants.Companion

interface NewsApi {

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "br",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Companion.API_KEY
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Companion.API_KEY
    ): Response<NewsResponse>

}