package com.astek.listing.adapter.listing

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.astek.R
import com.astek.app.imageloader.GlideApp
import com.astek.listing.adapter.MovieListingItemTypes
import com.astek.listing.adapter.MoviesModelItemWrapper
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.item_movie.view.*

class SingleTitleItem(wrapper: MoviesModelItemWrapper<*>) :
    ModelAbstractItem<MoviesModelItemWrapper<*>, RecyclerView.ViewHolder>(wrapper) {
    override val layoutRes: Int = R.layout.item_movie
    override val type: Int = MovieListingItemTypes.SingleTitleItemType.value

    private val itemMovieModel = wrapper.model as ItemMovieModel

    override fun getViewHolder(v: View): RecyclerView.ViewHolder {
        return ViewHolder(v)
    }

    override fun bindView(holder: RecyclerView.ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)
        holder.itemView.titleTextView.text = itemMovieModel.title

        GlideApp.with(holder.itemView)
            .load(itemMovieModel.image)
            .into(holder.itemView.backgroundImageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
