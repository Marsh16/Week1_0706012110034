package com.example.week1_0706012110034

import Adapter.ListDataRVAdapter
import Arraylist.Globalvar
import Interface.CardListener
import Model.Animal
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , CardListener {


    private val adapter = ListDataRVAdapter(Globalvar.listDataAnimal, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CheckPermissions()

        setupRecyclerView()

        listener()

    }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(baseContext)
        listDataRV.layoutManager = layoutManager //set layout
        listDataRV.adapter=  adapter //set adapter

    }
    private fun listener(){
        fabaddbutton.setOnClickListener {
            val myIntent = Intent(this, AnimalAddActivity::class.java)
            startActivity(myIntent)
        }
    }
    private fun CheckPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), Globalvar.STORAGE_PERMISSION_CODE)
        } else {
            //Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), Globalvar.STORAGE_PERMISSION_CODE)
        } else {
            //Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Globalvar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == Globalvar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCardClick(position: Int) {
//        val myIntent = Intent(this, DetailActivity::class.java).apply {
//            putExtra("position", position)
//
//        }
//        startActivity(myIntent)
    }


}