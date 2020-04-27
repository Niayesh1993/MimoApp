package com.example.mimosampleapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.room.Room
import com.example.mimosampleapp.R
import com.example.mimosampleapp.database.AppDb
import com.example.mimosampleapp.database.ContentEntity
import com.example.mimosampleapp.database.InputEntity
import com.example.mimosampleapp.database.LessonEntity
import com.example.mimosampleapp.model.Lesson
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.model.input.Course
import com.example.mimosampleapp.service.user.UserService
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import com.example.mimosampleapp.utility.api.ApiCallbackListener
import java.io.Serializable

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var service: UserService? = null
    var Lesson_List: List<Lesson>? = null
    var start_btn: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = UserService(this)
        SettingsManager.init(this)
        start_btn = findViewById(R.id.start_btn)
        start_btn!!.setOnClickListener(this)
        CheckActiveCourse()

    }

    fun CheckActiveCourse()
    {
        if (SettingsManager.getBoolean(Constants().PREF_ACTIVE_COURSE))
        {
            start_btn!!.visibility = View.VISIBLE
           // Fetchfromdatabase()
        }
        else if(!SettingsManager.getBoolean(Constants().PREF_ACTIVE_COURSE))
        {
            FetchCourse()
        }

    }

    fun FetchCourse()
    {
        try {
            service!!.GetLessons(object : ApiCallbackListener
            {
                override fun onSucceed(data: ApiResultModel) {
                    if (data.data != null && data.data!!.size>0)
                    {
                        SettingsManager.setValue(Constants().PREF_LESSON_COUNTER, data.data!!.size)
                        Lesson_List = data.data
                        Lesson_List?.let { Addtodatabase(it) }

                    }
                }

                override fun onError(errors: String) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
                }

            })
        } catch (e: Exception) {
            e.message
        }
    }

    fun Addtodatabase(list: List<Lesson>)
    {
            val thread = Thread {
                var Lesoon_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "LessonDB").build()

                var Input_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "InputDB").build()

                var Content_db =
                    Room.databaseBuilder(applicationContext, AppDb::class.java, "ContentDB").build()

                for (item: Lesson in list) {

                    var lessonEntity = LessonEntity()

                    lessonEntity.lessonId = item.lessonId!!
                    lessonEntity.LessonStatus = false

                    Lesoon_db.lessonDAO().saveLessons(lessonEntity)

                    if (item.input != null)
                    {
                        var inputEntity = InputEntity()
                        inputEntity.ID = (0..100).random()
                        inputEntity.lnputId = item.lessonId!!
                        inputEntity.startIndex = item.input!!.startIndex!!
                        inputEntity.endIndex = item.input!!.endIndex!!

                        Input_db.InputDAO().saveInputs(inputEntity)

                    }

                    for (Citem: LessonContent in item.contents!!)
                    {
                        try {
                            var contentEntity = ContentEntity()
                            contentEntity.ID = (0..100).random()
                            contentEntity.ContentId = item.lessonId!!
                            contentEntity.color = Citem.color!!
                            contentEntity.text = Citem.text!!

                            Content_db.ContentDAO().saveContent(contentEntity)
                        } catch (e: Exception) {
                            e.message
                        }
                    }
                }


            }

            thread.start()

        SettingsManager.setValue(Constants().PREF_ACTIVE_COURSE,true)
        start_btn!!.visibility = View.VISIBLE

    }
    override fun onClick(v: View?) {

        if (v != null) {
            when (v.getId()) {

                R.id.start_btn -> {

                    ShowCourse()
                }
            }
        }
    }

    fun ShowCourse()
    {
        try {
            val intent = Intent(this, CourseActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.message
        }

    }


}