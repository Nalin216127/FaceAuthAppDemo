package com.example.roomapp.fragments.fire

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.databinding.FireRowBinding
import com.example.roomapp.databinding.FragmentFirestoreBinding
import com.example.roomapp.fragments.list.ListFragmentDirections
import com.example.roomapp.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class FireAdapter(): RecyclerView.Adapter<FireAdapter.MyViewHolder>() {
    private var userFireList = emptyList<User>()

    class MyViewHolder(var binding:FireRowBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireAdapter.MyViewHolder {
        val itemView = FireRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userFireList.size
    }

    override fun onBindViewHolder(holder: FireAdapter.MyViewHolder, position: Int) {
        val currentItem = userFireList[position]

        holder.binding.apply {
            fireId.text = currentItem.id.toString()
            fireFirstName.text = currentItem.firstName
            fireLastName.text = currentItem.lastName
            fireAge.text = currentItem.age.toString()
        }

    }

    fun setData(user: List<User>){
        this.userFireList = user
        notifyDataSetChanged()
    }

}