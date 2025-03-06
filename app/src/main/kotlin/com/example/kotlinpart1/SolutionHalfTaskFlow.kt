package com.example.kotlinpart1

fun main() {
    println("Hello, TaskFlow!")

    val taskFlow = SolutionHalfTaskFlow()

    while (true) {
        println(
            """
            Выберите действие: 
                1 - Добавить задачу, 
                2 - Показать задачи, 
                3 - Показать выполненные задачи, 
                4 - Переключить состояние задачи, 
                5 - Выход
            """.trimIndent()
        )
        taskFlow.run {
            when (readlnOrNull()?.toIntOrNull()) {
                1 -> addTask()
                2 -> showTasks()
                3 -> TODO("Еще не реализовано")
                4 -> TODO("Еще не реализовано")
                5 -> return
                else -> println("Неверный выбор, попробуйте еще раз.")
            }
        }
    }
}

class SolutionHalfTask(
    var taskId: Int,
    var title: String,
    var description: String,
    var priority: Int = 0,
    var isCompleted: Boolean = false
)

class SolutionHalfTaskFlow {
    private var taskId = 0
    private val tasks: MutableList<SolutionHalfTask> by lazy { mutableListOf<SolutionHalfTask>() }

    fun addTask() {
        println("Введите название задачи:")
        val title = readlnOrNull().orEmpty()

        println("Введите описание задачи:")
        val description = readlnOrNull().orEmpty()

        println("Введите приоритет задачи (0 - низкий, 1 - средний, 2 - высокий):")
        val priority = readlnOrNull()?.toIntOrNull() ?: 0

        tasks.add(SolutionHalfTask(taskId++, title, description, priority))
        println("Задача добавлена.")
    }

    fun showTasks() {
        if (tasks.isEmpty()) {
            println("Список задач пуст.")
        } else {
            tasks.sortedByDescending { it.priority }
                .forEach { task ->
                    println("Task ${task.title} ${task.description}")
                }
        }
    }
}