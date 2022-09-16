package com.example.week1_0706012110034

import Arraylist.Globalvar
import Model.Animal
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_animal_add.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_animal.*

class AnimalAddActivity : AppCompatActivity() {
    private lateinit var  animal: Animal

    private var position= -1
    private var uri: Uri = Uri.EMPTY

//    private var getpos= intent.getIntExtra("getpos", -1)


    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            uri = it.data?.data!! // GET PATH TO IMAGE FROM GALLEY
            if (uri != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
            }
            fotohewan.setImageURI(uri) // MENAMPILKAN DI IMAGE VIEW
            if (position!=-1)
                Globalvar.listDataAnimal[position].imageUri = uri.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_add)
       //untuk mengecek edit atau add yang dilakukan
        val et = intent.getIntExtra("edit", 0)
        if (et==1){
            textviewedit.visibility= View.VISIBLE
            textViewadd.visibility= View.INVISIBLE
            editButton1.visibility= View.VISIBLE
            addButton.visibility= View.INVISIBLE
        }

        GetIntent()
        Listener()
    }

    private fun GetIntent() {
        position = intent.getIntExtra("position", -1)
        if (position != -1){
            val animal = Globalvar.listDataAnimal[position]
            Display(animal)
        }
    }
    private fun Display(animal: Animal){
        namaHewanText.setText(animal.namaHewan)
        jenisHewanText.setText(animal.jenisHewan)
        usiaHewanText.setText(animal.usiaHewan)

        if (animal.imageUri!!.isNotEmpty()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                baseContext.getContentResolver().takePersistableUriPermission(
                    Uri.parse(animal.imageUri),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
            fotohewan.setImageURI(Uri.parse(animal.imageUri))

        }

    }
    private fun Listener() {
        addfotohewan.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        fotohewan.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }
        addButton.setOnClickListener {

            var namaHewan = namaHewanText.text.toString().trim()
            var jenisHewan = jenisHewanText.text.toString().trim()
            var usiaHewan = usiaHewanText.text.toString().trim()

            animal = Animal(namaHewan, jenisHewan, usiaHewan, uri.toString())
        checker()

//            if (Globalvar.listDataAnimal.isNotEmpty()){
//                val myIntent = Intent(this, MainActivity::class.java).apply {
//                    val et = 1;
//
//                    putExtra("a", et)
//
//
//                }
//
//                startActivity(myIntent)
//            }


        }
        editButton1.setOnClickListener {
            var namaHewan = namaHewanText.text.toString().trim()
            var jenisHewan = jenisHewanText.text.toString().trim()
            var usiaHewan = usiaHewanText.text.toString().trim()

            animal = Animal(namaHewan, jenisHewan, usiaHewan, uri.toString())

            checker()
//finish()
            //Toast.makeText(this, "Animal has been edited", Toast.LENGTH_SHORT).show()
        }


        backbutton.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun checker() {
        //masih belum lengkap!!!
        var isCompleted: Boolean = true

        if (animal.imageUri!!.isEmpty()) { //bug
            Toast.makeText(baseContext, "Please pick picture and input again!!", Toast.LENGTH_LONG).show()
            isCompleted = false
            if (animal.namaHewan!!.isEmpty()) {
                namaHewanText.error = "Please enter animal name!!"
                isCompleted = false

                if (animal.jenisHewan!!.isEmpty()) {
                    jenisHewanText.error = "Please enter animal type!!"
                    isCompleted = false

                    if (animal.usiaHewan!!.isEmpty()) {
                        usiaHewanText.error = "Please enter animal age!!"
                        isCompleted = false
                    }

                }


            }
            //rating
            if (animal.jenisHewan!!.isEmpty()) {
                jenisHewanText.error = "Please enter animal type!!"
                isCompleted = false
            }
            //genre
            if (animal.usiaHewan!!.isEmpty()) {
                usiaHewanText.error = "Please enter animal age!!"
                isCompleted = false
            }

            //product= Product(id,user_id,productname,category,expdate,quantity,"","")
        } else {

            fotohewan.setImageURI((Uri.parse(animal.imageUri)))
            if (animal.imageUri!!.isNotEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    baseContext.getContentResolver().takePersistableUriPermission(
                        Uri.parse(animal.imageUri),
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
                fotohewan.setImageURI((Uri.parse(animal.imageUri)))

            }

        }
        if (animal.namaHewan!!.isEmpty()) {
            namaHewanText.error = "Please enter animal name!!"
            isCompleted = false
        }
        //rating
        if (animal.jenisHewan!!.isEmpty()) {
            jenisHewanText.error = "Please enter animal type!!"
            isCompleted = false
        }
        //genre
        if (animal.usiaHewan!!.isEmpty()) {
            usiaHewanText.error = "Please enter animal age!!"
            isCompleted = false
        }



        if (isCompleted) {

            if (position == -1) {
                Globalvar.listDataAnimal.add(animal)
                val myIntent = Intent(this, MainActivity::class.java).apply {
                    val et = 1;

                    putExtra("a", et)


                }

                startActivity(myIntent)

            } else {
                Globalvar.listDataAnimal[position] = animal
                val myIntent = Intent(this, MainActivity::class.java).apply {
                    val et = 1;

                    putExtra("a", et)


                }

                startActivity(myIntent)

            }
//finish()

        }
    }
    override fun onResume() {
        super.onResume()
        if (position!=-1){
            val ANIMALL = Globalvar.listDataAnimal[position]
            Display(ANIMALL)
        }

    }

}