package com.example.githubapi.feature.details.viewmodel

import app.cash.turbine.test
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Error
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Loading
import com.example.githubapi.feature.details.viewmodel.DetailUiState.Success
import com.example.githubapi.rules.MainDispatcherRule
import com.example.lib_domain.ResultType
import com.example.lib_domain.model.Details
import com.example.lib_domain.usecases.GetDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class DetailsViewModelImplTest {

    companion object {
        private const val USER_ID = "1"
    }

    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val mockGetDetailsUseCase = mockk<GetDetailsUseCase>()
    private val subject = DetailsViewModel(mockGetDetailsUseCase)

    @Test
    fun `GIVEN successful API call WHEN reduce is called with OnDetailsIdReceived THEN correct uiState is set`() =
        runTest {
            val mockDetails = mockk<Details>()
            coEvery { mockGetDetailsUseCase.getDetails(USER_ID) } returns ResultType.Success(
                listOf(mockDetails)
            )

            subject.reduce(DetailsEvent.OnDetailsIdReceived(userId = USER_ID))

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(
                    Success(listOf(mockDetails)),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN un-successful API call WHEN reduce is called with OnDetailsIdReceived THEN correct uiState is set`() =
        runTest {
            coEvery { mockGetDetailsUseCase.getDetails(USER_ID) } returns ResultType.Error(Exception())
            subject.reduce(DetailsEvent.OnDetailsIdReceived(userId = USER_ID))

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error, awaitItem())
            }
        }
}