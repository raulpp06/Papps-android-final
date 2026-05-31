package es.upsa.papps.trabajofinal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upsa.papps.trabajofinal.ui.screens.SubjectsScreen
import es.upsa.papps.trabajofinal.ui.screens.TaskScreen
import es.upsa.papps.trabajofinal.ui.screens.add.AddSubjectScreen
import es.upsa.papps.trabajofinal.ui.screens.add.AddTaskScreen
import es.upsa.papps.trabajofinal.ui.screens.detail.SubjectDetailScreen
import es.upsa.papps.trabajofinal.ui.screens.detail.TaskDetailScreen
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    vm: MainViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SUBJECTS,
        modifier = modifier,
    ) {

        composable(Routes.SUBJECTS) { SubjectsScreen(navController, vm) }
        composable(Routes.SUBJECT_ADD) { AddSubjectScreen(navController, vm) }
        composable(Routes.TASKS) { TaskScreen(navController, vm) }
        composable(Routes.TASK_ADD) { AddTaskScreen(navController, vm) }

        composable("${Routes.SUBJECT_DETAIL}/{id}") { backstack ->

            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                SubjectDetailScreen(id, vm)
            }

        }

        composable("${Routes.TASK_DETAIL}/{id}") { backstack ->

            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                TaskDetailScreen(id, vm)
            }

        }

    }
}