package com.sametsisman.hedefkoy.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sametsisman.hedefkoy.R
import com.sametsisman.hedefkoy.adapter.HedefAdapter
import com.sametsisman.hedefkoy.service.HedefDatabase
import com.sametsisman.hedefkoy.util.CustomSharedPreferences
import com.sametsisman.hedefkoy.util.SwipeRecyclerview
import com.sametsisman.hedefkoy.viewmodel.RecyclerviewViewModel
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.recyclerview_row.*

class RecyclerviewFragment() : Fragment() {
    private lateinit var viewHelp : View
    private val hedefAdapter = HedefAdapter(arrayListOf())
    private lateinit var viewModel : RecyclerviewViewModel
    private lateinit var customSharedPreferences : CustomSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHelp = view
        customSharedPreferences = CustomSharedPreferences(view.context.applicationContext)

        viewModel = ViewModelProviders.of(this).get(RecyclerviewViewModel::class.java)
        viewModel.getData()

        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = hedefAdapter
        val swipeRecyclerview = object : SwipeRecyclerview(view.context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        val deletedItem = hedefAdapter.hedefArrayList[viewHolder.adapterPosition]
                        hedefAdapter.deleteItem(viewHolder.adapterPosition)
                        viewModel.deleteItem(deletedItem)
                        }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeRecyclerview)
        itemTouchHelper.attachToRecyclerView(recyclerview)
        observeLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.hedef_ekle){
            customSharedPreferences.putWhereIsCome(1)
            val action = RecyclerviewFragmentDirections.actionRecyclerviewFragmentToHedefFragment()
            Navigation.findNavController(viewHelp).navigate(action)
        }

        return super.onOptionsItemSelected(item)


    }

    fun observeLiveData(){
        viewModel.hedefler.observe(viewLifecycleOwner, Observer {

            it?.let {
               recyclerview.visibility = View.VISIBLE
                bosTextView.visibility = View.GONE
                bosTextView2.visibility = View.GONE
               hedefAdapter.refreshData(it)
            }
            if(it.isEmpty()){
                recyclerview.visibility = View.GONE
                bosTextView.visibility = View.VISIBLE
                bosTextView2.visibility = View.VISIBLE
            }

        })
    }
}