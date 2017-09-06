package ru.aol_panchenko.tables.presentation.login.enter_code

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.enter_code_fragment.*
import kotlinx.android.synthetic.main.enter_number_fragment.*
import org.jetbrains.anko.onClick
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 30.08.17.
 */
class EnterCodeFragment : Fragment(), EnterCodeMVPView {

    interface OnConfirmListener{
        fun onConfirm(code: String)
        fun onResend()
    }

    private lateinit var _confirmListener: OnConfirmListener
    private var _presenter: EnterCodePresenter? = null

    companion object {
        fun newInstance() = EnterCodeFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        _confirmListener = context as OnConfirmListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.enter_code_fragment, container, false)
        _presenter = EnterCodePresenter(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvConfirm.onClick {
            _presenter!!.onClickConfirm()
            hideKeyboard()
        }
        tvResend.onClick {
            _presenter!!.onClickResend()
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etCode.windowToken, 0)
    }

    override fun confirmCode() {
        _confirmListener.onConfirm(etCode.text.toString())
    }

    override fun resendCode() {
        _confirmListener.onResend()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (_presenter != null) {
            _presenter = null
        }
    }
}