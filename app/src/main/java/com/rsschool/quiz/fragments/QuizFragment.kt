package com.rsschool.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.FragmentQuizBinding

//TODO: Реализовать сброс выбора в RadioGroup при нажатии Next, предварительно записав выбранный вариант в SharedPreference
//TODO: Реализовать восстановления выбора в RadioGroup из SharedPreference при нажатии Previous
//SOLVED: Создать массив тем и/или массив цветов статус бара, один раз при создании активити и передавать его во фрагемент при создании
//SOLVED: Так же создать массив вопросов и передавать во фрагмент

//TODO: Добавить вопросы в файлы ресурсов
//TODO: Переоформить фрагмент с результатом

class QuizFragment( private val viewPager: ViewPager2,
                          private val position:Int,
                          private val questions:Array<String>,
                          private var userAnswers: MutableList<String>
                          ) : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private var chosenId : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQuizBinding.inflate(inflater,container,false)

        with(requireNotNull(_binding)){

            toolbar.title = "Вопрос №${position + 1}"
            if(position == 0 ) toolbar.navigationIcon = null

            question.text = questions[0]

            val buttonsCount = radioGroup.childCount
            for(i in 0 until buttonsCount){
                val rButton = radioGroup[i]

                if(rButton is RadioButton) rButton.text = questions[i+1]

                if( userAnswers.size > position && userAnswers[position] == questions[i+1])
                    (rButton as RadioButton).isChecked = true
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (!nextButton.isEnabled) nextButton.isEnabled = true
                for(i in 0 until group.childCount)
                    if(group[i] is RadioButton && group[i].id == checkedId){
                        if(userAnswers.isNotEmpty() && position < userAnswers.size ){
                            userAnswers[position] = i.toString()
                        }else{
                            userAnswers += i.toString()
                        }
                        chosenId = i
                    }
            }

            toolbar.setNavigationOnClickListener {
                if(position > 0)
                    viewPager.currentItem = viewPager.currentItem -1
            }

            nextButton.setOnClickListener {
                if(userAnswers.size > position) userAnswers[position] = (chosenId + 1).toString()
                else userAnswers += (chosenId + 1).toString()
                viewPager.currentItem = viewPager.currentItem + 1
            }

            previousButton.isEnabled = position > 0

            previousButton.setOnClickListener {
                if(position > 0)viewPager.currentItem = viewPager.currentItem -1

            }

            if(position == 4) nextButton.text = "SUBMIT"
            else nextButton.text = "NEXT"
        }
            return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireNotNull(_binding)){
            val buttonsCount = radioGroup.childCount

            for(i in 0 until buttonsCount){
                val rButton = radioGroup[i]

                if( userAnswers.size > position && userAnswers[position] == questions[i+1])
                    (rButton as RadioButton).isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    companion object {
//
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            QuizFragment().apply {
//                arguments = Bundle().apply {
//                    //putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}