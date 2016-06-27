package com.savekirk.fizzbuzzgame.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding.view.RxView

import com.savekirk.fizzbuzzgame.ViewHolderListener
import com.savekirk.fizzbuzzgame.R
import kotlinx.android.synthetic.main.fragment_home.*
import rx.subscriptions.CompositeSubscription

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), HomeContract.View {

    private var parentActivity: ViewHolderListener? = null
    lateinit private var homePresenter: HomeContract.Presenter
    lateinit private var subscriptions : CompositeSubscription

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment HomeFragment.
         */
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscriptions = CompositeSubscription(
                RxView.clicks(play).subscribe {
                    startGame()
                }
        )

        homePresenter.getHighScore()

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ViewHolderListener) {
            parentActivity = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement ActivityViewContract")
        }
    }

    override fun onDetach() {
        super.onDetach()
        parentActivity = null
    }


    override fun setPresenter(presenter: HomeContract.Presenter) {
        homePresenter = presenter
    }

    override fun showHighScore(score: Int) {
        high_score.text = score.toString()
    }

    override fun startGame() {
        parentActivity?.newGame()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.unsubscribe()
    }

}
