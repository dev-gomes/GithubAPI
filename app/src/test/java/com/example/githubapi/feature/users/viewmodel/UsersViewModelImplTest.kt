package com.example.githubapi.feature.users.viewmodel

import app.cash.turbine.test
import com.example.githubapi.rules.MainDispatcherRule
import com.example.githubapi.feature.users.domain.GetUserListUseCase
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UiState.*
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserEvent
import com.example.githubapi.feature.users.viewmodel.UsersViewModel.UserIntent.NavigateToDetail
import com.example.githubapi.navigation.Screen.DetailScreen
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
    private lateinit var subject: UsersViewModelImpl

    private fun setUp() {
        subject = UsersViewModelImpl(mockGetUserListUseCase)
    }

    @Test
    fun `GIVEN successful API call WHEN init is called THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns emptyList()

            setUp()
            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Success(data = emptyList()), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call with error message WHEN init is called THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } throws Exception("Custom Error")

            setUp()
            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error(message = "Custom Error"), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call with null error message WHEN init is called THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } throws Exception()

            setUp()
            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error(message = "Unknown Error"), awaitItem())
            }
        }

    @Test
    fun `WHEN reduce is called with OnItemClicked THEN NavigateToDetail intent is sent`() =
        runTest {
            val userId = "1"
            coEvery { mockGetUserListUseCase.getUserList() } returns emptyList()

            setUp()

            subject.userIntent.test {
                subject.reduce(UserEvent.OnItemClicked(userId = userId))
                assertEquals(
                    NavigateToDetail(route = DetailScreen.createRoute(userId)),
                    awaitItem()
                )
            }
        }
}