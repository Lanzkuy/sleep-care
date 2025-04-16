package com.lans.sleep_care.presentation.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lans.sleep_care.presentation.screen.chatbot.ChatbotScreen
import com.lans.sleep_care.presentation.screen.forgot_password.ForgotPasswordScreen
import com.lans.sleep_care.presentation.screen.home.HomeScreen
import com.lans.sleep_care.presentation.screen.login.LoginScreen
import com.lans.sleep_care.presentation.screen.register.RegisterScreen
import com.lans.sleep_care.presentation.screen.therapist.TherapistScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    val activity = LocalContext.current as Activity
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    BackHandler {
        if (currentRoute?.route == Route.HomeScreen.route) {
            activity.finish()
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(500))
        }
    ) {
        composable(route = Route.LoginScreen.route) {
            LoginScreen(
                navigateToRegister = {
                    navController.navigate(route = Route.RegisterScreen.route) {
                        popUpTo(route = Route.LoginScreen.route)
                    }
                },
                navigateToForgotPassword = {
                    navController.navigate(route = Route.ForgotScreen.route) {
                        popUpTo(route = Route.LoginScreen.route)
                    }
                },
                navigateToHome = {
                    navController.navigate(route = Route.HomeScreen.route) {
                        popUpTo(route = Route.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Route.RegisterScreen.route) {
            RegisterScreen(
                navigateToLogin = {
                    navController.navigateUp()
                },
                navigateToHome = {
                    navController.navigate(route = Route.HomeScreen.route) {
                        popUpTo(route = Route.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Route.ForgotScreen.route) {
            ForgotPasswordScreen(
                navigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = Route.HomeScreen.route) {
            HomeScreen(
                navigateToLogin = {
                    navController.navigate(route = Route.LoginScreen.route) {
                        popUpTo(route = Route.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToTherapist = {
                    navController.navigate(route = Route.TherapistScreen.route) {
                        popUpTo(route = Route.HomeScreen.route)
                    }
                },
                navigateToMyTherapy = { },
                navigateToChatbot = {
                    navController.navigate(route = Route.ChatbotScreen.route) {
                        popUpTo(route = Route.HomeScreen.route)
                    }
                },
                navigateToHistory = { }
            )
        }
        composable(route = Route.TherapistScreen.route) {
            TherapistScreen(
                navigateToHome = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = Route.MyTherapyScreen.route) {
        }
        composable(route = Route.ChatbotScreen.route) {
            ChatbotScreen(
                navigateToHome = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = Route.HistoryScreen.route) {
        }
    }
}