package com.furkanmulayim.cardholder.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.ActivityMainBinding
import com.furkanmulayim.cardholder.utils.showExitDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val rootView = binding.root
        setBottNavMenu()
        deneme(rootView)
    }

    private fun deneme(rootView: View) {

        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff = rootView.rootView.height - rootView.height
            if (heightDiff > 300) {
                // Klavye açık, bottom navigation menüsünü gizle
                bottomNavGizle()
            } else {
                // Klavye kapalı, bottom navigation menüsünü göster
                bottomNavGoster()
                girisYapildiMi()
            }
        }

    }

    private fun setBottNavMenu() {
        binding.bottomNavMenu.apply {
            selectedItemId = R.id.cardListFragment
            binding.itemFloatActionButton1.setOnClickListener {
                selectedItemId = R.id.cardListFragment
            }
            binding.itemFloatActionButton.setOnClickListener {
                selectedItemId = R.id.addCardFragment
            }
            binding.itemFloatActionButton3.setOnClickListener {
                selectedItemId = R.id.meFragment
            }
            setupWithNavController(findNavController(R.id.fragmentContainerView))
        }
        girisYapildiMi()
    }


    fun girisYapildiMi() {
        val navController: NavController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavMenu.setupWithNavController(navController)

        // Giris Yapılmadan Bottom Nav Menü -- Header Bar Görünmeyecek
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.lockFragment) {
                bottomNavGizle()
            } else {
                bottomNavGoster()
            }

            if (destination.id == R.id.cardDetailsFragment || destination.id == R.id.lockFragment || destination.id == R.id.driverLicenceFragment || destination.id == R.id.passUpdateFragment) {
                bottomNavEtkisiz()
            } else {
                bottomNavEtkin()
            }
        }
    }

    private fun bottomNavEtkisiz() {
        binding.bottomNavMenu.isEnabled = false
        binding.bottomNavMenu.isClickable = false
        binding.coordinatorLayout.isEnabled = false
        binding.coordinatorLayout.isClickable = false
        binding.itemFloatActionButton.isEnabled = false
        binding.itemFloatActionButton1.isEnabled = false
        binding.itemFloatActionButton3.isEnabled = false
    }

    private fun bottomNavEtkin() {
        binding.bottomNavMenu.isEnabled = true
        binding.bottomNavMenu.isClickable = true
        binding.coordinatorLayout.isEnabled = true
        binding.coordinatorLayout.isClickable = true
        binding.itemFloatActionButton.isEnabled = true
        binding.itemFloatActionButton1.isEnabled = true
        binding.itemFloatActionButton3.isEnabled = true
    }

    private fun bottomNavGizle() {
        binding.coordinatorLayout.visibility = View.GONE
        binding.itemFloatActionButton.visibility = View.GONE
        binding.itemFloatActionButton1.visibility = View.GONE
        binding.itemFloatActionButton3.visibility = View.GONE
    }

    private fun bottomNavGoster() {
        binding.coordinatorLayout.visibility = View.VISIBLE
        binding.itemFloatActionButton.visibility = View.VISIBLE
        binding.itemFloatActionButton1.visibility = View.VISIBLE
        binding.itemFloatActionButton3.visibility = View.VISIBLE
    }


    override fun onBackPressed() {
        showExitDialog()
    }

}