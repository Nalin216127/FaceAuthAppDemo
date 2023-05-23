package com.example.roomapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.databinding.CustomRowBinding
import com.example.roomapp.databinding.FireRowBinding
import com.example.roomapp.fragments.fire.FireAdapter
import com.example.roomapp.model.User

class ListAdapter:RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(var binding:CustomRowBinding):RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.MyViewHolder {
        val itemView = CustomRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curretnItem = userList[position]

        holder.binding.apply {
            idTxt.text = curretnItem.id.toString()
            firstNameTxt.text = curretnItem.firstName
            lastNameTxt.text  = curretnItem.lastName
            ageTxt.text = curretnItem.age.toString()

        }

        holder.binding.rowLayout.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(curretnItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

}