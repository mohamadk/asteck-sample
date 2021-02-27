package com.astek.listing.items

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.astek.R
import com.astek.listing.ItemMovieModel
import com.astek.listing.MovieListingItemTypes
import com.astek.listing.MoviesModelItemWrapper
import com.bumptech.glide.Glide
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

        Glide.with(holder.itemView)
            .load(itemMovieModel.image)
            .into(holder.itemView.backgroundImageView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
