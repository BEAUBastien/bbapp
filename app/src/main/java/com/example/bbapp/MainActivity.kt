package com.example.bbapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bbapp.ui.theme.BbappTheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val viewmodel: MainViewModel by viewModels()
            val destinations = listOf(Destination.Profil, Destination.Edition)


            BbappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = { BottomNavigation {
                            destinations.forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(screen.label) },
                                    selected =
                                    currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                                    onClick = { navController.navigate(screen.destination) })
                            }}
                        }) { innerPadding ->
                        NavHost(navController, startDestination = Destination.Profil.destination,
                            Modifier.padding(innerPadding)) {
                            composable(Destination.Profil.destination) { Screen1 { navController.navigate("screen2") } }
                            composable(Destination.Edition.destination) { Screen2(viewmodel, { navController.navigate("screen1") },) }
                        }
                    }
                }
                }
            }
    }
}
sealed class Destination(val destination: String, val label: String, val icon: ImageVector) {
    object Profil : Destination("profil", "Mon Profil", Icons.Filled.Person)
    object Edition : Destination("edition", "Movie", Icons.Filled.Edit)
}



@Composable
fun Screen1(onClick: () -> Unit) {
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
fun Screen2(viewmodel: MainViewModel, onPreviousScreen: () -> Unit) {
    LaunchedEffect(key1 = true, block = {viewmodel.getMovies()})
    val etat by viewmodel.movies.collectAsStateWithLifecycle()
            LazyVerticatorid(columns GridCells.Fixed( count 2))
            horizontalArrangement Arrangement. spacedBy(16.dp), verticalArrangement =
            Arrangement.spacedBy (28.dp)) { this LazyGridScope
                    items(count 5) the Lycope
                Image(
                pointerResource(R.drawable.fleur), content Description = "Des Fleurs")

                    item { this Lazy GritemScope Text(text = "coucou")}

                    val nots = list(size 10) {index -> "not" index}
                items (mots) {this Lazydescope Text(text = it)}
        //image
        //Titre
        //date_sortie

    }
}

