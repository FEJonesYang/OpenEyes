package com.jonesyong.module_user.login

import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.jonesyong.library_base.BaseFragment
import com.jonesyong.module_user.R
import com.jonesyong.module_user.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<RegisterViewModel>() {

    private lateinit var binding: FragmentRegisterBinding

    override fun getInflateId() = R.layout.fragment_register

    override fun initViewModel() {
        vm = getFragmentScopeViewModel(RegisterViewModel::class.java)
    }

    override fun initView(root: View) {
        binding = FragmentRegisterBinding.bind(root)

        val updateButton = { _: CharSequence?, _: Int, _: Int, _: Int ->
            binding.btnRegister.isEnabled =
                binding.etUsername.text?.isNotEmpty() == true &&
                binding.etPassword.text?.isNotEmpty() == true &&
                binding.etRepassword.text?.isNotEmpty() == true
        }
        binding.etUsername.doOnTextChanged(updateButton)
        binding.etPassword.doOnTextChanged(updateButton)
        binding.etRepassword.doOnTextChanged(updateButton)

        binding.btnRegister.setOnClickListener {
            val password = binding.etPassword.text?.toString().orEmpty()
            val repassword = binding.etRepassword.text?.toString().orEmpty()
            if (password != repassword) {
                Toast.makeText(context, "两次密码不一致", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            vm.register(
                binding.etUsername.text?.toString().orEmpty(),
                password,
                repassword
            )
        }
    }

    override fun onSubscribeUi(view: View) {
        super.onSubscribeUi(view)
        vm.registerSuccess.observe(viewLifecycleOwner) {
            requireActivity().finish()
        }
        vm.registerError.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}
