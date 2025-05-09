package org.oriooneee

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun <T> LazyColumnWithStickyItem(
    stickyItemIndex: Int,
    items: List<T>,
    stickToTop: Boolean = true,
    stickToBottom: Boolean = true,
    itemUI: @Composable (item: T, index: Int, isSticky: Boolean, key: String) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    overscrollEffect: OverscrollEffect? = rememberOverscrollEffect(),
) {
    Box(
        modifier = modifier
    ) {
        val visibleItems = state.layoutInfo.visibleItemsInfo

        val header = visibleItems.find { it.key == "sticky_item" }

        val screenHeight = state.layoutInfo.viewportSize.height

        val animatedOffsetForBottom = header?.size?.minus(screenHeight.minus(header.offset)) ?: 0
        val animatedOffsetForTop = header?.offset?.coerceAtMost(0) ?: 0

        var firstVisibleItemIndex by remember { mutableStateOf(0) }
        var lastVisiblyVisibleItemIndex by remember { mutableStateOf(0) }

        LaunchedEffect(state.firstVisibleItemIndex) {
            firstVisibleItemIndex = state.firstVisibleItemIndex

            val lastVisibleItem = state.layoutInfo.visibleItemsInfo.lastOrNull()
            val lastIndex = (lastVisibleItem?.index ?: 0) - 1

            lastVisiblyVisibleItemIndex = lastIndex.coerceIn(0, items.lastIndex)
        }


        LazyColumn(
            state = state,
            contentPadding = contentPadding,
            horizontalAlignment = horizontalAlignment,
            flingBehavior = flingBehavior,
            overscrollEffect = overscrollEffect,
            modifier = Modifier.fillMaxSize(),
        ) {
            items.forEachIndexed { index, t ->
                if (index != stickyItemIndex) {
                    item(key = "item_$index") {
                        itemUI(t, index, false, "item_$index")
                    }
                } else {
                    item(key = "sticky_item") {
                        Box(
                            modifier = Modifier.offset {
                                val offsetY = when {
                                    stickToTop && stickToBottom -> {
                                        val bottomOffset = animatedOffsetForBottom.takeIf { it > 0 }
                                        -(bottomOffset ?: animatedOffsetForTop)
                                    }

                                    stickToTop -> {
                                        -animatedOffsetForTop
                                    }

                                    stickToBottom -> {
                                        animatedOffsetForBottom
                                            .takeIf { it > 0 }
                                            ?.times(-1) ?: 0
                                    }

                                    else -> 0
                                }
                                IntOffset(x = 0, y = offsetY)
                            }

                        ) {
                            itemUI(t, index, true, "sticky_item")
                        }
                    }
                }
            }
        }

        val isScrollingAboveStickyItem = state.firstVisibleItemIndex < stickyItemIndex

        val isScrollingBelowStickyItem =
            state.firstVisibleItemIndex > stickyItemIndex ||
                    (state.firstVisibleItemIndex == stickyItemIndex &&
                            state.firstVisibleItemScrollOffset > 0)

        val shouldShowFooter = stickToBottom &&
                (animatedOffsetForBottom > (header?.offset ?: 0) || isScrollingAboveStickyItem) &&
                animatedOffsetForBottom >= 0

        val shouldShowHeader = stickToTop &&
                (animatedOffsetForTop > (header?.size ?: 0) ||
                        isScrollingBelowStickyItem ||
                        (header?.offset ?: 0) < 0)

        val shouldShowStickyItem = shouldShowHeader || shouldShowFooter

        val alignment = if (shouldShowHeader) Alignment.TopCenter else Alignment.BottomCenter

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = if (shouldShowStickyItem) 0.dp else Int.MAX_VALUE.dp),
            contentAlignment = alignment
        ) {
            itemUI(items[stickyItemIndex], stickyItemIndex, true, "sticky_item")
        }

    }
}