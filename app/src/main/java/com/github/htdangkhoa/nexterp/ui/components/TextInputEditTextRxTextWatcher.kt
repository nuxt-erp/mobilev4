package com.github.htdangkhoa.nexterp.ui.components
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.core.Observable

fun TextInputEditText.addRxTextWatcher(): Observable<String?> {
    return Observable.create {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                it.onNext(s?.toString())
            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
        })
    }
}
