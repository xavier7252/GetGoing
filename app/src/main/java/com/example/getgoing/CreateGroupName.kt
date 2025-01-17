package com.example.getgoing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class CreateGroupName : AppCompatActivity() {

    private lateinit var edtGroupName: EditText
    private lateinit var btnGroupCreate: Button
    private lateinit var backBtn: ImageButton

    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        supportActionBar?.hide()
        setContentView(R.layout.activity_create_group_name)

        backBtn = findViewById<ImageButton>(R.id.backToGroupsPage)
        edtGroupName = findViewById(R.id.edt_create_group_name)
        btnGroupCreate = findViewById(R.id.nextToCreateGroupFriends)
        mDbRef = FirebaseDatabase.getInstance().getReference()



        backBtn.setOnClickListener {
            val intent = Intent(this, GroupsPage::class.java)
            finish()
            startActivity(intent)

        }

        btnGroupCreate.setOnClickListener {
            if (edtGroupName.text.toString() == "") {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                var user_test = CurrentUserManager.currentUser
                addGroupToDatabase(edtGroupName.text.toString())

                val intent = Intent(this, CreateGroupFriends::class.java)
                intent.putExtra("From","createGroup")
                finish()
                startActivity(intent)
            }
        }

    }

    private fun addGroupToDatabase(name: String?) {
        var currentUser = CurrentUserManager.currentUser
        if (currentUser != null) {

            CoroutineScope(Dispatchers.Main).launch {
                if (name != null) {
                    GroupManager.createGroup(name, arrayListOf(currentUser.phone!!))

                }
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
        }
    }

}