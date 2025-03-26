package com.ticketmaster.testapp.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ticketmaster.testapp.domain.EventDetail


@Composable
fun EventListScreen(
    events: LazyPagingItems<EventDetail>
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        val context = LocalContext.current
        LaunchedEffect(key1 = events.loadState) {
            if (events.loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    context,
                    "Error: " + (events.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if (events.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(events.itemCount) { index ->
                        if (events[index] != null) {
                            EventItem(
                                eventDetails = events[index],
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    item {
                        if (events.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
