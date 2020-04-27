package com.example.mimosampleapp.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.model.input.Course
import com.example.mimosampleapp.service.user.UserService
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import com.example.mimosampleapp.utility.api.ApiCallbackListener

class CourseActivity : AppCompatActivity(), View.OnClickListener {


    var answer: String =""
    var NoInput: Boolean = true
    private var courseFragment: CourseFragment? = null
    private var fragmentManager: FragmentManager? = null
    private var service: UserService? = null
    var Lesson_List: MutableList<Lesson> = mutableListOf()
    var start_btn: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        service = UserService(this)

        //val content_layout: LinearLayout = findViewById(R.id.content_layout)
       // val continue_btn: Button = findViewById(R.id.continue_btn)
       // continue_btn.setOnClickListener(this)

        FetchLessonfromDatabase()
        Lesson_List!!.size
//        for (item: Lesson in this!!.Lesson_List!!)
//        {
//            try {
//                if(!item.pass_status)
//                {
//
//                }
//            } catch (e: Exception) {
//                e.message
//            }
//
//        }

        Lesson_List!!.size



//        val completion = LessonCompletion()
//        completion.ID = leson!!.lessonId
//        completion.StartTime = System.currentTimeMillis()
//
//        if (leson.input != null)
//        {
//            NoInput = false
//            var temp = ""
//            for (item : LessonContent in leson.contents!!)
//            {
//                temp = temp+item.text
//            }
//
//            try {
//                    var s = leson.input!!.startIndex
//                    var end = leson.input!!.endIndex
//                    answer = temp.substring(s!!,end!!)
//                    var f = temp.substring(0,s)
//                    var l = temp.substring(end,temp.lastIndex+1)
//
//
//
//                        val textView = TextView(this)
//                        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                        textView.setText(f)
//                        textView.setTextColor(Color.parseColor("#000000"))
//                        textView.setPadding(2,2,2,2)
//                        content_layout.addView(textView)
//
//
//                        editText!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                        editText!!.setTextColor(Color.parseColor("#000000"))
//                        editText!!.setPadding(2,2,2,2)
//                        content_layout.addView(editText)
//
//                        val textView1 = TextView(this)
//                        textView1.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                        textView1.setText(l)
//                        textView1.setTextColor(Color.parseColor("#000000"))
//                        textView1.setPadding(2,2,2,2)
//                        content_layout.addView(textView1)
//
//
//            } catch (e: Exception) {
//            }
//
//        }
//        else if (leson.input == null)
//        {
//           for (item : LessonContent in leson.contents!!)
//           {
//               val textView = TextView(this)
//               textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//               textView.setText(item.text)
//               textView.setTextColor(Color.parseColor(item.color))
//               textView.setPadding(2,2,2,2)
//               content_layout.addView(textView)
//           }
//
//        }

    }

    fun FetchLessonfromDatabase()
    {
        val thread = Thread {
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
        }
        thread.start()


    }

//    fun FetchInput(id: Int)
//    {
//        val thread = Thread {
//            var Input_db =
//                Room.databaseBuilder(applicationContext, AppDb::class.java, "InputDB").build()
//            LessonInput = Input_db.InputDAO().selectInput(id)!!
//        }
//        thread.start()
//
//
//    }

//    fun FetchContent(id: Int)
//    {
//        val thread = Thread {
//            var Content_db =
//                Room.databaseBuilder(applicationContext, AppDb::class.java, "ContentDB").build()
//            Content_db.ContentDAO().selectContent(id).forEach()
//            {
//                LessonContent.add(it)
//            }
//        }
//        thread.start()
//
//
//    }

    fun Fetchfromdatabase()
    {
        val thread = Thread {
            var Lesoon_db =
                Room.databaseBuilder(applicationContext, AppDb::class.java, "LessonDB").build()
            var Input_db =
                Room.databaseBuilder(applicationContext, AppDb::class.java, "InputDB").build()
            var Content_db =
                Room.databaseBuilder(applicationContext, AppDb::class.java, "ContentDB").build()

            Lesoon_db.lessonDAO().getAllLessons().forEach()
            {
                Log.i("Fetch Records", "Id:  : ${it.LessonStatus}")
                var t = it.lessonId
                var r = it.LessonStatus
            }

            Input_db.InputDAO().getAllInputs().forEach()
            {
                var t = it.startIndex
                var r = it.endIndex
            }

            var C =  Content_db.ContentDAO().selectContent(5)
            Content_db.ContentDAO().getAllcontent().forEach()
            {
                var t = it.color
                var r = it.text

            }


        }
        thread.start()
    }

//    fun FetchCourse()
//    {
//        try {
//            service!!.GetLessons(object : ApiCallbackListener
//            {
//                override fun onSucceed(data: ApiResultModel) {
//                    if (data.data != null && data.data!!.size>0)
//                    {
//                        SettingsManager.setValue(Constants().PREF_LESSON_COUNTER, data.data!!.size)
//                        Lesson_List = data.data
//
//                        Lesson_List?.let { ShowCourseFragment(it) }
//                        //start_btn!!.visibility = View.VISIBLE
//
//                        // Toast.makeText(applicationContext, SettingsManager.getInt(Constants().PREF_LESSON_COUNTER).toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                override fun onError(errors: String) {
//
//                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
//                }
//
//            })
//        } catch (e: Exception) {
//            e.message
//        }
//    }

    fun ShowCourseFragment(list: List<Lesson>)
    {
        val course = Course()
        course.Course = list
        courseFragment = CourseFragment().newInstance(course)
        fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.cuorsefragment_container, courseFragment!!)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onClick(v: View?) {

//        if (v != null) {
//            when (v.getId()) {
//
//                R.id.continue_btn -> {
//                    if (!NoInput)
//                    {
//                        if (editText!!.text.equals(answer))
//                        {
//
//                        }
//                    }
//                    else
//                    {
//
//                    }
//
//
//
//
//
//                }
//            }
//        }

    }
}
