package com.example.mimosampleapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.example.mimosampleapp.R
import com.example.mimosampleapp.model.Lesson
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.service.user.UserService
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import com.example.mimosampleapp.utility.api.ApiCallbackListener

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

        if (SettingsManager.hasValue(Constants().PREF_LESSON_COUNTER))
        {
           // if (SettingsManager.getInt(Constants().PREF_LESSON_COUNTER)<1)
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
                        start_btn!!.visibility = View.VISIBLE

                        // Toast.makeText(applicationContext, SettingsManager.getInt(Constants().PREF_LESSON_COUNTER).toString(), Toast.LENGTH_SHORT).show();
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


    override fun onClick(v: View?) {

        if (v != null) {
            when (v.getId()) {

                R.id.start_btn -> {

                    Lesson_List?.let { ShowCourse(it) }
                }
            }
        }
    }

    fun ShowCourse(list: List<Lesson>)
    {

        try {
            var Lesson = list.get(2)
            val intent = Intent(this, CourseActivity::class.java)
            intent.putExtra("lesson" , Lesson)
            startActivity(intent)
        } catch (e: Exception) {

            e.message
        }


    }


}