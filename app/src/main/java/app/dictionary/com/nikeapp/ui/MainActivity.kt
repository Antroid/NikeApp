package app.dictionary.com.nikeapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.dictionary.com.nikeapp.R
import app.dictionary.com.nikeapp.data.local.EntryData
import app.dictionary.com.nikeapp.viewmodels.DictionaryViewModel
import app.dictionary.com.nikeapp.viewmodels.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(),TextWatcher,View.OnClickListener{

    var sortBy : Int = 0
    var orderBy : Int = 0

    var originalList : List<EntryData> = ArrayList()

    companion object{
        const val SORT_BY_NONE = 0
        const val SORT_BY_THUMBS_UP = 1
        const val SORT_BY_THUMBS_DOWN = 2


        const val ORDER_BY_NONE = 0
        const val ORDER_BY_ASC = 1
        const val ORDER_BY_DESC = 2
    }

    @set:Inject
    lateinit var viewModelFactory : ViewModelFactory

    var dictionaryViewModel : DictionaryViewModel?=null

    var adapter : EntryRecycleViewAdapter = EntryRecycleViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        dictionaryViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DictionaryViewModel::class.java)
        initObservers()

    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.thumbs_up_title->{

                orderBy = if(sortBy!= SORT_BY_THUMBS_UP) {
                    ORDER_BY_ASC
                } else {
                    (orderBy + 1) % 3
                }

                sortBy = SORT_BY_THUMBS_UP


                when(orderBy)
                {
                    ORDER_BY_NONE->{
                        thumbs_up_title.tag = 0//for testing
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,0,0)
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,0,0)
                        sortResults()
                    }

                    ORDER_BY_ASC->{
                        thumbs_up_title.tag = R.drawable.ic_keyboard_arrow_up_24px//for testing
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,R.drawable.ic_keyboard_arrow_up_24px,0)
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,0,0)
                        sortResults()
                    }

                    ORDER_BY_DESC->{
                        thumbs_up_title.tag = R.drawable.ic_keyboard_arrow_down_24px//for testing
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,R.drawable.ic_keyboard_arrow_down_24px,0)
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,0,0)
                        sortResults()
                    }
                }

            }

            R.id.thumbs_down_title->
            {
                orderBy = if(sortBy!= SORT_BY_THUMBS_DOWN) {
                    ORDER_BY_ASC
                } else {
                    (orderBy + 1) % 3
                }

                sortBy = SORT_BY_THUMBS_DOWN

                when(orderBy)
                {
                    ORDER_BY_NONE->{
                        thumbs_down_title.tag = 0//for testing
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,0,0)
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,0,0)
                        sortResults()
                    }

                    ORDER_BY_ASC->{
                        thumbs_down_title.tag = R.drawable.ic_keyboard_arrow_up_24px//for testing
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,0,0)
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,R.drawable.ic_keyboard_arrow_up_24px,0)

                        sortResults()
                    }

                    ORDER_BY_DESC->{
                        thumbs_down_title.tag = R.drawable.ic_keyboard_arrow_down_24px//for testing
                        thumbs_up_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_up_24px,0,0,0)
                        thumbs_down_title.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_thumb_down_24px,0,R.drawable.ic_keyboard_arrow_down_24px,0)

                        sortResults()
                    }
                }
            }
        }
    }

    private fun initUI()
    {
        setSupportActionBar(tool_bar)
        search.addTextChangedListener(this)

        thumbs_up_title.setOnClickListener(this)
        thumbs_down_title.setOnClickListener(this)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        result_recycle_view.layoutManager = layoutManager
        result_recycle_view.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        result_recycle_view.addItemDecoration(dividerItemDecoration)

        search.requestFocus()

    }

    private fun initObservers() {
        dictionaryViewModel!!.isLoading().observe(this,
            androidx.lifecycle.Observer<Boolean> { loading ->
                loader.visibility = if (loading) View.VISIBLE else View.GONE
                if(loading)
                {
                    result_recycle_view.visibility = View.GONE
                    no_entries.visibility = View.GONE
                }
            })

        dictionaryViewModel!!.isError().observe(this,
            androidx.lifecycle.Observer<Boolean> { isError ->
                if (isError) {
                    no_entries.visibility = View.GONE
                    loader.visibility = View.GONE
                    Toast.makeText(this, getString(app.dictionary.com.nikeapp.R.string.error), Toast.LENGTH_LONG).show()
                }
            })

        dictionaryViewModel!!.getDictionaryEntries().observe(this,
            androidx.lifecycle.Observer<ArrayList<EntryData>> { res ->

                no_entries.visibility = if(res.isEmpty())View.VISIBLE else View.GONE
                result_recycle_view.visibility = if(res.isEmpty())View.GONE else View.VISIBLE

                originalList = res

                //need to sort the value in case the user already chose sort by and order by
                sortResults()

            })

    }

    private fun sortResults()
    {
        val data = originalList
        var res = data
        when(sortBy)
        {
            SORT_BY_THUMBS_DOWN->{

                when(orderBy)
                {
                    ORDER_BY_DESC->{
                        res = data.sortedWith(EntryData.CompareThumbsDownDesc())
                    }
                    ORDER_BY_ASC->{
                        res = data.sortedWith(EntryData.CompareThumbsDownAsc())
                    }
                }
            }

            SORT_BY_THUMBS_UP->{
                when(orderBy)
                {
                    ORDER_BY_DESC->{
                        res = data.sortedWith(EntryData.CompareThumbsUpDesc())
                    }
                    ORDER_BY_ASC->{
                        res = data.sortedWith(EntryData.CompareThumbsUpAsc())
                    }
                }
            }
            SORT_BY_NONE-> {
                res = originalList
            }
        }

        for(item in res) {
            Log.d("anton","${item.thumbUp} ${item.thumbDown}")
        }

        adapter.setData(res)
    }




    override fun afterTextChanged(s: Editable?) {
        dictionaryViewModel!!.getDictionaryTerm(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }






}
