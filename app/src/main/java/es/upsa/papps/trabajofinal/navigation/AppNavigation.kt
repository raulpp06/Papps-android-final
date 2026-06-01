package es.upsa.papps.trabajofinal.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.upsa.papps.trabajofinal.ui.screens.SettingsScreen
import es.upsa.papps.trabajofinal.ui.screens.subject.SubjectAddScreen
import es.upsa.papps.trabajofinal.ui.screens.subject.SubjectDetailScreen
import es.upsa.papps.trabajofinal.ui.screens.subject.SubjectEditScreen
import es.upsa.papps.trabajofinal.ui.screens.subject.SubjectsScreen
import es.upsa.papps.trabajofinal.ui.screens.task.TaskAddScreen
import es.upsa.papps.trabajofinal.ui.screens.task.TaskDetailScreen
import es.upsa.papps.trabajofinal.ui.screens.task.TaskEditScreen
import es.upsa.papps.trabajofinal.ui.screens.task.TasksScreen
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    vm: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val slideUpEnter = slideInVertically(
        initialOffsetY = { it },
        animationSpec = tween(300)
    )
    val slideDownExit = slideOutVertically(
        targetOffsetY = { it },
        animationSpec = tween(300)
    )

    NavHost(
        navController = navController,
        startDestination = Routes.SUBJECTS,
        modifier = modifier,
    ) {
        composable(Routes.SUBJECTS) { SubjectsScreen(navController, vm) }
        composable(Routes.TASKS) { TasksScreen(navController, vm) }

        composable(
            Routes.SUBJECT_ADD,
            enterTransition = { slideUpEnter },
            exitTransition = { slideDownExit },
            popEnterTransition = { null },
            popExitTransition = { slideDownExit },
        ) { SubjectAddScreen(navController, vm) }

        composable(
            Routes.TASK_ADD,
            enterTransition = { slideUpEnter },
            exitTransition = { slideDownExit },
            popEnterTransition = { null },
            popExitTransition = { slideDownExit },
        ) { TaskAddScreen(navController, vm) }

        composable(
            "${Routes.SUBJECT_DETAIL}/{id}",
            enterTransition = { slideUpEnter },
            exitTransition = { slideDownExit },
            popEnterTransition = { null },
            popExitTransition = { slideDownExit },
        ) { backstack ->
            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            if (id != null) SubjectDetailScreen(navController, id, vm)
        }

        composable(
            "${Routes.SUBJECT_EDIT}/{id}",
        ) { backstack ->
            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            if (id != null) SubjectEditScreen(navController, id, vm)
        }

        composable(
            "${Routes.TASK_DETAIL}/{type}/{id}",
            enterTransition = { slideUpEnter },
            exitTransition = { slideDownExit },
            popEnterTransition = { null },
            popExitTransition = { slideDownExit },
        ) { backstack ->
            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            val type = backstack.arguments?.getString("type") ?: ""
            if (id != null) TaskDetailScreen(navController, id, vm, type)
        }

        composable(
            "${Routes.TASK_EDIT}/{type}/{id}",
        ) { backstack ->
            val id = backstack.arguments?.getString("id")?.toIntOrNull()
            val type = backstack.arguments?.getString("type") ?: ""
            if (id != null) TaskEditScreen(navController, id, vm, type)
        }

        composable(
            Routes.SETTINGS,
        ) {
            SettingsScreen(vm)
        }
    }
}