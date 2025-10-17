package com.example.pos_warung.presentation.ui.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun PosNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // --- Layar Autentikasi ---
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.ProductList.route){
                        popUpTo(Screen.Login.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        // --- Layar Produk ---
        composable(route = Screen.ProductList.route) {
            // TODO: Ganti dengan ProductListScreen(navController)
            PlaceholderScreen("Product List")
        }

        // Layar untuk Tambah/Edit Produk (menggunakan query parameter)
        composable(
            route = "${Screen.AddEditProduct.route}?${Screen.AddEditProduct.PRODUCT_ID_ARG}={${Screen.AddEditProduct.PRODUCT_ID_ARG}}",
            arguments = listOf(
                navArgument(Screen.AddEditProduct.PRODUCT_ID_ARG) {
                    type = NavType.LongType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong(Screen.AddEditProduct.PRODUCT_ID_ARG)
            // TODO: Ganti dengan AddEditProductScreen(navController, productId)
            PlaceholderScreen("Add/Edit Product (ID: $productId)")
        }

        // Layar Detail Produk (menggunakan path parameter)
        composable(
            route = "${Screen.ProductDetail.route}/{${Screen.ProductDetail.PRODUCT_ID_ARG}}",
            arguments = listOf(
                navArgument(Screen.ProductDetail.PRODUCT_ID_ARG) {
                    type = NavType.LongType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments!!.getLong(Screen.ProductDetail.PRODUCT_ID_ARG)
            // TODO: Ganti dengan ProductDetailScreen(navController, productId)
            PlaceholderScreen("Product Detail (ID: $productId)")
        }

        // --- Layar Keranjang & Checkout ---
        composable(route = Screen.Cart.route) {
            // TODO: Ganti dengan CartScreen(navController)
            PlaceholderScreen("Cart")
        }
        composable(route = Screen.Checkout.route) {
            // TODO: Ganti dengan CheckoutScreen(navController)
            PlaceholderScreen("Checkout")
        }

        // --- Layar Transaksi ---
        composable(route = Screen.TransactionList.route) {
            // TODO: Ganti dengan TransactionListScreen(navController)
            PlaceholderScreen("Transaction List")
        }
        composable(
            route = "${Screen.TransactionDetail.route}/{${Screen.TransactionDetail.TRANSACTION_ID_ARG}}",
            arguments = listOf(
                navArgument(Screen.TransactionDetail.TRANSACTION_ID_ARG) {
                    type = NavType.LongType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val transactionId =
                backStackEntry.arguments!!.getLong(Screen.TransactionDetail.TRANSACTION_ID_ARG)
            // TODO: Ganti dengan TransactionDetailScreen(navController, transactionId)
            PlaceholderScreen("Transaction Detail (ID: $transactionId)")
        }

        // --- Layar Laporan ---
        composable(route = Screen.DailyReport.route) {
            // TODO: Ganti dengan DailyReportScreen(navController)
            PlaceholderScreen("Daily Report")
        }
        composable(route = Screen.MonthlyReport.route) {
            // TODO: Ganti dengan MonthlyReportScreen(navController)
            PlaceholderScreen("Monthly Report")
        }

        // --- Layar Profil ---
        composable(route = Screen.Profile.route) {
            // TODO: Ganti dengan ProfileScreen(navController)
            PlaceholderScreen("Profile")
        }
    }
}

/**
 * Komponen placeholder sementara.
 * Ganti ini dengan layar asli yang sudah Anda buat.
 */
@Composable
private fun PlaceholderScreen(screenName: String) {
    androidx.compose.material3.Text(text = screenName)
}