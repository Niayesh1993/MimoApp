package com.example.mimosampleapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.room.Room
import androidx.room.Update
import com.example.mimosampleapp.R
import com.example.mimosampleapp.database.AppDb
import com.example.mimosampleapp.database.CmpEntity
import com.example.mimosampleapp.fragment.content_layout
import com.example.mimosampleapp.model.LessonCompletion
import com.example.mimosampleapp.model.LessonContent
import com.example.mimosampleapp.model.input.Course
import com.example.mimosampleapp.utility.Constants
import com.example.mimosampleapp.utility.SettingsManager
import kotlinx.android.synthetic.main.fragment_course.*
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match

private const val ARG_PARAM1 = "param1"
private var ride: Course? = null
var content_layout: LinearLayout? = null
var continue_btn: Button? = null
var editText: EditText? = null
var answer: String =""
var NoInput: Boolean = true
var LessonCounter = 0
val completion = LessonCompletion()
var Done: ImageButton? = null
var Incorrect: ImageButton? = null
var Well_Done_Layout: LinearLayout? = null
var finish_btn: ImageButton? = null




class CourseFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Course? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            try {
                ride = arguments!!.getParcelable(ARG_PARAM1)
                NoInput = true

            } catch (e: Exception) {

            }
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
        content_layout = view.findViewById(R.id.contentLayout)
        continue_btn = view.findViewById(R.id.check_btn)
        continue_btn!!.setOnClickListener(this)
        editText = EditText(requireContext())
        Done = view.findViewById(R.id.done_btn)
        Done!!.setOnClickListener(this)
        Incorrect = view.findViewById(R.id.wrong_btn)
        Incorrect!!.setOnClickListener(this)
        Well_Done_Layout = view.findViewById(R.id.wellDone_layout)
        finish_btn = view.findViewById(R.id.startAgain_btn)
        finish_btn!!.setOnClickListener(this)


        UpdateUI()
    }


    fun UpdateUI()
    {
        Done!!.visibility = View.GONE
        Incorrect!!.visibility = View.GONE
        Well_Done_Layout!!.visibility = View.GONE
        continue_btn!!.visibility = View.VISIBLE
        content_layout!!.removeAllViews()

        LessonCounter = SettingsManager.getInt(Constants().PREF_LESSON_IN_PROGRESS)

        var t = LessonCounter

        var leson = LessonCounter?.let { ride!!.Course!!.get(it) }


        completion.ID = leson!!.lessonId
        completion.StartTime = System.currentTimeMillis()

        if (leson!!.input != null)
        {
            NoInput = false
            var temp = ""
            for (item : LessonContent in leson.contents!!)
            {
                temp += item.text
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

                R.id.check_btn -> {
                    if (!NoInput)
                    {
                        if (editText!!.text.toString() == answer )
                        {
                            LessonCounter ++
                            SettingsManager.setValue(Constants().PREF_LESSON_IN_PROGRESS, LessonCounter)
                            completion.EndTime = System.currentTimeMillis()
                            AddCompleteLesson(completion)
                            UpdateLessonStatus(completion.ID!!)

                            if (LessonCounter >= SettingsManager.getInt(Constants().PREF_LESSON_COUNTER))
                            {
                                Well_Done_Layout!!.visibility = View.VISIBLE
                                contentLayout!!.visibility = View.GONE
                                continue_btn!!.visibility = View.GONE
                                Incorrect!!.visibility = View.GONE
                            }
                            else
                            {
                                Done!!.visibility = View.VISIBLE
                                continue_btn!!.visibility = View.GONE
                                Incorrect!!.visibility = View.GONE
                                contentLayout!!.visibility = View.GONE

                            }

                        }
                        else
                        {
                            Incorrect!!.visibility = View.VISIBLE
                            editText!!.setText("")
                        }
                    }
                    else
                    {

                        LessonCounter ++
                        SettingsManager.setValue(Constants().PREF_LESSON_IN_PROGRESS, LessonCounter)
                        completion.EndTime = System.currentTimeMillis()
                        AddCompleteLesson(completion)
                        UpdateLessonStatus(completion.ID!!)

                        if (LessonCounter >= SettingsManager.getInt(Constants().PREF_LESSON_COUNTER))
                        {
                            Well_Done_Layout!!.visibility = View.VISIBLE
                        }
                        else
                        {
                            continue_btn!!.visibility = View.GONE
                            Done!!.visibility = View.VISIBLE


                        }

                    }

                }
                R.id.done_btn ->
                {
                    UpdateUI()

                }
                R.id.startAgain_btn ->
                {
                    FinishCourse()

                }
            }
        }
    }

    fun UpdateLessonStatus(id: Int)
    {
        val thread = Thread {

            try {
                var Lesoon_db =
                    Room.databaseBuilder(requireContext(), AppDb::class.java, "LessonDB").build()

                Lesoon_db.lessonDAO().updateLesson(id, true)
            } catch (e: Exception) {
            }

        }
        thread.start()

    }

    fun AddCompleteLesson(cmp: LessonCompletion)
    {
        val thread = Thread {

            try {
                var Cmp_db =
                    Room.databaseBuilder(requireContext(), AppDb::class.java, "CmpDB").build()

                var cmpEntity = CmpEntity()
                cmpEntity.CmpId = cmp.ID!!
                cmpEntity.start = cmp.StartTime!!
                cmpEntity.end = cmp.EndTime!!

                Cmp_db.CmpDAO().saveCmp(cmpEntity)


            } catch (e: Exception) {
            }

        }
        thread.start()
    }

    fun FinishCourse()
    {

        SettingsManager.clearValue(Constants().PREF_LESSON_COUNTER)
        SettingsManager.clearValue(Constants().PREF_LESSON_IN_PROGRESS)
        SettingsManager.setValue(Constants().PREF_ACTIVE_COURSE, false)

        ClearData()

    }

    fun ClearData()
    {

        val thread = Thread {
            var Lesoon_db =
                Room.databaseBuilder(requireContext(), AppDb::class.java, "LessonDB").build()

            var Input_db =
                Room.databaseBuilder(requireContext(), AppDb::class.java, "InputDB").build()

            var Content_db =
                Room.databaseBuilder(requireContext(), AppDb::class.java, "ContentDB").build()

            var Cmp_db =
                Room.databaseBuilder(requireContext(), AppDb::class.java, "CmpDB").build()

            Lesoon_db.lessonDAO().DeletLesson()
            Content_db.ContentDAO().DeletContent()
            Input_db.InputDAO().DeletInput()
            Cmp_db.CmpDAO().DeletCmp()

            getActivity()!!.onBackPressed()

        }
        thread.start()
    }


}
