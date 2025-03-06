package com.example.kotlinpart1

fun main() {
    println("Hello, TaskFlow!")

    val taskFlow = SolutionTaskFlow()

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
                1 -> addTaskSafely()
                2 -> showTasks()
                3 -> showCompletedTasks()
                4 -> toggleTaskCompletion()
                5 -> return
                else -> println("Неверный выбор, попробуйте еще раз.")
            }
        }
    }
}

class SolutionTask(
    var taskId: Int,
    var title: String,
    var description: String,
    var priority: Int = 0,
    var isCompleted: Boolean = false
)

class SolutionTaskFlow {
    private var taskId = 0
    private val tasks: MutableList<SolutionTask> by lazy { mutableListOf<SolutionTask>() }

    fun addTaskSafely() {
        try {
            addTask()
        } catch (e: Exception) {
            println("Ошибка при добавлении задачи: ${e.message}")
        }
    }

    internal fun addTask() {
        println("Введите название задачи:")
        val title = readlnOrNull().orEmpty()
            .ifEmpty { throw IllegalArgumentException("Название не может быть пустым") }

        println("Введите описание задачи:")
        val description = readlnOrNull().orEmpty()

        println("Введите приоритет задачи (0 - низкий, 1 - средний, 2 - высокий):")
        val priority = readlnOrNull()?.toIntOrNull() ?: 0

        tasks.add(SolutionTask(taskId++, title, description, priority))
        println("Задача добавлена.")
    }

    fun showTasks() {
        if (tasks.isEmpty()) {
            println("Список задач пуст.")
        } else {
            tasks.sortedByDescending { it.priority }
                .forEach { task ->
                    println(task.format())
                }
        }
    }

    fun showCompletedTasks() {
        tasks.asSequence()
            .filter { it.isCompleted }
            .map { it.format() }
            .toList()
            .let {
                if (it.none()) {
                    println("Нет выполненных задач.")
                } else {
                    it.forEach { task ->
                        println(task.format())
                    }
                }
            }
    }

    fun toggleTaskCompletion() {
        println("Введите номер задачи, которую хотите отметить как выполненную/не выполненную:")
        val taskId = readlnOrNull()?.toIntOrNull()
        val taskIndex = taskId?.let { tasks.indexOfFirst { task -> task.taskId == it } }

        if (taskIndex != null) {
            tasks[taskIndex].isCompleted = !tasks[taskIndex].isCompleted
            println("Состояние задачи обновлено.")
        } else {
            println("Некорректный номер задачи.")
        }
    }

    private fun SolutionTask.format(): String =
        "${taskId}: [${if (isCompleted) "X" else " "}] $title (Приоритет: $priority) - $description"
}