package com.rsschool.quiz.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.R
import com.rsschool.quiz.adapters.QuizPagerAdapter
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

// TODO: Переделать layout фрагемента с результатом

class ResultFragment(
    private val viewPager: ViewPager2,
    private var userAnswers: MutableList<String>,
    private val themeArray: Array<Int>,
    private val quizSetsArray:Array<Array<String>>,
    private val pagesCount: Int
) : Fragment() {

    private var quizResult: Int = 0
    private var binding: FragmentResultBinding? = null

    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val answers : Int = userAnswers.size

        if(userAnswers.size < pagesCount - 1){
            for(i in 0 until (pagesCount - answers)){
                userAnswers += "5"
            }
        }

        for(i in 0 until pagesCount - 1){

            if (userAnswers[i] == quizSetsArray[i][6]) {
                quizResult++
            }
        }

        binding = FragmentResultBinding.inflate(inflater,container,false)

        val sendtext = "1. ${quizSetsArray[0][0]} : ${quizSetsArray[0][userAnswers[0].toInt()]} \n " +
                "2. ${quizSetsArray[1][0]} ${quizSetsArray[1][userAnswers[1].toInt()]} \n " +
                "3. ${quizSetsArray[2][0]} ${quizSetsArray[2][userAnswers[2].toInt()]} \n " +
                "4. ${quizSetsArray[3][0]} ${quizSetsArray[3][userAnswers[3].toInt()]} \n " +
                "5. ${quizSetsArray[4][0]} ${quizSetsArray[4][userAnswers[4].toInt()]} \n "

        with(requireNotNull(binding)){

            shareButton.setOnClickListener {
                val share = Intent(Intent.ACTION_SEND)
                share.putExtra(Intent.EXTRA_SUBJECT, "Quiz result")   //Устанавливаем Тему сообщения
                share.putExtra(Intent.EXTRA_TEXT,
                    "Ваш результат: $quizResult из ${pagesCount-1} \n" + sendtext)     //Устанавливаем само сообщение
                share.type = "text/plain"                                  //тип отправляемого сообщения
                startActivity(share)
            }

            exitButton.setOnClickListener {
                exitProcess(0)
            }

            restartButton.setOnClickListener {
                userAnswers.clear()
                viewPager.adapter = QuizPagerAdapter(requireActivity(), viewPager, userAnswers, themeArray, quizSetsArray, pagesCount )

            }

            tvPoints.text = "$quizResult из ${pagesCount-1}"

        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ResultFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ResultFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}