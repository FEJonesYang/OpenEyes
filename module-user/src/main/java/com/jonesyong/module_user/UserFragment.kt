package com.jonesyong.module_user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.didi.drouter.api.DRouter
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_common.ROUTE_USER_LOGIN
import com.jonesyong.module_user.databinding.FragmentUserBinding
import com.jonesyong.module_user.store.UserStore

class UserFragment : BaseFragment<UserViewModel>() {

    private lateinit var binding: FragmentUserBinding
    private var loginStarted = false

    override fun getInflateId() = R.layout.fragment_user

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginStarted = savedInstanceState?.getBoolean(KEY_LOGIN_STARTED, false) ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_LOGIN_STARTED, loginStarted)
    }

    override fun initView(root: View) {
        binding = FragmentUserBinding.bind(root)
        binding.btnLogout.setOnClickListener {
            UserStore.clear()
            loginStarted = true  // LoginActivity about to be shown; back press → Home
            DRouter.build(ROUTE_USER_LOGIN).start(requireContext())
        }
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.username.observe(viewLifecycleOwner) { username ->
            binding.tvUsername.text = username ?: ""
            binding.tvAvatar.text = username?.firstOrNull()?.uppercaseChar()?.toString() ?: ""
        }
    }

    override fun onResume() {
        super.onResume()
        val username = UserStore.getUsername()
        when {
            username != null -> {
                if (vm.username.value != username) vm.username.value = username
            }
            !loginStarted -> {
                loginStarted = true
                DRouter.build(ROUTE_USER_LOGIN).start(requireContext())
            }
            else -> {
                loginStarted = false
                val navController = findNavController()
                navController.navigate(navController.graph.startDestination)
            }
        }
    }

    companion object {
        private const val KEY_LOGIN_STARTED = "login_started"
    }
}
