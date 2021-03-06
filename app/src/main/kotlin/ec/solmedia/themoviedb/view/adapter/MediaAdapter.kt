package ec.solmedia.themoviedb.view.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ec.solmedia.themoviedb.commons.adapter.AdapterConstants
import ec.solmedia.themoviedb.commons.adapter.ViewType
import ec.solmedia.themoviedb.commons.adapter.ViewTypeDelegateAdapter
import ec.solmedia.themoviedb.model.MediaItem


class MediaAdapter(itemClick: (MediaItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.MEDIA, MediaDelegateAdapter(itemClick))
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = this.items[position].getViewType()

    fun addMediaItems(mediaItems: List<MediaItem>) {
        removeLoadingItem()

        // insert news and the loading at the end of the list
        items.addAll(mediaItems)
        items.add(loadingItem)
        notifyItemRangeChanged(items.size - 1, items.size + 1 /* plus loading item */)
    }

    fun clearAndAddMediaItems(mediaItems: List<MediaItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(mediaItems)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun removeLoadingItem() {
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex

    fun getMediaItems(): List<MediaItem> {
        return items
                .filter { it.getViewType() == AdapterConstants.MEDIA }
                .map { it as MediaItem }
    }
}