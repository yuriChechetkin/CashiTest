package org.example.cashitest.ui_components.views

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.cashitest.ui_components.models.TransactionView

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

        items(items.size, key = { items[it].key() }) { index ->
            val item = items[index]
            when (item) {
                is TransactionView.Header -> HeaderCard(
                    item = item,
                    modifier = Modifier.animateItem()
                )
                is TransactionView.Item -> TransactionCard(
                    item,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}

private fun TransactionView.key(): String {
    return when (this) {
        is TransactionView.Header -> this.toString()
        is TransactionView.Item -> this.toString()
    }
}
