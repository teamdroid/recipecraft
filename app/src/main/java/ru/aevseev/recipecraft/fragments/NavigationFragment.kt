package ru.aevseev.recipecraft.fragments

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.aevseev.recipecraft.R
import ru.aevseev.recipecraft.base.BaseMoxyFragment
import ru.aevseev.recipecraft.presenters.NavigationPresenter
import ru.aevseev.recipecraft.views.NavigationMvpView

class NavigationFragment : BaseMoxyFragment(), NavigationMvpView {

    override val contentResId = R.layout.fragment_nagivation

    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = NavigationFragment()
    }

}