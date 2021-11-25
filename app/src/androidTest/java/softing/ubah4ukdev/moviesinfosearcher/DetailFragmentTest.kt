package softing.ubah4ukdev.moviesinfosearcher

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import softing.ubah4ukdev.moviesinfosearcher.domain.model.Movie
import softing.ubah4ukdev.moviesinfosearcher.ui.detail.DetailFragment
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment

/**
 *   Project: Movies info searcher
 *
 *   Package: softing.ubah4ukdev.moviesinfosearcher
 *
 *   Created by Ivan Sheynmaer
 *
 *   Description:
 *
 *
 *   2021.11.15
 *
 *   v1.0
 */
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailFragment>

    @Before
    fun setup() {
        val bundle = Bundle().also {
            it.putParcelable(
                HomeFragment.MOVIE_ARG, getMovieForTest(
                    SIMPLE_MOVIE,
                    SIMPLE_RATING
                )
            )
        }

        scenario = launchFragmentInContainer<DetailFragment>(bundle)
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun detailFragment_AssertNotNull() {
        scenario.onFragment() {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun detailFragment_testBundle() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(SIMPLE_MOVIE))
        Espresso.onView(withId(R.id.movie_title)).check(assertion)
    }

    @Test
    fun detailFragment_RecyclerViewIsVisible() {
        Espresso.onView(withId(R.id.movie_title))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun detailFragment_AssertRating() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText("$SIMPLE_RATING"))
        Espresso.onView(withId(R.id.rating))
            .check(assertion)
    }

    private fun getMovieForTest(title: String, rating: Double = 1.0) =
        Movie(
            adult = false,
            backdropPath = "",
            genres_ids = listOf(),
            id = 0,
            originalLanguage = "",
            originalTitle = title,
            overview = "",
            popularity = 0.0,
            posterPath = "",
            releaseDate = null,
            title = title,
            video = false,
            voteAverage = rating,
            voteCount = 0,
            budget = 0,
            genres = listOf(),
            homePage = "",
            imdbId = "",
            productionCompanies = listOf(),
            productionCountries = listOf(),
            revenue = 0,
            runtime = 0,
            status = "",
            tagline = "",
            comment = ""
        )

    companion object {
        const val SIMPLE_MOVIE = "Тестовый фильм"
        const val SIMPLE_RATING = 4.0
    }
}