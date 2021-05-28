package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.animation.Animation
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
class MoviesAdapter(movieClickable: IMovieClickable, movieLongClickable: IMovieLongClickable) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder?>() {
    private val movies = ArrayList<Movie>()
    private val iMovieClickable = movieClickable
    private val iMovieLongClickable = movieLongClickable

    fun addItems(moviesList: ArrayList<Movie>) {
        movies.addAll(moviesList)
    }

    fun clear() {
        movies.clear()
    }

    fun getData(): List<Movie> {
        return movies
    }

    fun getMovieByPosition(positon: Int): Movie {
        return movies[positon]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = movies[position]
        holder.title.text = move.title
        holder.dateRelease.text = move.releaseDate

        Glide.with(holder.poster)
            .load(move.posterPath)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.ic_no_image)
            .into(holder.poster)

        holder.itemView.rootView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener, OnLongClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val dateRelease: TextView = itemView.findViewById(R.id.dateRelease)
        val poster: AppCompatImageView = itemView.findViewById(R.id.poster)

        override fun onClick(v: View) {
            val anim: Animation = AnimationUtils.loadAnimation(v.context, R.anim.translate)
            v.startAnimation(anim)
            iMovieClickable.onMovieClick(
                getAbsoluteAdapterPosition(),
                movies[getAbsoluteAdapterPosition()]
            )
        }

        override fun onLongClick(v: View): Boolean {
            iMovieLongClickable.onMovieLongClick(
                getAbsoluteAdapterPosition(),
                movies[getAbsoluteAdapterPosition()]
            )
            return true
        }

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }
}