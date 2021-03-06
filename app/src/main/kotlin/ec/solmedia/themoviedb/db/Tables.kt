package ec.solmedia.themoviedb.db

object DetailMediaItemTable {
    val TABLE_NAME = "DetailMediaItemTable"
    val ID = "_id"
    val OVER_VIEW = "overView"
    val HOMEPAGE = "homepage"
    val NAME = "name"
    val IN_PRODUCTION = "inProduction"
    val ORIGINAL_NAME = "originalName"
    val FIRST_AIR_DATE = "firstAirDate"
    val LAST_AIR_DATE = "lastAirDate"
    val NUMBER_OF_EPISODES = "numberOfEpisodes"
    val NUMBER_OF_SEASONS = "numberOfSeasons"
    val TYPE = "type"
    val TITLE = "title"
    val IMDB_ID = "imdbId"
    val RELEASE_DATE = "releaseDate"
    val REVENUE = "revenue"
    val ORIGINAL_TITLE = "originalTitle"
    val RUNTIME = "runtime"
    val BUDGET = "budget"
    val TAGLINE = "tagline"
    val ORIGINAL_LANGUAGE = "originalLanguage"
    val POPULARITY = "popularity"
    val VOTE_AVERAGE = "voteAverage"
    val VOTE_COUNT = "voteCount"
    val STATUS = "status"
    val POSTER_PATH = "posterPath"
    val BACK_DROP_PATH = "backDropPath"
}

object GenreTable {
    val TABLE_NAME = "GenreTable"
    val ID = "_id"
    val NAME = "name"
    val DETAIL_MEDIA_ITEM_ID = "DetailMediaItemId"
}
