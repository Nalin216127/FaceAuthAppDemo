package com.example.roomapp.fragments.fire

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.databinding.FragmentFirestoreBinding
import com.example.roomapp.databinding.FragmentListBinding
import com.example.roomapp.model.User
import com.example.roomapp.model.UserData
import com.example.roomapp.model.UserNew
import com.example.roomapp.viewmodel.UserViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.PropertyName
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson


class FireFragment:Fragment() {
    private lateinit var mUserViewModel: UserViewModel
    var userList:ArrayList<User> = ArrayList<User>()
//    lateinit var userList : Task<DocumentSnapshot>
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var binding: FragmentFirestoreBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirestoreBinding.inflate(inflater,container,false)
//        val view = inflater.inflate(R.layout.fragment_firestore, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView
        val adapter = FireAdapter()


//        adapter.setData(userList)
       binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        db.collection("users").document("super").get()
            .addOnSuccessListener { documentSnapshot ->
//                documentSnapshot?.let {
////                    val hasmap = HashMap<Int, User>()
//                val data = documentSnapshot.get("value")
//                Log.d("To", data.toString())

//                     val map :  List<User> = (data as List<User>)
//                Log.d("To1", map.toString())
//                    var listA = ArrayList<HashMap<String, List<User>>()
                      val data :UserData?= documentSnapshot.toObject(UserData::class.java)
                      Log.d("To", data.toString())
//                    data.forEach {
//                        Log.d("To",it.firstName)
//                    }
                data?.value?.let { userList.addAll(it) }

//                    }
//            .addOnSuccessListener {(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val list: MutableList<User> = mutableListOf()
//                     snapshot.getValue(User::class.java)?.let {
//                                it1 -> list.add(it1)
//                        }
//                        Log.d("list", list[0].toString())


//            .addOnSuccessListener { documentSnapshot ->
//                documentSnapshot?.let {
////                    val hasmap = HashMap<Int, User>()
//                    var listA = ArrayList<HashMap<String, User>>()
//                    val data = documentSnapshot.get("value")
//
//                    Log.d("To", data.toString())
////                    listA = data as ArrayList<HashMap<Int, User>>
//                    listA = data as ArrayList<HashMap<String, User>>
//                    Log.e("TAG", listA[0].get(0)?.firstName.toString())
//                    listA.forEach {
//                        it.forEach { i, user ->
//                            userList.add(user)
//                        }
//
//                    }
//                    val list : MutableList<User> = mutableListOf()
//                    val children =documentSnapshot.data
//                    children?.forEach {
//
//                    }
//                    userList.addAll(list)
//                    callback(list)
//                    Gson().fromJson(data, User::class.java)
//               documentSnapshot.toObject<User>()?.let {
//                   userList.clear()
//                   userList.add(it)
//               }

//                    Log.e("TAG", "onViewCreated: "+Gson().toJson(userList) )
               adapter.setData(userList)
                }






//        mUserViewModel.readAllData.observe(viewLifecycleOwner,Observer{user->
//            adapter.setData(user)
//        })

    }
}