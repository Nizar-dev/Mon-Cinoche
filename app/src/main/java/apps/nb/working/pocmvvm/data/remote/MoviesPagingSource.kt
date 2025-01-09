package apps.nb.working.pocmvvm.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import apps.nb.working.pocmvvm.model.Movie

const val STARTING_PAGE_INDEX = 1
class MoviesPagingSource(
    private val loadMovies: suspend(page: Int) -> List<Movie>
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val movies = loadMovies(position)
        return LoadResult.Page(
            data = movies,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (movies.isEmpty()) null else position + 1
        )
    }
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
