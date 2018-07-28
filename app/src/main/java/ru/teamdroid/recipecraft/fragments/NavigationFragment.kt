package ru.teamdroid.recipecraft.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.fragment_nagivation.*
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.base.*
import ru.teamdroid.recipecraft.views.NavigationMvpView

class NavigationFragment : BaseMoxyFragment(), NavigationMvpView {

    override val contentResId = R.layout.fragment_nagivation

    private var currentScreen = Screens.CRAFT

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
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
            getTabAt(Screens.tabs.indexOf(currentScreen))?.select()
        }
    }

    private fun setTabColor(position: Int, @ColorRes colorRes: Int) {
        tabLayout.getTabAt(position)?.let { tab -> tab.icon?.setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN) }
    }

    fun replaceScreen(screenKey: String) {
        currentScreen = screenKey

        val transaction = childFragmentManager?.beginTransaction()
        var fragment = childFragmentManager?.findFragmentByTag(currentScreen)

        if (fragment == null) {
            fragment = createFragment(currentScreen)
        }

        transaction?.replace(R.id.navigationContainer, fragment, currentScreen)
                ?.addToBackStack(currentScreen)?.commit()
    }

    private fun createFragment(screenKey: String): Fragment? {
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