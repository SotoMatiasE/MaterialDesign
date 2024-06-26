package com.example.mdc

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
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
                .setAction(R.string.caard_to_go) {
                    Toast.makeText(this, R.string.card_historial, Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        loadUrl()

        binding.content.cbEnablePass.setOnClickListener{
            //de esta forma siempre adquiere el valor contrario al que tiene
            binding.content.tilPassword.isEnabled = !binding.content.tilPassword.isEnabled
        }

        //CARGA IMAGEN SEGUN LA URL DEL edUrl
        binding.content.edUrl.onFocusChangeListener = View.OnFocusChangeListener { _, focused ->
            var errorStr: String? = null
            val url = binding.content.edUrl.text.toString()
            //Extraer el texto introducido dentro del editText
            //validacion de la url
            if(!focused){
                if (url.isEmpty()){
                    errorStr = getString(R.string.card_required)
                }else if (URLUtil.isValidUrl(url)){
                    loadUrl(url)
                }else {
                    errorStr = getString(R.string.card_invalid_url)
                }
            }
            binding.content.tilURL.error = errorStr
        }

        binding.content.toogleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            binding.content.root.setBackgroundColor(
                when(checkedId){
                    R.id.btnRed -> Color.RED
                    R.id.btnBlue -> Color.BLUE
                    else -> Color.GREEN
                }
            )
        }

        binding.content.swFab.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.text = getString(R.string.card_hide_fab)
                binding.fab.show()
            }else{
                button.text = getString(R.string.card_show_fab)
                binding.fab.hide()
            }
        }

        binding.content.sldVol.addOnChangeListener { slider, value, fromUser ->
            binding.content.tvSubtitle.text = "Vol: $value"
        }

        binding.content.cpEmail.setOnCheckedChangeListener { chip, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.content.cpEmail.setOnCloseIconClickListener {
            binding.content.cpEmail.visibility = View.GONE
        }
    }

    private fun loadUrl(url: String = "https://www.assemblyai.com/blog/content/images/2022/07/How-Imagen-Actually-Works.png"){
        /*descargar imágenes y guardarlas en caché para ser utilizadas
                posteriormente por una aplicación Android*/
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)//manejo de la cache
            .centerCrop()// la imagen se adapta a la imgView
            //into ES PARA DARLE DONDE QUEREMOS QUE SE CARGE LA IMAGEN
            .into(binding.content.imgCover)//la vista donde se va a ver la imagen
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