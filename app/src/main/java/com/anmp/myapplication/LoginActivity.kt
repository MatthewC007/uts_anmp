package com.anmp.myapplication


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.anmp.myapplication.databinding.ActivityLoginBinding
import com.anmp.myapplication.model.User
import com.anmp.myapplication.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.button.setOnClickListener {
            loginUser()
        }


        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val username = binding.txtInputUsername.text.toString()
        val password = binding.txtPassword.editText?.text.toString()

        userViewModel.login(username, password)


        userViewModel.statusLoginLiveData.observe(this){
            status->

           if (status == "success"){
               Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
               userViewModel.userLiveData.observe(this) { loggedInUser ->
                   if (loggedInUser != null) {

                       saveUserData(loggedInUser)





                       val intent = Intent(this, MainActivity::class.java)
                       startActivity(intent)
                       finish()
                   } else {

                       Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                   }
               }
           }
            else{
               Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
           }
        }



    }

    private fun saveUserData(loggedInUser: User) {
        // Simpan data user ke SharedPreferences
        val sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("id", loggedInUser.id)
        editor.putString("password",loggedInUser.password)
        editor.putString("username", loggedInUser.username)
        editor.putString("firstName", loggedInUser.firstName)
        editor.putString("lastName", loggedInUser.lastName)
        editor.putString("email", loggedInUser.email)
        editor.apply()
    }
}

