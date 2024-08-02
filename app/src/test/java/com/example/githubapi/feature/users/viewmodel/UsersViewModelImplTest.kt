package com.example.githubapi.feature.users.viewmodel

import app.cash.turbine.test
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Error
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Loading
import com.example.githubapi.feature.users.viewmodel.UsersUiState.Success
import com.example.githubapi.navigation.Screen.DetailScreen
import com.example.githubapi.rules.MainDispatcherRule
import com.example.lib_domain.ResultType
import com.example.lib_domain.usecases.GetUserListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersViewModelImplTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()
    private val mockGetUserListUseCase = mockk<GetUserListUseCase>()
    private lateinit var subject: UsersViewModel

    @Before
    fun setUp() {
        subject = UsersViewModel(mockGetUserListUseCase)
    }

    @Test
    fun `GIVEN successful API call WHEN uiState is observed THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns flow {
                emit(ResultType.Success(emptyList()))
            }

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Success(data = emptyList()), awaitItem())
            }
        }

    @Test
    fun `GIVEN un-successful API call WHEN uiState is observed THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns flow {
                emit(ResultType.Error(Exception()))
            }

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error, awaitItem())
            }
        }

    @Test
    fun `WHEN reduce is called with OnItemClicked THEN NavigateToDetail intent is sent`() =
        runTest {
            val userId = "1"
            coEvery { mockGetUserListUseCase.getUserList() } returns flow {
                emit(ResultType.Success(emptyList()))
            }

            subject.userIntent.test {
                subject.reduce(UserEvent.OnUserClicked(userId = userId))
                assertEquals(
                    UserIntent.NavigateToDetail(route = DetailScreen.createRoute(userId)),
                    awaitItem()
                )
            }
        }

    @Test
    fun `GIVEN exception during API call WHEN uiState is observed THEN correct uiState is emitted`() =
        runTest {
            coEvery { mockGetUserListUseCase.getUserList() } returns flow {
                throw Exception()
            }

            subject.uiState.test {
                assertEquals(Loading, awaitItem())
                assertEquals(Error, awaitItem())
            }
        }
}