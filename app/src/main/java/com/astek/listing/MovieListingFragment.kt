package com.astek.listing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astek.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ModelAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.items.ModelAbstractItem
import kotlinx.android.synthetic.main.fragment_movie_listing.*

class MovieListingFragment : Fragment(R.layout.fragment_movie_listing) {

    companion object {
        @JvmStatic
        fun newInstance() = MovieListingFragment()
        const val NUMBER_OF_COLUMNS_IN_LIST = 2
    }

    private val moviesItemFactory = MoviesItemFactory()
    private val itemAdapter: ModelAdapter<MoviesModelItemWrapper<*>, ModelAbstractItem<*, *>> =
        ModelAdapter(
            moviesItemFactory
        )
    private val fastAdapter: FastAdapter<AbstractItem<out RecyclerView.ViewHolder>> =
        FastAdapter.with(listOf(itemAdapter))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesRecyclerview.apply {
            layoutManager = GridLayoutManager(requireActivity(), NUMBER_OF_COLUMNS_IN_LIST)
            adapter = fastAdapter
        }

    }




}
