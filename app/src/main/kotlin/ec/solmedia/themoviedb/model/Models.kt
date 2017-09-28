package ec.solmedia.themoviedb.model

import ec.solmedia.themoviedb.commons.adapter.AdapterConstants
import ec.solmedia.themoviedb.commons.adapter.ViewType

data class Media(
        val page: Int = 0,
        val totalResults: Int = 0,
        val totalPages: Int = 1,
        val mediaItems: List<MediaItem> = emptyList())

data class MediaItem(val id: Int,
                     val title: String?,
                     val name: String?,
                     val overView: String,
                     val firstAirDate: String?,
                     val releaseDate: String?,
                     val genreIds: Array<Int>,
                     val originalTitle: String?,
                     val originalName: String?,
                     val originalLanguage: String,
                     //val originalCountry: String?,
                     val popularity: Float,
                     val voteAverage: Float,
                     val voteCount: Int,
                     val posterPath: String,
                     val backDropPath: String
) : ViewType {

    override fun getViewType(): Int = AdapterConstants.MEDIA
}
