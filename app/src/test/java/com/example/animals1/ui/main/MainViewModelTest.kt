package com.example.animals1.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.animals1.data.Filter
import com.example.animals1.data.MediaItem
import com.example.animals1.ui.Event
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    //El mainThread no lo tenemos durante los test!!!
    // viewModelScope utiliza por defecto el mainThread como el dispatcher principal
    // liveData tambien utiliza por defecto el mainThread como el dispatcher principal

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var vm: MainViewModel
    private val fakeMediaProvider = FakeMediaProvider()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = MainViewModel(fakeMediaProvider, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // lo volvemos a porner el dispatcher a threadMain
        testDispatcher.cleanupTestCoroutines() //cancela todas las corrutinas que esten asociadas a ese dispatcher
    }

    @Test
    fun `progress is set visible when progressVisible value changes`() =
        testDispatcher.runBlockingTest {
            val observer = mock<Observer<Boolean>>()
            vm.progressVisible.observeForever(observer)

            vm.onFilterSelected(Filter.None)

            verify(observer).onChanged(true)
        }

    @Test
    fun `navigates to detail when onItemClicked`() = testDispatcher.runBlockingTest {
        val observer = mock<Observer<Event<Int>>>()
        vm.navigateToDetail.observeForever(observer)

        val mediaItem = MediaItem(1, "", "", MediaItem.Type.PHOTO)
        vm.onItemClicked(mediaItem)

        verify(observer).onChanged(Event(1))
    }

    @Test
    fun `updates items when onFilterClicked`() {
        val observer = mock<Observer<List<MediaItem>>>()
        vm.items.observeForever(observer)

        vm.onFilterSelected(Filter.None)

        verify(observer).onChanged(fakeMediaProvider.getItems())
    }

}