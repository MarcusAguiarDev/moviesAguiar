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

class RecycleViewAdapter(private val dataSet: List<Movie>) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.imdbID = dataSet[position].imdbID
        viewHolder.movieTitle.text = dataSet[position].title
        viewHolder.movieYear.text = dataSet[position].year

        //Get image from URL or set a default image
        if(dataSet[position].poster != "N/A")
            Glide.with(viewHolder.itemView).load(dataSet[position].poster).into(viewHolder.movieImage)
        else
            viewHolder.movieImage.setImageResource(R.drawable.default_image)

        viewHolder.itemView.setOnClickListener {
            //viewHolder.imdbID
            val intent = Intent(viewHolder.itemView.context, MovieDetail::class.java).apply {
                putExtra("imdbID", viewHolder.imdbID)
            }
            viewHolder.itemView.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}