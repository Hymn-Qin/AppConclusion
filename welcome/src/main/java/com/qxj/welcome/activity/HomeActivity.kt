package com.qxj.welcome.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.qxj.welcome.R
import com.qxj.welcome.utilities.InjectorUtils
import com.qxj.welcome.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*

@Route(path = "/home/activity/HomeActivity", group = "home")
class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.java.simpleName

    private val onNavigationItemSelectedListener
            = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
         true
    }

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewModel.pagerChange(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewModel.pagerChange( 1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewModel.pagerChange( 2)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_find -> {
                viewModel.pagerChange( 3)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private val mOnPageChangeListener
            = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            viewModel.pagerChange(position)
        }

    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        nav_view.setNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewPager.addOnPageChangeListener(mOnPageChangeListener)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        subscribeUi()
    }

    private fun subscribeUi() {
        val factory = InjectorUtils.provideHomeViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        drawer_layout.let {
            val toggle = ActionBarDrawerToggle(
                    this,
                    it,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            it.addDrawerListener(toggle)
            toggle.syncState()
        }
        viewPager.adapter = FragmentPagerAdapter(supportFragmentManager,
                *viewModel.fragments.toTypedArray())

        viewModel.pagerItem.observe(this, Observer { position ->
            if (this.lifecycle.currentState == Lifecycle.State.STARTED) {
                Log.d(TAG, "正在初始化 pagerItem" + this.lifecycle.currentState)
                viewPager.currentItem = position
                navigation.selectedItemId = navigation.menu.getItem(position).itemId
            } else {
                if (viewPager.currentItem != position) {
                    Log.d(TAG, "viewPager切换到页面$position")
                    viewPager.currentItem = position
                } else {
                    Log.d(TAG, "navigation切换到$position")
                    navigation.menu.getItem(position).isChecked = true
                }
            }
            toolbar.title = viewModel.title
            Log.d(TAG, "当前在$position -> ${viewModel.title} 页面")

        })


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    internal class FragmentPagerAdapter(fm: FragmentManager,
                                        private vararg var fragments: Fragment)
        : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

        override fun getCount(): Int = fragments.size

    }
}
