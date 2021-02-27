package com.astek

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.astek.listing.MovieListingFragment

class MoviesListingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_listing)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.moviesListFrameLayout, MovieListingFragment.newInstance())
                .commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movies_listing_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        return true
    }
}
