package softing.ubah4ukdev.moviesinfosearcher.ui.history.adapter

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
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieHistory
import softing.ubah4ukdev.moviesinfosearcher.ui.extensions.visible
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.history.adapter

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
class MovieHistoryAdapter() :
    RecyclerView.Adapter<MovieHistoryAdapter.ViewHolder?>() {
    private val movies = ArrayList<MovieHistory>()

    fun addItems(moviesList: List<MovieHistory>) = movies.addAll(moviesList)

    fun clear() = movies.clear()

    fun getData(): ArrayList<MovieHistory> = movies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_history,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]

        with(holder) {
            title.text = movie.title
            val dateString =  SimpleDateFormat("dd.MM.yy hh:mm:ss").format(Date(movie.date))
            dateView.text = dateString
            Glide.with(poster)
                .load(movie.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .circleCrop()
                .error(R.drawable.ic_no_image)
                .into(poster)
            itemView.rootView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale)
        }
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val poster: AppCompatImageView = itemView.findViewById(R.id.poster)
    }
}