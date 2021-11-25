package softing.ubah4ukdev.moviesinfosearcher


import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import softing.ubah4ukdev.moviesinfosearcher.ui.home.HomeFragment
import softing.ubah4ukdev.moviesinfosearcher.ui.home.adapter.MoviesAdapter

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
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setup() {
        scenario = launchFragmentInContainer<HomeFragment>()
        Espresso.onView(ViewMatchers.isRoot()).perform(delay())
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun homeFragment_AssertNotNull() {
        scenario.onFragment() {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun homeFragment_RecyclerViewIsVisible() {
        Espresso.onView(withId(R.id.moviesList))
            .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun homeFragment_CheckCategory() {
        val movieCategory = Espresso.onView(
            ViewMatchers.withText(MOVIE_CATEGORY),
        )
        movieCategory.check(ViewAssertions.matches(ViewMatchers.withText(MOVIE_CATEGORY)))
    }

    @Test
    fun homeFragment_RecyclerView_CheckMovie() {
        val movieTitle = Espresso.onView(
            withIndex(withId(R.id.title), ZERO_INDEX)
        )
        movieTitle.check(
            ViewAssertions.matches(ViewMatchers.withText(MOVIE_TITLE))
        )

        val poster = Espresso.onView(
            withIndex(withId(R.id.poster), ZERO_INDEX)
        )
        poster.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun homeFragment_VerticalRecyclerView_ScrollTo() {
        Espresso.onView(withId(R.id.moviesList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(MOVIE_CATEGORY_NOW))
                )
            )
    }

    @Test
    fun homeFragment_HorizontalRecyclerView_ScrollTo() {
        Espresso.onView(withIndex(withId(R.id.moviesRV), VERTICAL_RV_ITEM_INDEX))
            .perform(
                RecyclerViewActions.scrollTo<MoviesAdapter.ViewHolder>(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(MOVIE_TITLE))
                )
            )
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = ""
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(DELAY)
            }
        }
    }

    private fun withIndex(matcher: Matcher<View>, index: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            private var currentIndex = 0

            override fun describeTo(description: Description?) {
                description?.appendText("with index: ");
                description?.appendValue(index);
                matcher.describeTo(description);
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index;
            }
        }
    }

    companion object {
        const val MOVIE_TITLE = "Веном 2"
        const val MOVIE_CATEGORY = "Up Coming"
        const val MOVIE_CATEGORY_NOW = "Now playing"
        const val DELAY = 2000L
        const val VERTICAL_RV_ITEM_INDEX = 2
        const val ZERO_INDEX = 0
    }
}