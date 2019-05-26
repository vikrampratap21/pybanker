package com.example.pybanker.ui.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pybanker.R
import com.example.pybanker.model.DBHelper
import com.example.pybanker.model.DashboardCurrentMonthExpAdapter
import kotlinx.android.synthetic.main.frg_dashboard.*


/**
 * A simple [Fragment] subclass.
 *
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FrgDashboard : Fragment() {

    private var dbhelper: DBHelper? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        dbhelper = DBHelper(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frg_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_dashboard_add_trans_btn.setOnClickListener {
            val fragment = FrgAddTran()
            activity!!.supportFragmentManager
                .beginTransaction().replace(R.id.frame_layout_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        f_dashboard_search.setOnClickListener {
            val fragment = FrgSearch()
            activity!!.supportFragmentManager
                .beginTransaction().replace(R.id.frame_layout_main, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

        if (dbhelper!!.transactionsTableExists()) {

            val currentMonthExpenses = ArrayList<CurrentMonthExpense>()
            val res = dbhelper?.getCurrentMonthExpensesByCategory()

            try {
                while (res!!.moveToNext()) {
                    currentMonthExpenses.add(
                        CurrentMonthExpense(res.getString(0), "£" + res.getString(1))
                    )
                }
                res.close()
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

            val layoutManager = LinearLayoutManager(activity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            f_dashboard_current_exp_recycler_view.layoutManager = layoutManager
            f_dashboard_current_exp_recycler_view.adapter = DashboardCurrentMonthExpAdapter(context, currentMonthExpenses)
        }

        f_dashboard_current_exp_recycler_view.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    f_dashboard_search.hide()
                    f_dashboard_add_trans_btn.hide()
                } else if (dy < 0) {
                    f_dashboard_search.show()
                    f_dashboard_add_trans_btn.show()
                }
            }
        })

    }

    data class CurrentMonthExpense(var category: String, var amount: String)

}
