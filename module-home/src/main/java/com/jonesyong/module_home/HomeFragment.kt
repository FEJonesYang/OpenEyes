package com.jonesyong.module_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.jonesyong.module_home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeTableLayout: TabLayout
    private lateinit var homeViewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        initEvent()
        return binding.root
    }

    private fun initEvent() {

    }

    private fun initView() {
        // 绑定 TabLayout 和 ViewPager
        this.homeTableLayout = binding.homeTable
        this.homeViewPager = binding.homeViewPager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}