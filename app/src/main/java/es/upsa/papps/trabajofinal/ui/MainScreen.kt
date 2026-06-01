package es.upsa.papps.trabajofinal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.navigation.AppNavigation
import es.upsa.papps.trabajofinal.navigation.Routes
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Routes.SUBJECTS,
                    onClick = {
                        navController.navigate(Routes.SUBJECTS)
                    },
                    label = { Text(stringResource(R.string.ui_subjects)) },
                    icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.TASKS,
                    onClick = {
                        navController.navigate(Routes.TASKS)
                    },
                    label = { Text(stringResource(R.string.ui_type_tasks)) },
                    icon = { Icon(Icons.Default.Menu, null) },
                )

                NavigationBarItem(
                    selected = currentRoute == Routes.SETTINGS,
                    onClick = {
                        navController.navigate(Routes.SETTINGS)
                    },
                    label = { Text(stringResource(R.string.ui_settings)) },
                    icon = { Icon(Icons.Default.Settings, null) },
                )
            }
        },
    ) { padding ->
        AppNavigation(
            navController = navController,
            vm = vm,
            modifier = Modifier.padding(padding),
        )
    }
}
