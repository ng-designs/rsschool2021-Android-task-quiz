package com.rsschool.quiz.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rsschool.quiz.fragments.QuizFragment
import com.rsschool.quiz.fragments.ResultFragment
import androidx.viewpager2.widget.ViewPager2

class QuizPagerAdapter(private var fa: FragmentActivity,
                       private val viewPager: ViewPager2,
                       private val chosenAnswers: MutableList<String>,
                       private val themeIdList: Array<Int>,
                       private val quizSetsArray: Array<Array<String>>,
                       private val pagesCount: Int
                       ) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = pagesCount

    override fun createFragment(position: Int): Fragment {

        if (themeIdList.isNotEmpty()) {
            fa.setTheme(themeIdList[position])
        }

        return when (position){
            pagesCount - 1 -> {
                ResultFragment(viewPager, chosenAnswers, themeIdList, quizSetsArray, pagesCount)
            }
            else -> QuizFragment(viewPager, position, quizSetsArray[position], chosenAnswers)
        }
    }
}