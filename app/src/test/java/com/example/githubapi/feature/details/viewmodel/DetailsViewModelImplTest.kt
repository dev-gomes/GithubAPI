package com.example.githubapi.feature.details.viewmodel

import app.cash.turbine.test
import com.example.githubapi.rules.MainDispatcherRule
import com.example.githubapi.feature.details.domain.GetDetailsUseCase
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailUiState.Error
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailUiState.Loading
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailUiState.Success
import com.example.githubapi.feature.details.viewmodel.DetailsViewModel.DetailsEvent
import com.example.githubapi.models.Details
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailsViewModelImplTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val mockGetDetailsUseCase = mockk<GetDetailsUseCase>()
    private val subject = DetailsViewModelImpl(mockGetDetailsUseCase)

    private val userId = "1"

    @Test
    fun `GIVEN successful API call WHEN reduce is called with OnDetailsIdReceived THEN correct uiState is set`() =
        runTest {
            val mockDetails = mockk<Details>()
            coEvery { mockGetDetailsUseCase.getDetails(userId) } returns listOf(mockDetails)

            subject.reduce(DetailsEvent.OnDetailsIdReceived(userId = userId))

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Success(listOf(mockDetails)), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call with null error message WHEN reduce is called with OnDetailsIdReceived THEN correct uiState is set`() =
        runTest {
            coEvery { mockGetDetailsUseCase.getDetails(userId) } throws Exception()

            subject.reduce(DetailsEvent.OnDetailsIdReceived(userId = userId))

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error("Unknown Error"), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call with non-null error message WHEN reduce is called with OnDetailsIdReceived THEN correct uiState is set`() =
        runTest {
            coEvery { mockGetDetailsUseCase.getDetails(userId) } throws Exception("Custom Error")

            subject.reduce(DetailsEvent.OnDetailsIdReceived(userId = userId))

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error("Custom Error"), awaitItem())
            }
        }
}