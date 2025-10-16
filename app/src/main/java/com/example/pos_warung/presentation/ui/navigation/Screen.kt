package com.example.pos_warung.presentation.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class Screen(val route: String) {
    object Login : Screen("login")

    object ProductList : Screen("product_list")

    object AddEditProduct : Screen("add_edit_product") {
        const val PRODUCT_ID_ARG = "productId"
        fun withProductId(productId: Long?) : String{
            return if (productId != null){
                "$route?$PRODUCT_ID_ARG=$productId"
            }else{
                route
            }
        }
    }

    object ProductDetail : Screen("product_detail"){
        const val PRODUCT_ID_ARG = "productId"
        fun withProductId(productId: Long) : String{
            return "$route?$PRODUCT_ID_ARG=$productId"
        }
    }

    object Cart : Screen("cart")
    object Checkout : Screen("checkout")

    object TransactionList : Screen("transaction_list")
    object TransactionDetail : Screen("transaction_detail"){
        const val TRANSACTION_ID_ARG = "transactionId"
        fun withTransactionId(transactionId: Long) : String{
            return "$route?$TRANSACTION_ID_ARG=$transactionId"
        }
    }

    object DailyReport : Screen("daily_report")
    object MonthlyReport : Screen("monthly_report")

    object Profile : Screen("profile")
}

fun NavController.navigateTo(screen: Screen, navOptions: NavOptions? = null){
    this.navigate(screen.route, navOptions)
}