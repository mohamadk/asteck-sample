package com.astek.listing.adapter.listing

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.astek.R
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.MoviesModelItemWrapper
import com.mikepenz.fastadapter.items.ModelAbstractItem

class LoadingItem(wrapper: MoviesModelItemWrapper<*>) :
    ModelAbstractItem<MoviesModelItemWrapper<*>, RecyclerView.ViewHolder>(wrapper) {
    override val layoutRes: Int = R.layout.item_loading
    override val type: Int = MovieListingItemTypes.LoadingItemType.value

    override fun getViewHolder(v: View): RecyclerView.ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
