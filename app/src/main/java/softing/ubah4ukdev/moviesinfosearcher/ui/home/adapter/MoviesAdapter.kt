package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.domain.Movie

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
class MoviesAdapter(movieClickable: IMovieClickable) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder?>() {
    private val movies = ArrayList<Movie>()
    private val iMovieClickable = movieClickable

    fun addItems(moviesList: ArrayList<Movie>) = movies.addAll(moviesList)

    fun clear() = movies.clear()

    fun getData(): ArrayList<Movie> = movies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        with(holder) {
            title.text = movie.title
            dateRelease.text = movie.releaseDate
            Glide.with(poster)
                .load(movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.drawable.ic_no_image)
                .into(poster)
            itemView.rootView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)
        }
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val dateRelease: TextView = itemView.findViewById(R.id.dateRelease)
        val poster: AppCompatImageView = itemView.findViewById(R.id.poster)

        override fun onClick(v: View) =
            iMovieClickable.onMovieClick(
                absoluteAdapterPosition,
                movies[absoluteAdapterPosition]
            )

        init {
            itemView.setOnClickListener(this)
        }
    }
}