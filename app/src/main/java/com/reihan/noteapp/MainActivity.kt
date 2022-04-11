package com.reihan.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Untuk Action Bar
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment?
//
//        if (navHostFragment != null){
//            val appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)
//            setupActionBarWithNavController(navHostFragment.navController ,appBarConfiguration)
//        }
    }

    //Untuk Menampilkan Action Bar
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_home, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return super.onSupportNavigateUp() || navController.navigateUp()
    }

}