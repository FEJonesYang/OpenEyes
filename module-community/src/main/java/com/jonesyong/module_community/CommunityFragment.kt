package com.jonesyong.module_community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jonesyong.module_community.databinding.FragmentCommunityBinding

/**
 * @Author JonesYang
 * @Date 2022-02-07
 * @Description
 */
class CommunityFragment : Fragment() {

    private lateinit var communityViewModel: CommunityViewModel
    private var _binding: FragmentCommunityBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        communityViewModel = ViewModelProvider(this).get(CommunityViewModel::class.java)
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCommunity
        communityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}