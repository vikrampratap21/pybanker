package com.example.pybanker


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.frg_add_account.*


/**
 * A simple [Fragment] subclass.
 *
 */
class FrgAddAccount : Fragment() {

    private var dbhelper: DBHelper? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dbhelper = DBHelper(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frg_add_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AddAccSubmit.setOnClickListener{

            val name = AddAccETAccountName.text.toString()
            val balance = AddAccETBalance.text.toString().toFloat()
            val excludetotal = if (AddAccExclude.isChecked) "yes" else "no"
            val type = if (AddAccTypeAsset.isChecked) "A" else "L"

            try {
                dbhelper?.addAccount(name, balance, excludetotal, type)
                Toast.makeText(activity,"Account added successfully", Toast.LENGTH_SHORT).show()
                clearForm()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearForm() {
        AddAccETAccountName.setText("")
        AddAccETBalance.setText("")
        AddAccExclude.isChecked = false
        AddAccType.check(R.id.AddAccTypeAsset)
    }

}
