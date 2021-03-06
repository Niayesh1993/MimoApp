package com.example.mimosampleapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.mimosampleapp.R
import com.example.mimosampleapp.database.AppDb
import com.example.mimosampleapp.database.ContentEntity
import com.example.mimosampleapp.database.InputEntity
import com.example.mimosampleapp.database.LessonEntity
import com.example.mimosampleapp.model.Lesson
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.service.user.UserService
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import com.example.mimosampleapp.utility.Utils
import com.example.mimosampleapp.utility.api.ApiCallbackListener
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var service: UserService? = null
    var Lesson_List: List<Lesson>? = null
    var start_btn: ImageButton? = null
    private var snackbar: Snackbar? = null
    private var utils: Utils? = null
    private var progressDialog: ProgressDialog? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        utils = Utils(this)


        snackbar = Snackbar.make(
            findViewById(R.id.drawer_layout),
            "No connection to the server, try again!",
            Snackbar.LENGTH_LONG
        )
        snackbar!!.setAction("", null)
        snackbar!!.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.orangeRed))

        val view = snackbar!!.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        val tv = view.findViewById(R.id.snackbar_text) as TextView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        } else {
            tv.gravity = Gravity.CENTER_HORIZONTAL
        }

        progressDialog = ProgressDialog(this, R.style.MyAlertDialogStyle)

        service = UserService(this)
        SettingsManager.init(this)
        start_btn = findViewById(R.id.start_btn)
        start_btn!!.setOnClickListener(this)


    }

    fun CheckActiveCourse()
    {
        if (SettingsManager.getBoolean(Constants().PREF_ACTIVE_COURSE))
        {
            ShowCourse()
        }
        else if(!SettingsManager.getBoolean(Constants().PREF_ACTIVE_COURSE))
        {
            if(utils!!.isNetworkAvailable())
            {
                if (snackbar!!.isShown())
                    snackbar!!.dismiss()

                FetchCourse()

            }
            else
            {
                snackbar!!.show()

            }
        }

    }

    fun FetchCourse()
    {
        try {
            progressDialog!!.show()
            service!!.GetLessons(object : ApiCallbackListener
            {
                override fun onSucceed(data: ApiResultModel) {
                    if (data.data != null && data.data!!.size>0)
                    {
                        SettingsManager.setValue(Constants().PREF_LESSON_COUNTER, data.data!!.size)
                        SettingsManager.setValue(Constants().PREF_LESSON_IN_PROGRESS,0)

                        Lesson_List = data.data
                        Lesson_List?.let { Addtodatabase(it) }

                    }
                }

                override fun onError(errors: String) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                    progressDialog!!.dismiss()
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
                        inputEntity.lnputId = item.lessonId!!
                        inputEntity.startIndex = item.input!!.startIndex!!
                        inputEntity.endIndex = item.input!!.endIndex!!

                        Input_db.InputDAO().saveInputs(inputEntity)

                    }

                    for (Citem: LessonContent in item.contents!!)
                    {
                        try {
                            var contentEntity = ContentEntity()
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
            thread.join()

        SettingsManager.setValue(Constants().PREF_ACTIVE_COURSE,true)
        progressDialog!!.dismiss()
        ShowCourse()

    }
    override fun onClick(v: View?) {

       if (v != null) {
            when (v.getId()) {

                R.id.start_btn -> {

                    CheckActiveCourse()
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