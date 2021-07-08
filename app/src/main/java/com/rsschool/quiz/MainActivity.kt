package com.rsschool.quiz

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.adapters.QuizPagerAdapter
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mainBinding : ActivityMainBinding? = null
    private var viewPager : ViewPager2? = null
    private var pagesCount : Int = 1
    private var barColors = arrayOf<Int>()
    private var themes: TypedArray? = null
    private var themeIdList = arrayOf<Int>()
    private var quizSetsArray = arrayOf<Array<String>>()
    private var chosenAnswers = mutableListOf<String>()

    @SuppressLint("Recycle", "CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        viewPager = requireNotNull(mainBinding).fragmentPager
        viewPager?.isUserInputEnabled = false
        viewPager?.isSaveEnabled = false

        val attrs = intArrayOf(R.attr.colorPrimaryDark)
        themes = resources.obtainTypedArray(R.array.styles)
        pagesCount = resources.obtainTypedArray(R.array.quiz).length() + 1
        val questions = resources.obtainTypedArray(R.array.quiz)
        val themeCount = requireNotNull(themes).length()+1

        for(i in 0 until themeCount){
            themeIdList += requireNotNull(themes).getResourceId(i,0)
        }

        for(i in 0 until themeCount){
            barColors += requireNotNull(theme?.obtainStyledAttributes(themeIdList[i], attrs)).getColor(0,0)
        }

        for(i in 0 until questions.length()){
            val arrayId = questions.getResourceId(i,0)
            quizSetsArray += resources.getStringArray(arrayId)
        }

        questions.recycle()

        viewPager?.adapter = QuizPagerAdapter(this, requireNotNull(viewPager), chosenAnswers, themeIdList, quizSetsArray, pagesCount)

        viewPager?.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            @SuppressLint("ResourceAsColor")
            override fun onPageSelected(position: Int) {
                when(position){
                    in 0..pagesCount - 2 -> window.statusBarColor = barColors[position]
                    else -> window.statusBarColor = R.color.white
                }

            }
        })
    }

    override fun onBackPressed() {
        if (viewPager?.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager?.currentItem = requireNotNull(viewPager).currentItem - 1
        }
    }

//    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CommitPrefEdits")
    override fun onDestroy() {
        super.onDestroy()
        mainBinding = null
    }

//    private inner class QuizPagerAdapter( fa: FragmentActivity, private val themeArray: Array<Int>,
//                                          private val quizSetsArray:Array<Array<String>>
//                                          ) : FragmentStateAdapter(fa) {
//
//        override fun getItemCount(): Int = pagesCount
//
//        override fun createFragment(position: Int): Fragment {
//
//                if (themeArray.isNotEmpty()) {
//                    setTheme(themeArray[position])
//                }
//
//            return when (position){
//                pagesCount-1 -> ResultFragment(requireNotNull(viewPager), chosenAnswers)
//                else -> QuizFragment(requireNotNull(viewPager), position, quizSetsArray[position], chosenAnswers)
//            }
//        }
//    }

//    companion object {
//        @JvmStatic
//        fun nextQuestion():Int{
//            return  0
//        }
//    }

}