package ru.aevseev.recipecraft.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_nagivation.*
import ru.aevseev.recipecraft.R
import ru.aevseev.recipecraft.base.*
import ru.aevseev.recipecraft.presenters.NavigationPresenter
import ru.aevseev.recipecraft.views.NavigationMvpView

class NavigationFragment : BaseMoxyFragment(), NavigationMvpView {

    override val contentResId = R.layout.fragment_nagivation

    @InjectPresenter
    lateinit var presenter: NavigationPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(toolbar, false, getString(R.string.app_name))
        setupTabLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                Toast.makeText(context, getString(R.string.menu_refresh), Int.MAX_VALUE).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabLayout() {

        with(tabLayout) {

            Screens.tabs.forEachIndexed { index, _ ->
                setTabColor(index, R.color.colorUnselected)
            }

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab) {
                    setTabColor(tab.position, R.color.colorActive)
                    replaceScreen(Screens.tabs[tab.position])
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    setTabColor(tab.position, R.color.colorUnselected)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    onTabSelected(tab)
                }
            })

            getTabAt(Screens.tabs.indexOf(Screens.CRAFT))?.select()

        }
    }

    private fun setTabColor(position: Int, @ColorRes colorRes: Int) {
        tabLayout.getTabAt(position)?.let { tab -> tab.icon?.setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN) }
    }

    private fun replaceScreen(screenKey: String) {
        val transaction = fragmentManager?.beginTransaction()
        var fragment = fragmentManager?.findFragmentByTag(screenKey)

        if (fragment == null) {
            fragment = createFragment(screenKey, null)
        }

        transaction?.replace(R.id.navigationContainer, fragment, screenKey)
                ?.addToBackStack(screenKey)?.commit()
    }

    private fun createFragment(screenKey: String, data: Any?): Fragment? {
        return when (screenKey) {
            Screens.CRAFT -> CraftFragment.newInstance()
            Screens.RECIPES -> RecipesFragment.newInstance()
            Screens.FAVORITES -> FavoritesFragment.newInstance()
            else -> CraftFragment.newInstance()
        }
    }

    companion object {
        fun newInstance() = NavigationFragment()
    }

}