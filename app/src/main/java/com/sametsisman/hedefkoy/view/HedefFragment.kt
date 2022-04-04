package com.sametsisman.hedefkoy.view

import android.app.Application
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.sametsisman.hedefkoy.R
import com.sametsisman.hedefkoy.adapter.HedefAdapter
import com.sametsisman.hedefkoy.databinding.FragmentHedefBinding
import com.sametsisman.hedefkoy.model.Hedef
import com.sametsisman.hedefkoy.service.HedefDao
import com.sametsisman.hedefkoy.service.HedefDatabase
import com.sametsisman.hedefkoy.util.CustomSharedPreferences
import com.sametsisman.hedefkoy.viewmodel.BaseViewModel
import com.sametsisman.hedefkoy.viewmodel.HedefViewModel
import kotlinx.android.synthetic.main.fragment_hedef.*
import kotlinx.android.synthetic.main.recyclerview_row.*

class HedefFragment() : Fragment() {
    private lateinit var hedefArrayList: ArrayList<Hedef>
    private lateinit var viewModel : HedefViewModel
    private lateinit var viewHolder: RecyclerView.ViewHolder
    private lateinit var customSharedPreferences : CustomSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hedefArrayList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hedef, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customSharedPreferences = CustomSharedPreferences(view.context.applicationContext)

        if(customSharedPreferences.getWhereIsCome() == 2){
            eskiyiGoster()
        }

        viewModel = ViewModelProviders.of(this).get(HedefViewModel::class.java)
        initChip()
        button.setOnClickListener {
            hedefOlustur(it)
        }


    }

    fun eskiyiGoster(){
        textFieldBaslikEditText.setText(customSharedPreferences.getBaslik())
        textFieldHedefEditText.setText(customSharedPreferences.getHedef())
        chipGroup.visibility = View.GONE
        button.visibility = View.GONE
    }


    fun hedefOlustur(view: View){

        val baslik = textFieldBaslikEditText.text.toString()
        val hedef = textFieldHedefEditText.text.toString()
        val time = System.currentTimeMillis()

        if(weekOrHourText.text.toString() == "Hafta"){

            val hafta = editText1.text.toString()
            val gun = editText2.text.toString()

            if(baslik.isNotEmpty() && hedef.isNotEmpty() && hafta.isNotEmpty() && gun.isNotEmpty()){
                val hedefler = Hedef(baslik,hedef,hafta,gun,null,null,time)
                viewModel.setData(hedefler)
                val action = HedefFragmentDirections.actionHedefFragmentToRecyclerviewFragment()
                Navigation.findNavController(view).navigate(action)
            }else{
                Toast.makeText(context,"Boş bırakılan alanları doldurunuz.",Toast.LENGTH_LONG).show()
            }

        }else{

            val saat = editText1.text.toString()
            val dakika = editText2.text.toString()

            if(baslik.isNotEmpty() && hedef.isNotEmpty() && saat.isNotEmpty() && dakika.isNotEmpty()){
                val hedefler = Hedef(baslik,hedef,null,null,saat,dakika,time)
                viewModel.setData(hedefler)
                val action = HedefFragmentDirections.actionHedefFragmentToRecyclerviewFragment()
                Navigation.findNavController(view).navigate(action)
            }else{
                Toast.makeText(context,"Boş bırakılan alanları doldurunuz.",Toast.LENGTH_LONG).show()
            }

        }
    }

    fun initChip(){
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if(group.checkedChipId == chipUzun.id){
                linearLayoutZaman.visibility = View.VISIBLE
                weekOrHourText.text = "Hafta"
                dayOrMinuteText.text = "Gün"
            }else if(group.checkedChipId == chipKısa.id){
                linearLayoutZaman.visibility = View.VISIBLE
                weekOrHourText.text = "Saat"
                dayOrMinuteText.text = "Dakika"
            }else{
                linearLayoutZaman.visibility = View.INVISIBLE
            }
        }
    }


}