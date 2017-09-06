package ru.aol_panchenko.tables.presentation.login.enter_number

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.enter_number_fragment.*
import org.jetbrains.anko.onClick
import ru.aol_panchenko.tables.R

/**
 * Created by alexey on 30.08.17.
 */
class EnterNumberFragment : Fragment(), EnterNumberMVPView {

    interface OnSendListener{
        fun onSend(phone: String)
    }

    private lateinit var _sendListener: OnSendListener
    private var _presenter: EnterNumberPresenter? = null


    companion object {
        fun newInstance() = EnterNumberFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        _sendListener = context as OnSendListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.enter_number_fragment, container, false)
        _presenter = EnterNumberPresenter(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        tvSend.onClick {
            _presenter!!.onClickSend()
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(etPhone.windowToken, 0)
        }
    }

    override fun sendPhone() {
        _sendListener.onSend(etPhone.text.toString())
    }

    override fun showErrorEmptyPhone() {
        etPhone.requestFocus()
        etPhone.error = getString(R.string.enter_number_empty_error)
    }

    override fun showErrorIncorrectPhone() {
        etPhone.requestFocus()
        etPhone.error = getString(R.string.enter_number_incorrect_error)
    }

    override fun getPhone() = etPhone.text.toString()

    override fun onDestroy() {
        super.onDestroy()
        if (_presenter != null) {
            _presenter = null
        }
    }
}