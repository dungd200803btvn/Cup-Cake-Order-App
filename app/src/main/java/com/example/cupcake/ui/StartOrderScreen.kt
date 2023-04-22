
package com.example.cupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.cupcake.data.DataSource.quantityOptions

@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int, Int>>,
    // TODO: add onNextButtonClicked
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(R.drawable.cupcake),
            contentDescription = null,
            modifier = Modifier.width(300.dp)
        )
        // phan dem giua cac phan tu lien ke
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.order_cupcakes), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        quantityOptions.forEach { item ->
            SelectQuantityButton(
                labelResourceId = item.first,
                onClick = { /* TODO: handle next button */ }
            )
        }
    }
}
@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Preview
@Composable
fun StartOrderPreview(){
    StartOrderScreen(quantityOptions = quantityOptions)
}
