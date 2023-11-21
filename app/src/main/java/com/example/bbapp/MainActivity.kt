package com.example.bbapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.bbapp.ui.theme.BbappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val viewmodel: MainViewModel by viewModels()
            val destinations =
                listOf(Destination.Profil, Destination.Movie, Destination.Serie, Destination.Acteur)

            BbappTheme {
                Scaffold(
                    bottomBar = {
                        if (currentDestination?.hierarchy?.any {
                                it.route in listOf(
                                    Destination.Movie.destination,
                                    Destination.Serie.destination,
                                    Destination.Acteur.destination
                                )
                            } == true) {
                            BottomNavigation {
                                destinations.filter { it.destination != Destination.Profil.destination }
                                    .forEach { screen ->
                                        BottomNavigationItem(
                                            icon = {
                                                Icon(
                                                    screen.icon,
                                                    contentDescription = null
                                                )
                                            },
                                            label = { Text(screen.label) },
                                            selected =
                                            currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                                            onClick = { navController.navigate(screen.destination) }
                                        )
                                    }
                            }
                        } else {
                            // Updated code for the button to navigate to MovieList
                            Button(onClick = { navController.navigate(Destination.Movie.destination) }) {
                                Text("Go to MovieList")
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController, startDestination = Destination.Profil.destination,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Destination.Profil.destination) {
                            Profil {
                                navController.navigate(
                                    "MovieList"
                                )
                            }
                        }
                        composable(Destination.Movie.destination) {
                            MovieList(viewmodel) { id ->
                                Log.d("DDD", id.toString())
                                navController.navigate(
                                    "MovieDetail/"+id

                                )
                            }
                        }
                        composable(Destination.Serie.destination) {
                            Profil {
                                navController.navigate(
                                    "MovieList"
                                )
                            }
                        }
                        composable(Destination.Acteur.destination) {
                            Profil {
                                navController.navigate(
                                    "MovieList"
                                )
                            }
                        }
                        composable("MovieDetail/{id}") { backStackEntry ->
                            MovieDetail(viewmodel, backStackEntry.arguments?.getString("id") ?: "")
                        }
                    }
                }
            }
        }
    }

    sealed class Destination(val destination: String, val label: String, val icon: ImageVector) {
        object Profil : Destination("profil", "Profil", Icons.Filled.Person)
        object Movie : Destination("Films", "Films", Icons.Filled.Movie)
        object Serie : Destination("Séries", "Séries", Icons.Filled.Tv)
        object Acteur : Destination("Acteurs", "Acteurs", Icons.Filled.Group)
    }

    @Composable
    fun Profil(onClick: () -> Unit) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.babas),
                contentDescription = "Bastien BEAU a Primavera Sound 2023",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(350.dp)
            )
            Text(text = "Bastien BEAU", Modifier.padding(16.dp))
            Text(text = "Responsable SEO chez Yoopies")
            Text(text = "Étudiant en 3ᵉ années du BUT MMI")

        }
    }
    @Composable
    fun MovieList(viewmodel: MainViewModel, onClick: (id: String) -> Unit) {
        val movies by viewmodel.movies.collectAsState()

        LaunchedEffect(Unit) {
            // Launch movie retrieval when the component is launched
            viewmodel.getMovies()
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            items(movies.size) { index ->
                val movie = movies[index]
                // Use Coil (or another image library) to load the movie poster
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clickable {
                            onClick(movie.id)
                            Log.d("CCC", movie.id.toString())
                        }
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(shape = RoundedCornerShape(20.dp)) // Ajoutez cette ligne pour arrondir les bords
                    )

                    Spacer(modifier = Modifier.height(5.dp)) // Add spacing between image and text

                    Text(
                        text = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.release_date,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }

    @Composable
    fun MovieDetail(viewmodel: MainViewModel, id: String) {
        LaunchedEffect(key1 = true) {
            viewmodel.getDetail(id)
        }
        val movie by viewmodel.detail.collectAsState()
        Log.d("ZZZ", id.toString())
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)

        ) {

        Text(
            text = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(shape = RoundedCornerShape(20.dp)) // Ajoutez cette ligne pour arrondir les bords
            )


        Text(
            text = movie.overview,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            fontStyle = FontStyle.Italic
        )
    }
}}
