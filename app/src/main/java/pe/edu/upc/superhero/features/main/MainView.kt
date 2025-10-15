package pe.edu.upc.superhero.features.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.superhero.features.home.HomeView
import pe.edu.upc.superhero.features.news.presentation.FavoritesNewsView
import pe.edu.upc.superhero.features.news.presentation.NewsDetailView
import pe.edu.upc.superhero.features.news.presentation.SearchNewsView
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainView(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(-1) }

    val navigationItems = listOf(
        NavigationItem("Search", Icons.Default.Search, "search_news"),
        NavigationItem("Favorites", Icons.Default.Favorite, "favorites")
    )

    Scaffold(
        bottomBar = {
            // Solo mostrar bottom nav cuando NO estamos en Home ni en Detail
            if (selectedIndex != -1) {
                NavigationBar {
                    navigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(item.route) {
                                    popUpTo("home") { inclusive = false }
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(item.icon, contentDescription = item.label)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = modifier.padding(paddingValues)
        ) {
            // Vista HOME inicial
            composable("home") {
                selectedIndex = -1
                HomeView(
                    onFindNewsClick = {
                        selectedIndex = 0
                        navController.navigate("search_news")
                    },
                    onFavoriteNewsClick = {
                        selectedIndex = 1
                        navController.navigate("favorites")
                    }
                )
            }

            // Vista "Find news"
            composable("search_news") {
                selectedIndex = 0
                SearchNewsView(
                    onNewsClick = { newsUrl ->
                        val encodedUrl = URLEncoder.encode(
                            newsUrl,
                            StandardCharsets.UTF_8.toString()
                        )
                        navController.navigate("news_detail/$encodedUrl")
                    }
                )
            }

            // Vista "Favorite news"
            composable("favorites") {
                selectedIndex = 1
                FavoritesNewsView()
            }

            // Vista "Details"
            composable(
                route = "news_detail/{newsUrl}",
                arguments = listOf(
                    navArgument("newsUrl") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val encodedUrl = backStackEntry.arguments?.getString("newsUrl")
                val newsUrl = encodedUrl?.let {
                    URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
                }
                NewsDetailView(newsUrl = newsUrl)
            }
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)