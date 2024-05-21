package com.example.mdc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
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