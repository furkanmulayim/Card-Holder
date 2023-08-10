package com.furkanmulayim.cardholder.ui.lock_future

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.cardholder.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LockFragment : Fragment() {

    private lateinit var numberAdap: NumberAdapter
    private var password: String = ""
    private var passwordShared: String = ""
    private var isPassRegistered = false
    private lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sp = requireActivity().getSharedPreferences("Password", Context.MODE_PRIVATE)
        isPassRegistered = getSharedPref()
        passwordEnter()
    }


    private fun passwordEnter() {
        loginQuery()
        val recyclerView = view?.findViewById(R.id.numberRcyc) as RecyclerView
        val layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        numberAdap =
            NumberAdapter(context?.getString(R.string.btn_items).orEmpty()) { basilanSayi ->
                if (isPassRegistered) {
                    //şifreyi kontrol et
                    isPasswordCorrect(basilanSayi)
                } else {
                    //yeni şifre oluştur
                    createPassword(basilanSayi)
                }
                passEnterToCircle()
            }
        recyclerView.adapter = numberAdap
    }

    private fun isPasswordCorrect(basilanSayi: String) {
        password += basilanSayi
        if (password.length == 4) {
            if (passwordShared == password) {
                //navigasyon geçiş
                password = ""
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_lockFragment_to_cardListFragment)
            } else {
                // Şifre Yanlış
                exceptionAnim()
                password = ""
            }
        }
    }

    private fun exceptionAnim() {
        val buttons = view?.findViewById<LinearLayout>(R.id.buttons)
        val anim = AnimationUtils.loadAnimation(
            requireContext(), R.anim.false_alarm
        )
        buttons?.startAnimation(anim)
    }

    private fun createPassword(basilanSayi: String) {
        password += basilanSayi
        if (password.length == 4) {
            val editor = sp.edit()
            editor.putString("Pass Name", password)
            editor.apply()
            password = ""
            Navigation.findNavController(requireView())
                .navigate(R.id.action_lockFragment_to_cardListFragment)
        }
    }

    private fun getSharedPref(): Boolean {
        passwordShared = sp.getString("Pass Name", "").toString()
        return passwordShared.isNotEmpty()
    }

    private fun setText(comingText: String) {
        val text = view?.findViewById<TextView>(R.id.text)
        text?.text = comingText
    }

    private fun loginQuery() {
        if (isPassRegistered) {
            setText("Şifrenizi Girin")
        } else {
            setText("Şifre Oluşturun")
        }
    }

    private fun passEnterToCircle() {

        val b1 = view?.findViewById<ImageView>(R.id.b1)
        val b2 = view?.findViewById<ImageView>(R.id.b2)
        val b3 = view?.findViewById<ImageView>(R.id.b3)
        val b4 = view?.findViewById<ImageView>(R.id.b4)

        when (password.length) {
            0 -> {
                b1?.setBackgroundResource(R.drawable.lock_item)
                b2?.setBackgroundResource(R.drawable.lock_item)
                b3?.setBackgroundResource(R.drawable.lock_item)
                b4?.setBackgroundResource(R.drawable.lock_item)
            }

            1 -> {
                b1?.setBackgroundResource(R.drawable.lock_item_full)
            }

            2 -> {
                b2?.setBackgroundResource(R.drawable.lock_item_full)
            }

            3 -> {
                b3?.setBackgroundResource(R.drawable.lock_item_full)
            }

            4 -> {
                b4?.setBackgroundResource(R.drawable.lock_item_full)
            }

        }
    }
}