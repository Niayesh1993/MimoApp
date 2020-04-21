package com.example.mimosampleapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mimosampleapp.R
import com.example.mimosampleapp.model.input.ApiResultModel
import com.example.mimosampleapp.service.user.UserService
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import com.example.mimosampleapp.utility.api.ApiCallbackListener

class MainActivity : AppCompatActivity() {

    private var service: UserService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service = UserService(this)
        SettingsManager.init(this)

        try {
            service!!.GetLessons(object : ApiCallbackListener
            {
                override fun onSucceed(data: ApiResultModel) {
                    if (data.data != null && data.data!!.size>0)
                    {

                        SettingsManager.setValue(Constants().PREF_LESSON_COUNTER, data.data!!.size)

                        Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show();
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



}