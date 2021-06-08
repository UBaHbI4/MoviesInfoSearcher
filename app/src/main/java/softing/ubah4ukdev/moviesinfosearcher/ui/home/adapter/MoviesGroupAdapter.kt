package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import softing.ubah4ukdev.moviesinfosearcher.R
import softing.ubah4ukdev.moviesinfosearcher.domain.model.MovieGroup

/****
Project Movies info searcher
Package softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter

Created by Ivan Sheynmaer

2021.05.03
v1.0
 */
class MoviesGroupAdapter(movieClickable: IMovieClickable) :
    RecyclerView.Adapter<MoviesGroupAdapter.ViewHolder?>() {
    private val movieGroups = ArrayList<MovieGroup>()
    private val iMovieClickable = movieClickable

    private val adapters: ArrayList<MoviesAdapter> = arrayListOf()

    private val pool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    fun addItems(movieGroupList: ArrayList<MovieGroup>) = movieGroups.addAll(movieGroupList)

    fun clear() = movieGroups.clear()

    fun getData(): ArrayList<MovieGroup> = movieGroups

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movies_group,
                parent,
                false
            ).also {
                adapters.add(MoviesAdapter(iMovieClickable))
            }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = movieGroups[position]

        adapters[position].clear()
        adapters[position].addItems(movieGroups[position].movies)

        with(holder) {
            groupName.text = move.group
            moviesRV.adapter = adapters[position]
            moviesRV.setRecycledViewPool(pool)
        }
    }

    override fun getItemCount() = movieGroups.size

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val groupName: TextView = itemView.findViewById(R.id.groupName)
        val moviesRV: RecyclerView = itemView.findViewById(R.id.moviesRV)
    }
}