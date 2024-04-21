package com.anmp.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anmp.myapplication.model.User
import org.json.JSONObject

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val userLiveData = MutableLiveData<User>()
    val statusRegisterLiveData = MutableLiveData<String>()
    val statusLoginLiveData = MutableLiveData<String>()
    val statusUpdateProfileLiveData=MutableLiveData<String>()
    val statusUpdatePasswordLiveData=MutableLiveData<String>()

    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun register(username:String,firstName:String,lastName: String,email:String,password:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/register.php"

        val userData = JSONObject()
        userData.put("username", username)
        userData.put("first_name", firstName)
        userData.put("last_name", lastName)
        userData.put("email", email)
        userData.put("password", password)

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")


                    if (status == "success") {
                        statusRegisterLiveData.value = status

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    statusRegisterLiveData.value = "error"
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                statusRegisterLiveData.value = "error"
            }) {
            override fun getParams(): MutableMap<String, String> {
                return jsonObjectToMap(userData).toMutableMap()
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun login(username: String, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/login.php"

        val userData = JSONObject()
        userData.put("username", username)
        userData.put("password", password)

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")
                    statusLoginLiveData.value = status

                    if (status == "success") {
                        val userJson = jsonResponse.getJSONObject("user")

                        val idAsString = userJson.getString("id")


                        val id = idAsString.toInt()
                        val username = userJson.getString("username")
                        val firstName = userJson.getString("first_name")
                        val lastName = userJson.getString("last_name")
                        val email = userJson.getString("email")
                        val password = userJson.getString("password")

                        val loggedInUser = User(id, username, firstName, lastName, email, password)
                        userLiveData.value = loggedInUser
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    statusLoginLiveData.value = "error"
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                statusLoginLiveData.value = "error"
            }) {
            override fun getParams(): MutableMap<String, String> {
                return jsonObjectToMap(userData).toMutableMap()
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun updateProfile(id:Int,firstName: String,lastName: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/update_profile.php"

        val userData = JSONObject()
        userData.put("id", id)
        userData.put("first_name", firstName)
        userData.put("last_name",lastName)

        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")


                    if (status == "success") {
                        statusUpdateProfileLiveData.value=status
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    statusUpdateProfileLiveData.value = "error"
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                statusLoginLiveData.value = "error"
            }) {
            override fun getParams(): MutableMap<String, String> {
                return jsonObjectToMap(userData).toMutableMap()
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
    fun updatePassword(id:Int, password: String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/update_password.php"

        val userData = JSONObject()
        userData.put("id", id)
        userData.put("password", password)


        val stringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val status = jsonResponse.getString("status")


                    if (status == "success") {
                        statusUpdatePasswordLiveData.value=status
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    statusUpdatePasswordLiveData.value= "error"
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                statusLoginLiveData.value = "error"
            }) {
            override fun getParams(): MutableMap<String, String> {
                return jsonObjectToMap(userData).toMutableMap()
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun jsonObjectToMap(jsonObject: JSONObject): Map<String, String> {
        val map = mutableMapOf<String, String>()
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            map[key] = jsonObject.getString(key)
        }
        return map
    }

}