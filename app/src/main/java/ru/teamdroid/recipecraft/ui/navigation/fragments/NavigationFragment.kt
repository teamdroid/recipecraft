package ru.teamdroid.recipecraft.ui.navigation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_nagivation.*
import org.jetbrains.anko.bundleOf
import ru.teamdroid.recipecraft.R
import ru.teamdroid.recipecraft.ui.base.BaseMoxyFragment
import ru.teamdroid.recipecraft.ui.base.Screens

class NavigationFragment : BaseMoxyFragment() {

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

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab) {
                    setTabIcon(tab.position, true)
                    replaceScreen(Screens.tabs[tab.position])
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    setTabIcon(tab.position, false)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    onTabSelected(tab)
                }
            })
            getTabAt(Screens.tabs.indexOf(currentScreen))?.select()
        }
    }

    private fun setTabIcon(position: Int, isSelected: Boolean) {
        when (Screens.tabs[position]) {
            Screens.CRAFT -> changeIcon(position, if (isSelected) R.drawable.ic_menu_craft_active else R.drawable.ic_menu_craft_inactive)
            Screens.RECIPES -> changeIcon(position, if (isSelected) R.drawable.ic_menu_recipe_active else R.drawable.ic_menu_recipe_inactive)
            Screens.PROFILE -> changeIcon(position, if (isSelected) R.drawable.ic_menu_profile_active else R.drawable.ic_menu_profile_inactive)
        }
    }

    private fun changeIcon(position: Int, icon: Int) {
        tabLayout.getTabAt(position)?.setIcon(icon)
    }

    fun replaceScreen(screenKey: String) {
        childFragmentManager.beginTransaction().apply {
            childFragmentManager.findFragmentByTag(currentScreen)?.let { if (it.isAdded) hide(it) }
            currentScreen = screenKey
            val fragment = childFragmentManager.findFragmentByTag(currentScreen) ?: createFragment(currentScreen)
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(R.id.navigationContainer, fragment, currentScreen).addToBackStack(currentScreen)
            }
        }.commit()
    }

    private fun createFragment(screenKey: String): Fragment {
        return when (screenKey) {
            Screens.CRAFT -> CraftFragment.newInstance()
            Screens.RECIPES -> RecipesFragment.newInstance()
            Screens.PROFILE -> ProfileFragment.newInstance()
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