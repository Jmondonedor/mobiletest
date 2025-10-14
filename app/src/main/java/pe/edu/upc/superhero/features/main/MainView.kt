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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.superhero.features.favorites.presentation.FavoritesView
import pe.edu.upc.superhero.features.heroes.presentation.SearchHeroView

@Composable
fun MainView(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val selectedIndex = remember {
        mutableIntStateOf(0)
    }
    val navigationItems = listOf(
        NavigationItem(
            "Search",
            Icons.Default.Search,
            "search_hero"
        ),
        NavigationItem(
            "Favorites",
            Icons.Default.Favorite,
            "favorites"
        ),
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedIndex.intValue == index,
                        onClick = {
                            selectedIndex.intValue = index
                            navController.navigate(item.route)
                        },
                        icon = {
                            Icon(item.icon,
                                contentDescription = item.label)
                        }
                    )
                }
            }
        }

    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = "search_hero",
            modifier = modifier.padding(paddingValues)
        ) {
            composable("search_hero") {
                SearchHeroView()
            }

            composable("favorites") {
                FavoritesView()
            }

        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)