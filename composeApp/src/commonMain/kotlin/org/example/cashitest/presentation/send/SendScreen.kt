package org.example.cashitest.presentation.send

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.cashitest.domain.models.Currency
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import org.example.cashitest.ui_components.views.AmountInput
import org.example.cashitest.ui_components.views.CurrencyDropdown
import org.example.cashitest.ui_components.views.EmailInput
import org.example.cashitest.ui_components.views.LoadingButton
import org.koin.compose.viewmodel.koinViewModel

object SendScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<SendViewModel>()

        val uiState = viewModel.uiState.collectAsState()

        val viewState = uiState.value.viewState

        val navigator = LocalNavigator.current

        Body(
            viewState = viewState,
            inputEmail = viewModel::inputEmail,
            inputAmount = viewModel::inputAmount,
            selectCurrency = viewModel::selectCurrency,
            onButtonClicked = viewModel::onSendButtonClicked,
            onDialogButtonClicked = { navigator?.pop() },
            onBackClicked = { navigator?.pop() },
            modifier = Modifier
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun Body(
        viewState: SendViewState,
        inputEmail: (String) -> Unit,
        inputAmount: (String) -> Unit,
        selectCurrency: (Currency) -> Unit,
        onButtonClicked: () -> Unit,
        onDialogButtonClicked: () -> Unit,
        onBackClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        var email by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }

        if (viewState == SendViewState.Successful) {
            SuccessDialog(
                onDialogButtonClicked = onDialogButtonClicked
            )
        }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Send money", fontSize = 24.sp, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            },
            bottomBar = {
                LoadingButton(
                    onClick = onButtonClicked,
                    enabled = viewState.buttonEnabled(),
                    loading = viewState == SendViewState.Loading,
                    text = "\uD83D\uDCB8 Send",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 16.dp, 16.dp, 64.dp)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                EmailInput(
                    email = email,
                    onEmailChange = {
                        email = it
                        inputEmail(it)
                    },
                )

                AmountInput(
                    amount = amount,
                    onAmountChange = {
                        amount = it
                        inputAmount(it)
                    }
                )

                CurrencyDropdown(
                    selectCurrency = selectCurrency
                )
            }
        }
    }

    @Composable
    fun SuccessDialog(
        onDialogButtonClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        AlertDialog(
            onDismissRequest = onDialogButtonClicked,
            title = { Text("Success") },
            text = { Text("Your payment successfully sent") },
            confirmButton = {
                Button(onClick = onDialogButtonClicked) {
                    Text("Got it")
                }
            },
            modifier = modifier
        )
    }
}