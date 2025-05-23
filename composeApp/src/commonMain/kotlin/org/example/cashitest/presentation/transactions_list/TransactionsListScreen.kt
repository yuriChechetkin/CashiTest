package org.example.cashitest.presentation.transactions_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.example.cashitest.presentation.send.SendScreen
import org.example.cashitest.ui_components.views.ErrorStateCard
import org.example.cashitest.ui_components.views.LoadingScreen
import org.example.cashitest.ui_components.views.MessageCard
import org.example.cashitest.ui_components.models.TransactionView
import org.example.cashitest.ui_components.views.AnimatedLazyList

object TransactionsListScreen : Screen {
    @OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val viewModel = koinViewModel<TransactionsListViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadTransactions()
        }

        val uiState = viewModel.uiState.collectAsState()

        val viewState = uiState.value.viewState

        val navigator = LocalNavigator.current

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            viewState.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxSize(),
        ) { padding ->
            Body(
                viewState = viewState,
                onRetryClicked = viewModel::loadTransactions,
                onButtonClicked = { navigator?.push(SendScreen) },
                onTransactionClicked = { /*TODO Implement transaciton details screen*/ },
                modifier = Modifier.padding(padding)
            )
        }
    }

    @Composable
    internal fun Body(
        viewState: TransactionsListViewState,
        onRetryClicked: () -> Unit,
        onButtonClicked: () -> Unit,
        onTransactionClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        when (viewState) {
            is TransactionsListViewState.Content -> TransactionsList(
                items = viewState.transactions,
                onSendMoneyClick = onButtonClicked,
                modifier = modifier,
            )

            is TransactionsListViewState.EmptyContent -> MessageCard("Transactions list is empty")
            is TransactionsListViewState.Error -> ErrorStateCard(
                message = viewState.errorDescription,
                onRetry = onRetryClicked,
            )

            is TransactionsListViewState.Loading -> LoadingScreen()
        }
    }

    @Composable
    internal fun TransactionsList(
        items: List<TransactionView>,
        onSendMoneyClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        val listState = rememberLazyListState()

        val isScrolling by remember {
            derivedStateOf { listState.isScrollInProgress }
        }

        Box(modifier.fillMaxSize()) {
            AnimatedLazyList(
                items = items,
                listState = listState,
                modifier = Modifier.fillMaxSize().align(Alignment.TopStart),
            )

            AnimatedVisibility(
                visible = !isScrolling,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(32.dp)
            ) {
                Button(onClick = {
                    onSendMoneyClick()
                }) {
                    Text("🌎 Go to Send Screen")
                }
            }
        }
    }
}