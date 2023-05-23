package com.example.roomapp.fragments.list

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.data.RegisterDatabase
import com.example.roomapp.databinding.FragmentListBinding
import com.example.roomapp.model.Register
import com.example.roomapp.model.UserData
import com.example.roomapp.util.generateFile
import com.example.roomapp.viewmodel.UserViewModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File


class ListFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var binding: FragmentListBinding
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    private lateinit var registerList: List<Register>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)

//        Add Menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //RecyclerView
        val adapter = ListAdapter()

        binding.recyclerview1.adapter = adapter

        binding.recyclerview1.layoutManager = LinearLayoutManager(requireContext())

        //UserViewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner,Observer{user->
            adapter.setData(user)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.fetchButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_fireFragment)
        }

        val listDao = RegisterDatabase.getInstance(requireContext()).registerDao
        listDao.getAllUsers()?.observe(viewLifecycleOwner){
            it?.let {

                registerList=it
                Log.d("registerlist",registerList.toString())
//                it.forEach {
//                    Log.d("listUser",it.userName.toString())
//                    Log.d("listpsw",it.passwrd.toString())
//                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sync_menu,menu)
//        val search = menu?.findItem(R.id.menu_search)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_sync){
//            deleteAllUsers()
            syncAllUsers()
            Toast.makeText(requireContext(),"Sync", Toast.LENGTH_SHORT).show()
        }

        if(item.itemId == R.id.menuLogout){
            findNavController().navigate(R.id.action_listFragment_to_loginFragment)
        }

        if(item.itemId == R.id.export_db_table){
            exportDatabaseToCSVFile()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun exportDatabaseToCSVFile() {
        val csvFile = generateFile(requireContext(),"example.csv")
        csvWriter().open(csvFile!!,append = false){
            //Header
            writeRow(listOf("[id]","[userId]","[firstname]","[lastname]","[username]","[password]"))
            registerList.forEachIndexed { index, register ->
                writeRow(listOf(index,register.userId,register.firstName,register.lastName,register.userName,register.passwrd))
            }
        }

        Toast.makeText(requireContext(),"Successfully exported database",Toast.LENGTH_LONG).show()
        exportCSV()

    }


    private fun exportCSV(){
//        val csv = Environment.getExternalStorageDirectory().absolutePath + "/example.csv"


        val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filelocation = root.absolutePath+ "/" + "example.csv"
        Log.d("filelocation",filelocation)
        val intent = Intent(Intent.ACTION_SENDTO)
        val message = "File to be shared is example.csv."
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, message)
        intent.data = Uri.parse("mailto:nalinkeshav99@gmail.com")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val csv_File = File(filelocation)

        val contentUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.fileprovider", csv_File)
        intent.putExtra(Intent.EXTRA_STREAM, contentUri)

        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION


        startActivity(intent)
    }


    private fun syncAllUsers() {
        var ss = mUserViewModel.readAllData.value;
        Log.d("LISTSIZE",  ss?.size.toString());
        if (ss != null) {
            val userData= UserData()
            userData.value=ss
            fireStoreDatabase.collection("users").document("super")
                .set(userData)
                .addOnSuccessListener {
                    Log.d(TAG,"Added document with ID  ")
                }
                .addOnFailureListener{
                    Log.w(TAG,"Error adding document")
                }
        }
    }


    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }



}