package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_nagivation.*
import org.jetbrains.anko.bundleOf
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseFragment
import ru.teamdroid.recipecraft.ui.base.Screens

class NavigationFragment : BaseFragment() {

    override val contentResId = R.layout.fragment_nagivation

    private var currentScreen = Screens.RECIPES

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.getBoolean(IS_FIRST_STARTUP)) showStartup()
        }
    }

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

        val transaction = childFragmentManager.beginTransaction()
        val fragment = childFragmentManager.findFragmentByTag(currentScreen)
                ?: createFragment(currentScreen)

        transaction.replace(R.id.navigationContainer, fragment, currentScreen)
                .addToBackStack(currentScreen).commit()
    }

    private fun createFragment(screenKey: String): Fragment {
        return when (screenKey) {
            Screens.CRAFT -> CraftFragment.newInstance()
            Screens.RECIPES -> RecipesFragment.newInstance()
            Screens.FAVORITES -> FavoritesFragment.newInstance()
            else -> CraftFragment.newInstance()
        }
    }

    companion object {
        private const val IS_FIRST_STARTUP = "isFirstStartup"
        const val TAG = "NavigationFragment"
        fun newInstance(isFirstStartup: Boolean) = NavigationFragment().apply {
            arguments = bundleOf(IS_FIRST_STARTUP to isFirstStartup)
        }
    }
}