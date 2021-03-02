package com.example.aguiarmovies.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aguiarmovies.R
import com.example.aguiarmovies.activities.MovieDetail
import com.example.aguiarmovies.models.Movie

const val NA = "N/A"

class RecycleViewAdapter(
    private val dataSet: List<Movie>,
    private val listeners: Listeners
    ) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imdbID: String? = null
        val movieTitle: TextView
        val movieYear: TextView
        val movieImage: ImageView

        init {
            // view binding
            movieTitle = view.findViewById(R.id.movieTitle)
            movieYear = view.findViewById(R.id.movieYear)
            movieImage = view.findViewById(R.id.movieImage)
        }

        fun onBind(movie: Movie) {
            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            imdbID = movie.imdbID
            movieTitle.text = movie.title
            movieYear.text = movie.year

            //Get image from URL or set a default image
            if (movie.poster != NA)
                Glide.with(itemView).load(movie.poster)
                    .into(movieImage)
            else
                movieImage.setImageResource(R.drawable.no_image_available)

            //set parent received click listener
            itemView.setOnClickListener {
                listeners.onClickListener(imdbID)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBind(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}

interface Listeners{
    fun onClickListener (imdbID: String?)
}