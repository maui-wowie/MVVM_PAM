package com.example.mvvmlab

open class TaskRepository {
    open fun loadInitialTasks(): List<Task> = listOf(
        Task(1, "Task 0")
    )
}