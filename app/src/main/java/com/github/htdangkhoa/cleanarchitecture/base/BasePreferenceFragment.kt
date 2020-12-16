package com.github.htdangkhoa.cleanarchitecture.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.github.htdangkhoa.cleanarchitecture.data.model.AuthModel
import com.github.htdangkhoa.cleanarchitecture.data.model.ResponseExceptionModel
import com.github.htdangkhoa.cleanarchitecture.ui.login.LoginActivity
import com.pawegio.kandroid.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.HttpException
import kotlin.reflect.KClass


abstract class BasePreferenceFragment<VM : ViewModel>(val clazz: KClass<VM>) : PreferenceFragmentCompat() {
    abstract val preferenceResID: Int

    protected val viewModel: VM by viewModel(clazz)

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(preferenceResID, rootKey)
    }

    protected open fun render(view: View, savedInstanceState: Bundle?) = Unit

    protected fun handleError(throwable: Throwable? = null, block: ((Throwable?) -> Unit)? = null) {
        return block?.invoke(throwable) ?: handleHttpError(throwable)
    }

    protected fun handleHttpError(throwable: Throwable?) {
        when (throwable) {
            is HttpException -> {
                logout(throwable.code())
            }
            is ResponseExceptionModel -> {
                throwable.responseModel?.code?.let { logout(it) }
            }
        }
    }

    protected fun logout(code: Int) {
        if (code == 401) {
            AuthModel.clear()

            context?.let {
                if (it is Activity && it::class.simpleName != LoginActivity::class.simpleName) {
                    it.startActivity<LoginActivity>()

                    it.finishAfterTransition()
                }
            }
        }
    }

    protected fun showDialog(title: String? = "Info", message: String? = null): MaterialDialog {
        return MaterialDialog(requireContext()).show {
            title(text = title)

            message(text = message)

            positiveButton(text = "OK")
        }
    }
}
