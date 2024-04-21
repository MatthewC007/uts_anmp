import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anmp.myapplication.model.News
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    val newsLiveData = MutableLiveData<ArrayList<News>>()
    private var queue: RequestQueue? = null

    fun fetchNews() {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/news_list.php?news"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    if (jsonResponse.getString("status") == "success") {
                        val newsArray = jsonResponse.getJSONArray("data")

                        val newsListType = object : TypeToken<ArrayList<News>>() {}.type
                        val newsList = Gson().fromJson<ArrayList<News>>(newsArray.toString(), newsListType)

                        newsLiveData.value = newsList

                    } else {
                        Log.e("NewsViewModel", "Status is not success")
                    }
                } catch (e: JsonSyntaxException) {
                    Log.e("NewsViewModel", "JSON syntax error: ${e.message}")
                } catch (e: Exception) {
                    Log.e("NewsViewModel", "Error fetching news: ${e.message}")
                }
            },
            { error ->
                Log.e("NewsViewModel", "Volley error: ${error.message}")
            }
        )

        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}
