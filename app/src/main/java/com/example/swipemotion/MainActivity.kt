package com.example.swipemotion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.swipemotion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: SwipeRightViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Инициализация ViewModel
        viewModel = ViewModelProvider(this)[SwipeRightViewModel::class.java]
        setupLiveData()
        setupMotionLayout()
        setupClickListener()
    }

    private fun setupLiveData() {
        viewModel.modelStream.observe(this) {
            bindCard(it)
        }
    }

    private fun setupMotionLayout() = with(binding) {
        motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (currentId) {
                    // При завершении перехода к состояниям offScreenPass или offScreenLike
                    R.id.offScreenPass,
                    R.id.offScreenLike -> {
                        // Сбрасываем прогресс анимации
                        motionLayout?.progress = 0f
                        // Устанавливаем новый переход (от like к rest) и вызываем метод swipe в ViewModel
                        motionLayout?.setTransition(R.id.rest, R.id.like)
                        viewModel.swipe()
                    }
                }
            }
        })
    }

    private fun setupClickListener() = with(binding) {
        likeButton.setOnClickListener {
            motionLayout.transitionToState(R.id.like)
        }

        passButton.setOnClickListener {
            motionLayout.transitionToState(R.id.pass)
        }
    }

    private fun bindCard(it: SwipeRightModel?) = with(binding) {
        // Устанавливаем цвет фона верхней карточки из SwipeRightModel
        topCard.setBackgroundColor(it?.top!!.backgroundColor)
        // Устанавливаем цвет фона нижней карточки из SwipeRightModel
        bottomCard.setBackgroundColor(it.bottom.backgroundColor)
    }
}