package com.example.newstoday

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(){
    var pageNo = 1 ;
    var totalResults = -1
    lateinit var adapter: NewsAdapter
    private val articals = mutableListOf<Articals>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NewsAdapter(this@MainActivity, articals)
        newsList.adapter = adapter
//        newsList.layoutManager = LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        newsList.layoutManager = layoutManager
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                if(totalResults > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount-5) {
                    pageNo ++ ;
                    getNews()
                }
            }

        })
        getNews()
    }

    private fun getNews() {
        val news = NewsService.newsInstance.get_Data("in", pageNo)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    totalResults = news.totalResults
                    articals.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d("Soumik", "There is an Error in the News Data")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Soumik", "Error", t)
            }

        })

    }


}

