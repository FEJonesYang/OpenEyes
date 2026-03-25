package com.jonesyong.module_user.login

import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.didi.drouter.api.DRouter
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.library_common.ROUTE_USER_REGISTER
import com.jonesyong.module_user.R
import com.jonesyong.module_user.databinding.FragmentLoginBinding
import com.jonesyong.module_user.store.UserStore

class LoginFragment : BaseFragment<LoginViewModel>() {

    private lateinit var binding: FragmentLoginBinding

    override fun getInflateId() = R.layout.fragment_login

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(LoginViewModel::class.java)
    }

    override fun initView(root: View) {
        binding = FragmentLoginBinding.bind(root)

        val updateButton = { _: CharSequence?, _: Int, _: Int, _: Int ->
            binding.btnLogin.isEnabled =
                binding.etUsername.text?.isNotEmpty() == true && binding.etPassword.text?.isNotEmpty() == true
        }
        binding.etUsername.doOnTextChanged(updateButton)
        binding.etPassword.doOnTextChanged(updateButton)

        binding.btnLogin.setOnClickListener {
            vm.login(
                binding.etUsername.text?.toString().orEmpty(),
                binding.etPassword.text?.toString().orEmpty()
            )
        }

        binding.tvRegister.setOnClickListener {
            DRouter.build(ROUTE_USER_REGISTER).start(requireContext())
        }
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.loginSuccess.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }
        vm.loginError.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (UserStore.getUsername() != null) {
            requireActivity().finish()
        }
    }
}
