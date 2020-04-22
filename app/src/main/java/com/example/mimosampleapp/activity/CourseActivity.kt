package com.example.mimosampleapp.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mimosampleapp.R
import com.example.mimosampleapp.model.Lesson
import com.example.mimosampleapp.model.LessonCompletion
import com.example.mimosampleapp.model.LessonContent

class CourseActivity : AppCompatActivity(), View.OnClickListener {

    var editText: EditText? = null
    var answer: String =""
    var NoInput: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val content_layout: LinearLayout = findViewById(R.id.content_layout)
        val continue_btn: Button = findViewById(R.id.continue_btn)
        continue_btn.setOnClickListener(this)
        editText = EditText(this)

        val leson = intent.getSerializableExtra("lesson") as? Lesson

        val completion = LessonCompletion()
        completion.ID = leson!!.lessonId
        completion.StartTime = System.currentTimeMillis()

        if (leson.input != null)
        {
            NoInput = false
            var temp = ""
            for (item : LessonContent in leson.contents!!)
            {
                temp = temp+item.text
            }

            try {
                    var s = leson.input!!.startIndex
                    var end = leson.input!!.endIndex
                    answer = temp.substring(s!!,end!!)
                    var f = temp.substring(0,s)
                    var l = temp.substring(end,temp.lastIndex+1)



                        val textView = TextView(this)
                        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        textView.setText(f)
                        textView.setTextColor(Color.parseColor("#000000"))
                        textView.setPadding(2,2,2,2)
                        content_layout.addView(textView)


                        editText!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        editText!!.setTextColor(Color.parseColor("#000000"))
                        editText!!.setPadding(2,2,2,2)
                        content_layout.addView(editText)

                        val textView1 = TextView(this)
                        textView1.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        textView1.setText(l)
                        textView1.setTextColor(Color.parseColor("#000000"))
                        textView1.setPadding(2,2,2,2)
                        content_layout.addView(textView1)


            } catch (e: Exception) {
            }

        }
        else if (leson.input == null)
        {
           for (item : LessonContent in leson.contents!!)
           {
               val textView = TextView(this)
               textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
               textView.setText(item.text)
               textView.setTextColor(Color.parseColor(item.color))
               textView.setPadding(2,2,2,2)
               content_layout.addView(textView)
           }

        }

    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.getId()) {

                R.id.continue_btn -> {
                    if (!NoInput)
                    {
                        if (editText!!.text.equals(answer))
                        {

                        }
                    }
                    else
                    {

                    }





                }
            }
        }

    }
}
