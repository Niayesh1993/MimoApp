package com.example.mimosampleapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mimosampleapp.R
import com.example.mimosampleapp.model.LessonCompletion
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.input.Course
import com.example.mimosampleapp.utility.SettingsManager

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private var ride: Course? = null
var content_layout: LinearLayout? = null
var continue_btn: Button? = null
var editText: EditText? = null
var answer: String =""
var NoInput: Boolean = true
var LessonCounter = 0




class CourseFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Course? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            try {
                ride = arguments!!.getParcelable(ARG_PARAM1)

            } catch (e: Exception) {

            }
            var t = ride
          var r =   t!!.Course

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

         var view = inflater.inflate(R.layout.fragment_course, container, false)
        SettingsManager.init(requireContext())
        initView(view)

        return view
    }

    fun initView(view: View)
    {
         content_layout = view.findViewById(R.id.content_layout)
        continue_btn = view.findViewById(R.id.continue_btn)
        continue_btn!!.setOnClickListener(this)
        editText = EditText(requireContext())

        BindData()
    }

    fun BindData()
    {

        UpdateUI()

    }

    fun UpdateUI()
    {
//        val completion = LessonCompletion()
//        completion.ID = leson!!.lessonId
//        completion.StartTime = System.currentTimeMillis()

        content_layout!!.removeAllViews()

        var leson = LessonCounter?.let { ride!!.Course!!.get(it) }
        if (leson!!.input != null)
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



                        val textView = TextView(requireContext())
                        textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        textView.setText(f)
                        textView.setTextColor(Color.parseColor("#000000"))
                        textView.setPadding(2,2,2,2)
                        content_layout!!.addView(textView)


                        editText!!.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        editText!!.setTextColor(Color.parseColor("#000000"))
                        editText!!.setPadding(2,2,2,2)
                        content_layout!!.addView(editText)

                        val textView1 = TextView(requireActivity())
                        textView1.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        textView1.setText(l)
                        textView1.setTextColor(Color.parseColor("#000000"))
                        textView1.setPadding(2,2,2,2)
                        content_layout!!.addView(textView1)


            } catch (e: Exception) {
            }

        }
        else if (leson.input == null)
        {
           for (item : LessonContent in leson.contents!!)
           {
               val textView = TextView(requireActivity())
               textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
               textView.setText(item.text)
               textView.setTextColor(Color.parseColor(item.color))
               textView.setPadding(2,2,2,2)
               content_layout!!.addView(textView)
           }

        }

    }

    fun newInstance(course: Course?): CourseFragment? {
        val fragment = CourseFragment()
        val args = Bundle()
        args.putParcelable(ARG_PARAM1, course)
        fragment.setArguments(args)
        return fragment
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
                        LessonCounter =+ 1
                        UpdateUI()

                    }





                }
            }
        }
    }

}
