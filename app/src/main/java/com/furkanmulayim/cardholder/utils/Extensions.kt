package com.furkanmulayim.cardholder.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import com.furkanmulayim.cardholder.ui.add_future.AddCardFragmentViewModel
import com.furkanmulayim.cardholder.ui.detail_future.DetailFragmentViewModel
import com.furkanmulayim.cardholder.ui.tc_future.DriverLicenceFragmentViewModel
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


fun Context.showDeleteConfirmationDialog(cardName: String, onDeleteConfirmed: () -> Unit) {
    val alertDialogBuilder = AlertDialog.Builder(this)
    alertDialogBuilder.setTitle("Silme Onayı")
    alertDialogBuilder.setMessage("'$cardName' numaralı kimlik kartını silmek istediğinize emin misiniz?")
    alertDialogBuilder.setPositiveButton("Sil") { _, _ ->
        onDeleteConfirmed()
    }
    alertDialogBuilder.setNegativeButton("İptal") { dialog, _ ->
        dialog.dismiss()
    }
    alertDialogBuilder.create().show()
}


fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/** For Add Fragment */
//edittext dinleme
fun EditText.addTextWatcher(listener: (String) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(s?.toString() ?: "")
        }

        override fun afterTextChanged(s: Editable?) {
        }
    }
    addTextChangedListener(textWatcher)
}

fun EditText.addTextWatcherAy(listener: (String) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(s?.toString() ?: "")
        }
        private var isProcessing = false
        override fun afterTextChanged(s: Editable?) {
            if (!isProcessing) {
                isProcessing = true
                if (s != null) {
                    if (s.isNotEmpty() && s.length >= 2) { // Boş dize kontrolü
                        var sayi = s.toString().toIntOrNull()
                        if (sayi != null && sayi < 13) {
                            if (sayi.toString() == "00") {
                                s.clear()
                            }
                        } else {
                            s.clear()
                        }
                    } else if (s.isNotEmpty() && s.length == 1) {
                        var sayi = s.toString().toIntOrNull()
                        s.clear()
                        s.append(sayi.toString())
                    }
                }
                isProcessing = false
            }
        }
    }
    addTextChangedListener(textWatcher)
}

fun EditText.addTextWatcherYil(listener: (String) -> Unit) {
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.invoke(s?.toString() ?: "")
        }
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (s.isNotEmpty() && s.length >= 2) {
                    var sayi = s.toString().toIntOrNull()
                    if (sayi != null) {
                        if (sayi > 40) {
                            s.clear()
                        } else if (sayi < 23) {
                            s.clear()
                        }
                    }
                }
            }
        }
    }
    addTextChangedListener(textWatcher)
}


//Gelen Listedeki Edittextleri textwatcherliyoruz
fun listenToEditTextsExt(
    viewModel: AddCardFragmentViewModel, list: List<EditText>
) {
    list[0].addTextWatcher { viewModel.cardNameOnTextChanged(it) }
    list[1].addTextWatcher { viewModel.cardNumberOnTextChanged(it) }
    list[2].addTextWatcher { viewModel.cardNameSurnameOnTextChanged(it) }
    list[3].addTextWatcherAy { viewModel.cardMonthOnTextChanged(it) }
    list[4].addTextWatcherYil { viewModel.cardYearOnTextChanged(it) }
    list[5].addTextWatcher { viewModel.cardCvvOnTextChanged(it) }
}

fun listenToEditTextsExt1(
    viewModel: DetailFragmentViewModel, list: List<EditText>
) {
    list[0].addTextWatcher { viewModel.cardNameOnTextChanged(it) }
    list[1].addTextWatcher { viewModel.cardNumberOnTextChanged(it) }
    list[2].addTextWatcher { viewModel.cardNameSurnameOnTextChanged(it) }
    list[3].addTextWatcherAy { viewModel.cardMonthOnTextChanged(it) }
    list[4].addTextWatcherYil { viewModel.cardYearOnTextChanged(it) }
    list[5].addTextWatcher { viewModel.cardCvvOnTextChanged(it) }
}


//Gelen Listedeki Edittextleri textwatcherliyoruz
fun listenToEditTextsExt2(
    viewModel: DriverLicenceFragmentViewModel, list: List<EditText>
) {
    list[0].addTextWatcher { viewModel.cardGiveDateTextChanged(it) }
    list[1].addTextWatcher { viewModel.cardDateEndTextChanged(it) }
    list[2].addTextWatcher { viewModel.cardLicenceNoTextChanged(it) }
    list[3].addTextWatcher { viewModel.cardYearOnTextChanged(it) }
}

//Kart Rotasyonu Kart Ekle
fun ImageView.setupCardRotation(
    viewModel: AddCardFragmentViewModel,
    viewLifecycleOwner: LifecycleOwner,
    cl: ConstraintLayout,
    tw: TextView,
    tw2: TextView
) {
    viewModel.rotateLayoutLiveData.observe(viewLifecycleOwner) { rotate ->
        if (rotate) {
            animate().rotationY(180f).start()
            cl.visibility = GONE
            tw.visibility = VISIBLE
            tw2.visibility = VISIBLE
        } else {
            animate().rotationY(0f).start()
            cl.visibility = VISIBLE
            tw.visibility = GONE
            tw2.visibility = GONE
        }
    }

    setOnClickListener {
        viewModel.rotateLayout()
    }
}


//Kart Numarasını 4'lü parsellere ayırır
fun String.formatWithSpaces(): String {
    val formattedText = StringBuilder()
    for (i in indices) {
        formattedText.append(this[i])
        if ((i + 1) % 4 == 0 && i != length - 1) {
            formattedText.append("  ")
        }
    }
    return formattedText.toString()
}


fun formatForListing(s: String): String {
    val first = s.substring(0, 4)
    val end = s.substring(s.length - 4)
    return "$first    ⋆ ⋆ ⋆ ⋆   ⋆ ⋆ ⋆ ⋆    $end"
}

//Klavye kapatmak için ext
fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}


@SuppressLint("GetInstance")
fun aes64Encrypted(text: String): String {
    val keyBytes = byteArrayOf(
        0x01,
        0x02,
        0x03,
        0x04,
        0x05,
        0x06,
        0x07,
        0x08,
        0x09,
        0x10,
        0x11,
        0x12,
        0x13,
        0x14,
        0x15,
        0x16
    )

    val key = SecretKeySpec(keyBytes, "AES")
    val plainBytes = text.toByteArray()
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key)
    val encryptedBytes = cipher.doFinal(plainBytes)
    val encryptedText = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)

    return encryptedText
}

fun aes64Decrypted(text: String): String {

    val keyBytes = byteArrayOf(
        0x01,
        0x02,
        0x03,
        0x04,
        0x05,
        0x06,
        0x07,
        0x08,
        0x09,
        0x10,
        0x11,
        0x12,
        0x13,
        0x14,
        0x15,
        0x16
    )

    val key = SecretKeySpec(keyBytes, "AES")
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, key)
    val encryptedBytes = Base64.decode(text, Base64.DEFAULT)
    val decryptedBytes = cipher.doFinal(encryptedBytes)
    val decryptedText = decryptedBytes.toString(Charsets.UTF_8)
    return decryptedText
}

fun Activity.showExitDialog() {
    val builder = androidx.appcompat.app.AlertDialog.Builder(this)
    builder.setTitle("Çıkış")
    builder.setMessage("Uygulamadan çıkmak istediğinizden emin misiniz?")
    builder.setPositiveButton("Evet") { _, _ ->
        finish() // Uygulamayı kapat
    }
    builder.setNegativeButton("Hayır", null)
    val dialog = builder.create()
    dialog.show()
}


