package com.ticketmaster.testapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.ticketmaster.testapp.data.mappers.toEventDetail
import com.ticketmaster.testapp.domain.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val dataRepository: DataRepository,
): ViewModel()  {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    @OptIn(FlowPreview::class)
    fun getEventLists() = dataRepository.getEventListByCity(searchText.value)
        .map { pagingData ->
            pagingData.map {it.toEventDetail() }
        }.debounce(DEBOUNCE_TIME)
        .cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    companion object {
        const val DEBOUNCE_TIME = 500L
    }

}