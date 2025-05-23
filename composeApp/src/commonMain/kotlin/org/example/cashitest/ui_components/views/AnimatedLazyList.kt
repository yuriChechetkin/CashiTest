package org.example.cashitest.ui_components.views

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.cashitest.ui_components.models.TransactionView

private const val ANIMATION_DURATION = 300
private const val ANIMATION_DURATION_OFFSET = 50L

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BoxScope.AnimatedLazyList(
    items: List<TransactionView>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
        state = listState,
    ) {
        itemsIndexed(items, key = { index, item ->
            when (item) {
                is TransactionView.Header -> item.date
                is TransactionView.Item -> item.subtitle
            }
        }) { index, item ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(index * ANIMATION_DURATION_OFFSET)
                visible = true
            }
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(ANIMATION_DURATION)) +
                        slideInVertically(
                            animationSpec = tween(ANIMATION_DURATION),
                            initialOffsetY = { it / 2 }
                        ),
                exit = fadeOut()
            ) {
                when (item) {
                    is TransactionView.Header -> HeaderCard(item)
                    is TransactionView.Item -> TransactionCard(item)
                }
            }
        }
    }
}
