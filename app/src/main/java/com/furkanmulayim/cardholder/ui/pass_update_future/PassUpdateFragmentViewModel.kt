package com.furkanmulayim.cardholder.ui.pass_update_future

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.utils.hideKeyboard
import com.furkanmulayim.cardholder.utils.showMessage


class PassUpdateFragmentViewModel() : ViewModel() {

    private lateinit var sp: SharedPreferences

    var cardName = MutableLiveData<String>()
    var cardNumber = MutableLiveData<String>()


    fun passwordUpdateClick(
        button: Button,
        fm: FragmentManager,
        activity: Activity,
        context: Context,
        oldPass: EditText,
        newPass1: EditText,
        newPass2: EditText
    ) {
        isOldPasswordBackground(oldPass, activity)
        button.setOnClickListener {
            val newPassword = isSamePassword(newPass1, newPass2)
            val oldPassword = isOldTrue(oldPass, activity)
            if (newPassword && oldPassword) {
                if (newPass1.text.toString() == newPass2.text.toString() && newPass1.text.toString() != oldPass.text.toString()) {
                    hideKeyboard(activity)
                    message(context, R.string.sifre_guncellendi)
                    updatedPostPass(newPass1.text.toString())
                    fm.popBackStack()
                } else {
                    message(context, R.string.sifreler_ayni)
                }
            } else {
                message(context, R.string.sifre_guncelleme_hatasi)
            }
        }

    }

    private fun isSamePassword(newPass1: EditText, newPass2: EditText): Boolean {
        var bool = false
        if (newPass1.text.toString().length == 4) {
            if (newPass1.text.toString() == newPass2.text.toString()) {
                bool = true
            }
        }
        return bool
    }

    private fun isOldTrue(oldPass: EditText, activity: Activity): Boolean {
        return oldPass.text.toString() == getPassword(activity)
    }


    private fun isOldPasswordBackground(oldPass: EditText, activity: Activity) {
        oldPass.doAfterTextChanged {
            getPassword(activity)
            if (getPassword(activity) == it.toString()) {
                oldPass.setBackgroundResource(R.drawable.sign_text_border_true)
            } else {
                oldPass.setBackgroundResource(R.drawable.sign_text_border_false)
            }
        }
    }

    fun popBackStack(backButton: ImageView, fm: FragmentManager) {
        backButton.setOnClickListener {
            fm.popBackStack()
        }
    }

    private fun getPassword(activity: Activity): String {
        sp = activity.getSharedPreferences("Password", Context.MODE_PRIVATE)
        return sp.getString("Pass Name", "").toString()
    }

    private fun updatedPostPass(password: String) {
        val editor = sp.edit()
        editor.putString("Pass Name", password)
        editor.apply()
    }

    private fun hideKeyboard(activity: Activity) {
        activity.hideKeyboard()
    }

    private fun message(context: Context, resId: Int) {
        context.apply {
            this.getString(resId).let { message ->
                this.showMessage(message)
            }
        }
    }
}