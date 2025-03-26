package com.ticketmaster.testapp.uitests

import androidx.activity.viewModels
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.paging.map
import com.ticketmaster.testapp.presentation.MainActivity
import com.ticketmaster.testapp.presentation.viewmodel.EventsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ComposableUITests {

    @get:Rule(order = 1)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var eventsViewModel: EventsViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        eventsViewModel = composeTestRule.activity.viewModels<EventsViewModel>().value
    }

    @Test
    fun testSearchBoxComposable() {
        composeTestRule.onNodeWithText("Search").isDisplayed()
    }

    @Test
    fun testEventListComposable() = runTest {
        eventsViewModel.getEventLists().map { pagingData ->
            pagingData.map {
                composeTestRule.onNodeWithText(it.name).isDisplayed()
                composeTestRule.onNodeWithText(it.date).isDisplayed()
                composeTestRule.onNodeWithText(it.city).isDisplayed()
            }
        }
    }
}