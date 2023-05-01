package com.example.todoapp.view

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.notesAdapter
import com.example.todoapp.database.CatDb
import com.example.todoapp.database.NotesDb
import com.example.todoapp.databinding.ActivityHomeBinding
import com.example.todoapp.model.CatModel
import com.example.todoapp.model.NoteModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton


class HomeAct : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var binding: ActivityHomeBinding
    var FragAdd = AddNotesFrg()
    var Fragupdate = UpdateNoteFrag()
    var up_title: String = ""
    var up_note: String = ""
    var up_cat: String = ""
    var up_priority: Int = 0
    var up_position: Int = 0
    var up_userid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        loaddata(0)



        binding.ftAddnotes.setOnClickListener {
            Toast(this).ShowCustomToast("Add Notes", this)
            replaceFragment(FragAdd, "Compose")


        }
        binding.icAddCat.setOnClickListener {
            var d = Dialog(this)
            d.setContentView(R.layout.custom_dialog)
            d.setCancelable(true)
            d.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var btnsave = d.findViewById<MaterialButton>(R.id.btn_cat)
            var tv_category = d.findViewById<EditText>(R.id.tv_category)


            btnsave.setOnClickListener {
                var catlist: ArrayList<CatModel>
                var dbHelper = CatDb(this)


                dbHelper.insert_cat(
                    CatModel(
                        it.id, tv_category.text.toString()
                    )
                )

                Toast(this).ShowCustomToast("Saved", this)

                d.dismiss()
            }
            d.show()
        }
        binding.chipSort.setOnClickListener {
            var d = BottomSheetDialog(this)
            d.setContentView(R.layout.bottomsheet_dialog)
            d.setCancelable(true)
            d.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var tv_hl = d.findViewById<TextView>(R.id.tv_hl)
            var tv_lh = d.findViewById<TextView>(R.id.tv_lh)

            tv_hl?.setOnClickListener {
                loaddata(4)
            }

            tv_lh?.setOnClickListener {

                loaddata(5)
            }


            d.show()
        }

        binding.apply {
            chip1.setOnClickListener { loaddata(1) }
            chip2.setOnClickListener { loaddata(2) }
            chip3.setOnClickListener { loaddata(3) }
            chipAll.setOnClickListener { loaddata(0) }
        }


    }

    private fun loaddata(retrivenum: Int) {
        val layoutManager = GridLayoutManager(this, 1)
        binding.recview.layoutManager = layoutManager
        var userlist: java.util.ArrayList<NoteModel>
        var dbRetrivehelper = NotesDb(this)
        if (retrivenum != 0) {
            userlist = dbRetrivehelper.retrieve(retrivenum) as java.util.ArrayList<NoteModel>
        } else {
            userlist = dbRetrivehelper.retrieve(0) as java.util.ArrayList<NoteModel>

        }


        var adapter =
            notesAdapter(this, userlist, object : notesAdapter.OnclickCardview {
                override fun Onclickbtn(
                    position: Int,
                    userid: Int,
                    title: String,
                    note: String,
                    category: String,
                    priority: Int
                ) {
                    Handler().postDelayed({
                        // Your code to be executed after delay
                        var dbhelper_delete = NotesDb(this@HomeAct)

                        dbhelper_delete.note_delete(
                            NoteModel(

                                userid, "", "", "", 0
                            )
                        )
                        //removing item at an specific place
                        userlist.removeAt(position)
                        //telling recycler view that data has changed
                        binding.recview.adapter?.notifyDataSetChanged()
                    }, 3000)

                    Toast(this@HomeAct).ShowCustomToast("Congratulation", this@HomeAct)
                }

                override fun onclikitem(
                    position: Int,
                    userid: Int,
                    title: String,
                    note: String,
                    category: String,
                    priority: Int
                ) {
                    val fragment = UpdateNoteFrag()
                    val bundle = Bundle()
                    bundle.putString("title", title)
                    bundle.putString("note", note)
                    bundle.putString("category", category)
                    bundle.putInt("priority", priority)
                    bundle.putInt("userid", userid)
                    bundle.putInt("position", position)
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.cointainerview, fragment)
                        .addToBackStack(null)
                        .commit()


                    binding.apply {
                        cointainerview.visibility = View.VISIBLE
                        ftAddnotes.visibility = View.INVISIBLE
                        recview.visibility = View.GONE
                        chiplayout.visibility = View.GONE
                        toolbar.title = "Update"
                    }


                }

            }
            )
        binding.recview.adapter = adapter

    }

    private fun replaceFragment(fragment: Fragment, title: String) {


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.apply {
            setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            refreshFragment(fragment)
            replace(R.id.cointainerview, fragment)
            commit()

        }


        binding.apply {
            cointainerview.visibility = View.VISIBLE
            ftAddnotes.visibility = View.INVISIBLE
            recview.visibility = View.GONE
            chiplayout.visibility = View.GONE
            toolbar.title = title
        }

    }


    private fun refreshFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.cointainerview)
        if (currentFragment != null && currentFragment::class == fragment::class) {
            supportFragmentManager.beginTransaction().detach(currentFragment)
                .attach(currentFragment).commit()
        }
    }


    override fun onBackPressed() {
        // Here you want to show the user a dialog box
        binding.cointainerview.visibility = View.GONE
        binding.ftAddnotes.visibility = View.VISIBLE
        binding.toolbar.title = "Notes"
        binding.recview.visibility = View.VISIBLE
        binding.chiplayout.visibility = View.VISIBLE
        loaddata(0)


        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()


        } else {
            loaddata(0)

        }


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity() // This will close the app
        } else {
            Toast(this).ShowCustomToast("Press back to exit", this)
            backPressedTime = System.currentTimeMillis()
        }
    }

}

fun Toast.ShowCustomToast(message: String, activity: Activity) {

    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast, activity.findViewById(R.id.toast_container)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.toast_text)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()

    }
}

