package com.example.swipemotion

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SwipeRightViewModel : ViewModel() {
    private val stream = MutableLiveData<SwipeRightModel>()

    val modelStream: LiveData<SwipeRightModel>
        get() = stream

    // Список карточек с разными цветами
    private val data = listOf(
        SwipeCardBackgroundModel(backgroundColor = Color.parseColor("#E91E63")),
        SwipeCardBackgroundModel(backgroundColor = Color.parseColor("#2196F3")),
        SwipeCardBackgroundModel(backgroundColor = Color.parseColor("#F44336")),
        SwipeCardBackgroundModel(backgroundColor = Color.parseColor("#9E9E9E"))
    )
    // Индекс текущей карточки
    private var currentIndex = 0
    // Возвращает верхнюю карточку в текущем состоянии
    private val topCard
        get() = data[currentIndex % data.size]

    // Возвращает нижнюю карточку в текущем состоянии
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    init {
        updateStream()
    }

    fun swipe() {
        currentIndex += 1 // Увеличиваем индекс
        updateStream() // Обновляем данные и отправляем их в активность
    }

    private fun updateStream() {
        stream.value = SwipeRightModel(
            top = topCard,
            bottom = bottomCard
        )
    }
}