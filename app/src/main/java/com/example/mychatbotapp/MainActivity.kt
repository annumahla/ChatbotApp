package com.example.mychatbotapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychatbotapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val USER_KEY = "user"
    private val BOT_KEY = "bot"

    val chatModelArrayList: ArrayList<ChatsModel> = ArrayList()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding

        setRecyclerView()
        setView()
    }

    private fun setView() {
        binding.sendMsg.setOnClickListener {
           getResponse(binding.inputText.text.toString())
           binding.inputText.text = null
       }
    }

    private fun getResponse(msg: String) {
        chatModelArrayList.add(ChatsModel(message = msg, USER_KEY))
        contentAdapter.notifyDataSetChanged()

        val url = "http://api.brainshop.ai/get?bid=170161&key=EOsFH1cIepCCnW4p&uid=[uid]&msg="+msg
        val baseUrl = "http://api.brainshop.ai/"

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java).getMessage(url).enqueue(object:
            Callback<BotResponseModel>{
            override fun onResponse(call: Call<BotResponseModel>, response: Response<BotResponseModel>) {
                if(response.isSuccessful){
                    val msgModel = response.body()
                    chatModelArrayList.add(ChatsModel(message = msgModel?.cnt.orEmpty(), sender = BOT_KEY))
                    contentAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BotResponseModel>, t: Throwable) {
                chatModelArrayList.add(ChatsModel(message = "Sorry Could u plz elaborate?", sender = BOT_KEY))
                contentAdapter.notifyDataSetChanged()
            }

        })
    }

    private val contentAdapter by lazy {
        ChatAdapter(chatModelArrayList)
    }

    private fun setRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = contentAdapter
    }

}