package com.example.mvvmlab

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskViewModelTest {

    private class FakeRepository : TaskRepository() {
        override fun loadInitialTasks(): List<Task> = listOf(
            Task(1, "First"),
            Task(2, "Second", done = true)
        )
    }

    @Test
    fun toggleDone_flipsTheTaskState() {
        val viewModel = TaskViewModel(FakeRepository())
        viewModel.toggleDone(1)
        assertTrue(viewModel.tasks.value.first { it.id == 1 }.done)
    }

    @Test
    fun toggleDone_twice_returnsToOriginalState() {
        val viewModel = TaskViewModel(FakeRepository())
        viewModel.toggleDone(2) // Aslinya true, diubah jadi false
        viewModel.toggleDone(2) // Diubah jadi true lagi
        assertTrue(viewModel.tasks.value.first { it.id == 2 }.done)
    }

    @Test
    fun addTask_appendsWithNextId() {
        val viewModel = TaskViewModel(FakeRepository())
        viewModel.addTask("Third")

        assertEquals(3, viewModel.tasks.value.size)
        assertEquals(3, viewModel.tasks.value.last().id)
        assertEquals("Third", viewModel.tasks.value.last().title)
    }

    @Test
    fun addTask_ignoresBlankTitle() {
        val viewModel = TaskViewModel(FakeRepository())
        viewModel.addTask("   ")
        assertEquals(2, viewModel.tasks.value.size) // Ukuran list tidak boleh bertambah
    }
}