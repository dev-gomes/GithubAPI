package com.example.githubapi.feature.users.viewmodel

import app.cash.turbine.test
import com.example.githubapi.feature.users.viewmodel.UserIntent.NavigateToDetail
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Error
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Loading
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Success
import com.example.githubapi.navigation.Screen.DetailScreen
import com.example.githubapi.rules.MainDispatcherRule
import com.example.lib_domain.ResultType
import com.example.lib_domain.usecases.GetUserListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class UsersViewModelImplTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val mockGetUserListUseCase = mockk<GetUserListUseCase>()
    private lateinit var subject: UsersViewModel

    @Test
    fun `GIVEN successful API call WHEN init is called THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns ResultType.Success(emptyList())

            initialiseSubject()
            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Success(data = emptyList()), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call WHEN init is called THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns ResultType.Error(Exception())

            initialiseSubject()
            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error, awaitItem())
            }
        }

    @Test
    fun `GIVEN OnItemClicked WHEN reduce is called THEN NavigateToDetail intent is sent`() =
        runTest {
            val userId = "1"
            coEvery { mockGetUserListUseCase.getUserList() } returns ResultType.Success(emptyList())

            initialiseSubject()

            subject.userIntent.test {
                subject.reduce(UserEvent.OnUserClicked(userId = userId))
                assertEquals(
                    NavigateToDetail(route = DetailScreen.createRoute(userId)),
                    awaitItem()
                )
            }
        }

    private fun initialiseSubject() {
        subject = UsersViewModel(mockGetUserListUseCase)
    }
}