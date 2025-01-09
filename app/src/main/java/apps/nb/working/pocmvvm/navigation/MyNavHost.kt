package apps.nb.working.pocmvvm.navigation

import AppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import apps.nb.working.pocmvvm.model.MoviesTab
import apps.nb.working.pocmvvm.network.NetworkStatus
import apps.nb.working.pocmvvm.ui.components.MyBottomBar
import apps.nb.working.pocmvvm.ui.screens.ConnectivityViewModel
import apps.nb.working.pocmvvm.ui.screens.DetailScreen
import apps.nb.working.pocmvvm.ui.screens.FavoriteMoviesViewModel
import apps.nb.working.pocmvvm.ui.screens.FavoritesScreen
import apps.nb.working.pocmvvm.ui.screens.LostConnectionScreen
import apps.nb.working.pocmvvm.ui.screens.MainScreen
import apps.nb.working.pocmvvm.ui.screens.MainViewModel
import apps.nb.working.pocmvvm.ui.screens.PreferencesScreen
import apps.nb.working.pocmvvm.ui.screens.SearchScreen

@Composable
fun MyNavHost(appNavHostController: NavHostController = rememberNavController()) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val favoritesViewModel: FavoriteMoviesViewModel = hiltViewModel()
    val networkViewModel: ConnectivityViewModel = hiltViewModel()
    val currentDestination = appNavHostController.currentBackStackEntryAsState()
    val networkStatus by networkViewModel.networkStatus.collectAsStateWithLifecycle()
    LaunchedEffect(networkStatus) {
        if (networkStatus == NetworkStatus.Lost) {
            appNavHostController.navigate(LostConnectionDestination)
        } else {
            appNavHostController.popBackStack(LostConnectionDestination, inclusive = true)
        }
    }
    LaunchedEffect(currentDestination.value) {
        when (currentDestination.value?.destination?.route) {
            HomeDestination::class.java.name -> mainViewModel.onTabSelected(MoviesTab.HOME)
            SearchDestination::class.java.name -> mainViewModel.onTabSelected(MoviesTab.SEARCH)
            FavoritesDestination::class.java.name -> mainViewModel.onTabSelected(MoviesTab.FAVORITES)
            else -> {}
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        if (networkStatus == NetworkStatus.Connected) {
            AppBar(title = "Mon Cinoche", onSettingsClick = {
                appNavHostController.navigate(PreferencesDestination)
            })
        }
    }, bottomBar = {
            if (networkStatus == NetworkStatus.Connected) {
                MyBottomBar(
                    { appNavHostController.navigate(HomeDestination) },
                    { appNavHostController.navigate(SearchDestination) },
                    { appNavHostController.navigate(FavoritesDestination) },
                    mainViewModel
                )
            }
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = appNavHostController,
                startDestination = HomeDestination
            ) {
                composable<HomeDestination> {
                    MainScreen(onMovieClick = {
                        appNavHostController.navigate(
                            DetailDestination(
                                movieId = it
                            )
                        )
                    })
                }
                composable<SearchDestination> {
                    SearchScreen(
                        onMovieClick = {
                            appNavHostController.navigate(DetailDestination(movieId = it))
                        }
                    )
                }
                composable<DetailDestination> {
                    val movieId = it.arguments?.getInt("movieId")
                    DetailScreen(
                        navController = appNavHostController,
                        favoriteMoviesViewModel = favoritesViewModel,
                        movieId = movieId ?: 0
                    )
                }
                composable<FavoritesDestination> {
                    FavoritesScreen(onMovieClick = {
                        appNavHostController.navigate(DetailDestination(movieId = it))
                    })
                }
                composable<PreferencesDestination> {
                    PreferencesScreen()
                }
                composable<LostConnectionDestination> {
                    LostConnectionScreen()
                }
            }
        }
    }
}
