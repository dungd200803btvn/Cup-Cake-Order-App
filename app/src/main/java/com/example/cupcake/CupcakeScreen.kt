
package com.example.cupcake

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.DataSource.flavors
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
enum class CupcakeScreen() {
    Start,
    Flavor,
    Pickup,
    Summary
}
@Composable

fun CupcakeAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(modifier: Modifier = Modifier, viewModel: OrderViewModel = viewModel()){
    // TODO: Create NavController
      val navController = rememberNavController( )
    // TODO: Get current back stack entry

    // TODO: Get the name of the current screen

    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = false,
                navigateUp = { /* TODO: implement back navigation */ }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
       NavHost(navController = navController ,
       startDestination = CupcakeScreen.Start.name,
       modifier = modifier.padding(innerPadding)){
composable(route =CupcakeScreen.Start.name ){
    StartOrderScreen(quantityOptions = DataSource.quantityOptions,
    onNextButtonClicked = {viewModel.setQuantity(it)
    navController.navigate(CupcakeScreen.Flavor.name)})
}
           composable(route = CupcakeScreen.Flavor.name){
               val context = LocalContext.current
               SelectOptionScreen(subtotal = uiState.price,
                   onNextButtonClicked = {navController.navigate(CupcakeScreen.Pickup.name)},
                   onCancelButtonClicked ={ cancelOrderAndNavigateToStart(viewModel,navController) },
                   options = flavors.map{
                   id -> context.resources.getString(id)},
                   onSelectionChanged = {viewModel.setFlavor(it)}
                )
           }

           composable(route = CupcakeScreen.Pickup.name){
               val context = LocalContext.current
               SelectOptionScreen(subtotal = uiState.price, options = uiState.pickupOptions,
                   onNextButtonClicked = {navController.navigate(CupcakeScreen.Summary.name)},
                   onCancelButtonClicked ={ cancelOrderAndNavigateToStart(viewModel,navController) },
                   onSelectionChanged = {viewModel.setDate(it)}
               )
           }
           composable(route = CupcakeScreen.Summary.name){
               OrderSummaryScreen(orderUiState = uiState,
                   onCancelButtonClicked ={ cancelOrderAndNavigateToStart(viewModel,navController) },
                   onSendButtonClicked = { subject: String, summary: String ->
                   })
           }


       }


        // TODO: add NavHost
    }
}
private fun cancelOrderAndNavigateToStart(viewmodel: OrderViewModel,navController: NavHostController) {
  viewmodel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name,false)
}

