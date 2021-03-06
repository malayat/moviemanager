package ec.solmedia.themoviedb.domain

import android.content.SharedPreferences
import android.util.Log
import ec.solmedia.themoviedb.VimoApp
import ec.solmedia.themoviedb.api.VimoAPI
import ec.solmedia.themoviedb.model.Media
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException
import javax.inject.Inject

class RequestMediaCommand @Inject constructor(
        private val api: VimoAPI,
        private val sPref: SharedPreferences,
        private val dataMapper: DataMapper) : Command<Media> {

    private var media = Media()

    override fun execute(mediaType: String, category: String, error: () -> Unit, successful: (Media) -> Unit) {
        Log.d("MediaFragment", "actualPage: ${media.page} totalPage: ${media.totalPages}")
        doAsync {
            if (media.page < media.totalPages) {
                val callResponse = api.get(mediaType, category, media.page + 1,
                        sPref.getString(VimoApp.LOCALE_KEY, "en-US")
                )
                val response = try {
                    callResponse.execute()
                } catch (e: IOException) {
                    Log.e("RequestMediaCommand", e.message)
                    null
                }

                uiThread {
                    response?.let {
                        if (response.isSuccessful) {
                            val mediaResponse = response.body()
                            val mediaConverted = dataMapper.convertFromApiModel(mediaResponse)
                            media = mediaConverted
                            successful(mediaConverted)
                        } else {
                            Log.e("RequestMediaCommand", response.message())
                            error()
                        }
                    } ?: kotlin.run {
                        error()
                    }

                }
            }
        }
    }
}