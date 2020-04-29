package com.example.mimosampleapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.room.Room
import com.example.mimosampleapp.R
import com.example.mimosampleapp.database.AppDb
import com.example.mimosampleapp.fragment.CourseFragment
import com.example.mimosampleapp.model.Lesson
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.LessonInput
import com.example.mimosampleapp.model.input.Course
import com.example.mimosampleapp.service.user.UserService

class CourseActivity : AppCompatActivity() {


    private var courseFragment: CourseFragment? = null
    private var fragmentManager: FragmentManager? = null
    private var service: UserService? = null
    var Lesson_List: MutableList<Lesson> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        service = UserService(this)

        FetchLessonfromDatabase()
        ShowCourseFragment(Lesson_List)

    }

    fun FetchLessonfromDatabase()
    {
        val thread = Thread {
            try {
                var Lesoon_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "LessonDB").build()
                var Content_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "ContentDB").build()
                var Input_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "InputDB").build()
                Lesoon_db.lessonDAO().getAllLessons().forEach()
                {

                    var lesson = Lesson()
                    var LessonContent: MutableList<LessonContent> = mutableListOf()
                    var LessonInput: LessonInput = LessonInput()

                    lesson.lessonId = it.lessonId
                    lesson.pass_status = it.LessonStatus


                    Content_db.ContentDAO().selectContent(it.lessonId).forEach()
                    {
                        val Lc = LessonContent()
                        Lc.color = it.color
                        Lc.text = it.text
                        LessonContent.add(Lc)
                    }
                    lesson.contents = LessonContent

                    if (Input_db.InputDAO().selectInput(it.lessonId)!= null)
                    {
                        var Li = LessonInput()
                        Li.startIndex =  Input_db.InputDAO().selectInput(it.lessonId)!!.startIndex
                        Li.endIndex =  Input_db.InputDAO().selectInput(it.lessonId)!!.endIndex

                        lesson.input = Li
                    }

                    Lesson_List.add(lesson)


                }
            } catch (e: Exception) {
                e.message
            }
        }
        thread.start()
        thread.join()

    }


    fun ShowCourseFragment(list: List<Lesson>)
    {
        try {
            val course = Course()
            course.Course = list
            courseFragment = CourseFragment().newInstance(course)
            fragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.cuorsefragment_container, courseFragment!!)
            fragmentTransaction.commitAllowingStateLoss()
        } catch (e: Exception) {
        }
    }

    override fun onBackPressed() {

        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.message
        }
    }

}
