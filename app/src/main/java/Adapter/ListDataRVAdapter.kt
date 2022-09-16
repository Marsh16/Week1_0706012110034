package Adapter
import Arraylist.Globalvar
import Interface.CardListener
import Model.Animal
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.week1_0706012110034.AnimalAddActivity
import com.example.week1_0706012110034.MainActivity
import com.example.week1_0706012110034.R
import kotlinx.android.synthetic.main.card_animal.view.*
import kotlin.coroutines.coroutineContext


class ListDataRVAdapter (val listAnimal: ArrayList<Animal>, val cardListener: CardListener):
    RecyclerView.Adapter<ListDataRVAdapter.viewHolder>() {
    //private var position = -1

    class viewHolder(val itemView: View, val cardListener1: CardListener) :
        RecyclerView.ViewHolder(itemView) {

//manage card, membuat card view

        val namahewan_card = itemView.namaHewan
        val jenishewan_card = itemView.jenisHewan
        val usiahewan_card = itemView.usiaHewan
        val fotohewan_card = itemView.fotoHewan
        val tomboledit = itemView.editButton
        val tomboldelete = itemView.deleteButton




        fun setData(data: Animal) {
           tomboledit.setOnClickListener {
//              tomboledit.setText("nasjdn")

               v ->
               val intent = Intent(v.context, AnimalAddActivity::class.java).apply {
                   val et = 1;
                   putExtra("position", position)
                   putExtra("edit", et)
               }
               v.context.startActivity(intent)
           }
            tomboldelete.setOnClickListener {
                v ->
                val mAlertDialog = AlertDialog.Builder(v.context)
                //set alertdialog icon
                mAlertDialog.setTitle("Delete animal") //set alertdialog title
                mAlertDialog.setMessage("Are you sure you want to delete this animal?") //set alertdialog message
                mAlertDialog.setPositiveButton("Delete") { dialog, id ->
                    //perform some tasks here
                    Toast.makeText(v.context, Globalvar.listDataAnimal.get(position).namaHewan + " has been deleted", Toast.LENGTH_SHORT).show()
                    Globalvar.listDataAnimal.removeAt(position)

//                    finish()
                    if (Globalvar.listDataAnimal.isNotEmpty()){

                        val myIntent = Intent(v.context, MainActivity::class.java).apply {
                            val et = 0;

                            putExtra("a", et)

                        }

                        v.context.startActivity(myIntent)
                    }else{
                        val myIntent = Intent(v.context, MainActivity::class.java).apply {
                            val et = 2;

                            putExtra("a", et)

                        }

                        v.context.startActivity(myIntent)
                    }

                }
                mAlertDialog.setNegativeButton("Cancel") { dialog, id ->

                }
                mAlertDialog.show()

            }
            namahewan_card.text= data.namaHewan
            jenishewan_card.text= data.jenisHewan
            usiahewan_card.text= data.usiaHewan
            if (!data.imageUri!!.isEmpty())
                fotohewan_card.setImageURI(Uri.parse(data.imageUri))
            // itemview cardnya di click
            itemView.setOnClickListener {
                cardListener1.onCardClick(adapterPosition)
            }

        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListDataRVAdapter.viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_animal, parent, false)
        return viewHolder(view, cardListener)

    }

    override fun onBindViewHolder(holder: ListDataRVAdapter.viewHolder, position: Int) {
        holder.setData(listAnimal[position])
        //mengisi card viewnya 100/ 10
    }

    override fun getItemCount(): Int {
        //mengambil jumlah
        return listAnimal.size
    }

}