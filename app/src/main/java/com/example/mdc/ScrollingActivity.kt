package com.example.mdc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mdc.databinding.ActivityScrollingBinding
import com.google.android.material.bottomappbar.BottomAppBar

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ANIMACION
        binding.fab.setOnClickListener {
            if (binding.bottomAppBar.fabAlignmentMode == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER)
            {
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            }else{
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            }
        }

        binding.bottomAppBar.setNavigationOnClickListener{
            Snackbar.make(binding.root, getString(R.string.evento_exitoso), Snackbar.LENGTH_SHORT)
                .setAnchorView(binding.fab) //mueve el mensaje del snackbar por encima del navigation
                .show()
        }

        //dandole un id a include dentro de activity scroll podemos acceder a todos los botones de
        //content_scrolling
        binding.content.btnSkip.setOnClickListener{
            binding.content.cvAdd.visibility = View.GONE
        }

        //Mensaje con accion con Toast
        /*SnackBar depende de una sola vista, en cambio Toast nos sirve para cambiar de actividad
        o de aplicacion porque es un mensaje que se muestra en la interfaz del sistema*/
        binding.content.btnBuy.setOnClickListener{
            Snackbar.make(it, R.string.card_buying, Snackbar.LENGTH_LONG)
                .setAnchorView(binding.fab) //mueve el mensaje del snackbar por enc
                .setAction(R.string.caard_to_go,{
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                })
                .show()
        }

        /*descargar imágenes y guardarlas en caché para ser utilizadas
        posteriormente por una aplicación Android*/
        Glide.with(this)
            .load("https://www.assemblyai.com/blog/content/images/2022/07/How-Imagen-Actually-Works.png")
            .diskCacheStrategy(DiskCacheStrategy.ALL)//manejo de la cache
            .centerCrop()// la imagen se adapta a la imgView
            //into ES PARA DARLE DONDE QUEREMOS QUE SE CARGE LA IMAGEN
            .into(binding.content.imgCover)//la vista donde se va a ver la imagen

        binding.content.cbEnablePass.setOnClickListener{
            //de esta forma siempre adquiere el valor contrario al que tiene
            binding.content.tilPassword.isEnabled = !binding.content.tilPassword.isEnabled
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}