package com.sametsisman.hedefkoy.adapter

import android.app.Application
import android.graphics.Color
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavArgument
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sametsisman.hedefkoy.R
import com.sametsisman.hedefkoy.databinding.RecyclerviewRowBinding
import com.sametsisman.hedefkoy.model.Hedef
import com.sametsisman.hedefkoy.util.CustomSharedPreferences
import com.sametsisman.hedefkoy.view.HedefFragment
import com.sametsisman.hedefkoy.view.RecyclerviewFragmentDirections
import kotlinx.android.synthetic.main.recyclerview_row.*
import kotlin.random.Random

class HedefAdapter(val hedefArrayList: ArrayList<Hedef>) : RecyclerView.Adapter<HedefAdapter.HedefHolder>() {
    private lateinit var sharedPreferences: CustomSharedPreferences
    private var runnable: Runnable = Runnable {  }
    private var handler: Handler = Handler(Looper.getMainLooper())

    class HedefHolder(var view : RecyclerviewRowBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HedefHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RecyclerviewRowBinding>(inflater, R.layout.recyclerview_row,parent,false)
        return HedefHolder(view)
    }

    override fun onBindViewHolder(holder: HedefHolder, position: Int) {
        holder.view.recyclerViewRowText.text = hedefArrayList[position].baslik
        if (hedefArrayList[position].hafta == null){
            val finisTime = (hedefArrayList[position].dakika.toString().toInt() * 60 * 1000 * 1000 * 1000L) + (hedefArrayList[position].saat.toString().toInt() * 60 * 60 * 1000 * 1000 * 1000L)
            holder.view.girilenSureTextView.text = "${hedefArrayList[position].saat} Saat ${hedefArrayList[position].dakika} Dakika"
            if(System.nanoTime() >= hedefArrayList[position].Time!! + finisTime){
                holder.view.recyclerViewRowText.setTextColor(Color.parseColor("#4D423636"))
                holder.view.girilenSureTextView.setTextColor(Color.parseColor("#4D423636"))
                holder.view.kalanSureTextView.visibility = View.INVISIBLE
                holder.view.tamamlandiTextView.visibility = View.VISIBLE
            }else{
                val kalandakika =
                    (hedefArrayList[position].Time!! + finisTime - System.nanoTime()) /60000000000L % 60
                val kalansaat =
                    (hedefArrayList[position].Time!! + finisTime - System.nanoTime()) /60000000000L / 60

                holder.view.kalanSureTextView.text = "${kalansaat} Saat ${kalandakika} Dakika"
            }
        }else if (hedefArrayList[position].saat == null){
            val finisTime = (hedefArrayList[position].hafta.toString().toInt() * 7 * 24 * 60 * 60 * 1000 * 1000 * 1000L) + (hedefArrayList[position].gun.toString().toInt() * 24 * 60 * 60 * 60 * 1000 * 1000 * 1000L)
            holder.view.girilenSureTextView.text = "${hedefArrayList[position].hafta} Hafta ${hedefArrayList[position].gun} Gün"
            if(System.nanoTime() >= hedefArrayList[position].Time!! + finisTime){
                holder.view.recyclerViewRowText.setTextColor(Color.parseColor("#4D423636"))
                holder.view.girilenSureTextView.setTextColor(Color.parseColor("#4D423636"))
                holder.view.kalanSureTextView.visibility = View.INVISIBLE
                holder.view.tamamlandiTextView.visibility = View.VISIBLE
            }else{
                val kalanhafta =
                    ((hedefArrayList[position].Time!! + finisTime - System.nanoTime()) /60000000000L / 60 / 24) / 7
                val kalangun =
                    ((hedefArrayList[position].Time!! + finisTime - System.nanoTime()) /60000000000L / 60 / 24) % 7

                holder.view.kalanSureTextView.text = "${kalanhafta} Hafta ${kalangun} Gün"
            }
        }
        holder.itemView.setOnClickListener {
            sharedPreferences = CustomSharedPreferences(it.context.applicationContext)
            hedefArrayList[position].baslik?.let { it1 -> hedefArrayList[position].hedef?.let { it2 ->
                sharedPreferences.savePosition(it1,
                    it2
                )
            } }
            sharedPreferences.putWhereIsCome(2)
            val action = RecyclerviewFragmentDirections.actionRecyclerviewFragmentToHedefFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return hedefArrayList.size
    }

    fun refreshData(newList: List<Hedef>){
        hedefArrayList.clear()
        hedefArrayList.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteItem(i : Int){
        hedefArrayList.removeAt(i)
        notifyDataSetChanged()
    }



}