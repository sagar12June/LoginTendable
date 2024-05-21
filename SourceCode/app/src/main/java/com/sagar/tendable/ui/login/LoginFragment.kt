package com.sagar.tendable.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.sagar.tendable.R
import com.sagar.tendable.databinding.FragmentLoginBinding
import com.sagar.tendable.model.LoginRegister
import com.sagar.tendable.util.ApiResponse
import com.sagar.tendable.util.Constants.Companion.isValidEmail
import com.sagar.tendable.util.showToast
import kotlinx.coroutines.flow.collectLatest

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel : LoginFragmentViewModel by viewModels()
    private lateinit var binding : FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        loginObservers()

        binding.loginRegisterNewAccTv.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        binding.loginPb.visibility = View.GONE
        binding.loginLoginBtn.setOnClickListener {

            val mEmail = binding.loginEmailET.text.toString()
            if (mEmail == ""){
                binding.loginEmailET.error = getString(R.string.please_enter_your_email)
                binding.loginEmailET.isFocusable = true
                return@setOnClickListener
            }

            if (isValidEmail(mEmail)) {
                binding.loginEmailET.error = null
            } else {
                binding.loginEmailET.error = getString(R.string.invalid_email_address)
                requireActivity().showToast(getString(R.string.invalid_email_address))
                return@setOnClickListener
            }

            val mPassword = binding.loginPasswordET.text.toString()
            if (mPassword == "") {
                binding.loginPasswordET.error = getString(R.string.please_enter_password)
                binding.loginPasswordET.isFocusable = true
                return@setOnClickListener
            }

            val loginRegister = LoginRegister().apply {
                email = mEmail
                password = mPassword
            }

            val strLoginReq = Gson().toJson(loginRegister)
            Log.e("Login: ", "LoginReq: $strLoginReq")

            viewModel.saveLoginData(strLoginReq)

        }
    }

    private fun loginObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowLoginResponse.collectLatest { response ->
                run {
                    when (response){
                        is ApiResponse.Success -> {
                            binding.loginPb.visibility = View.GONE
                            requireActivity().showToast(getString(R.string.login_successfully))
                        }

                        is ApiResponse.Error -> {
                            binding.loginPb.visibility = View.GONE
                            MaterialAlertDialogBuilder(requireActivity())
                                .setTitle(getString(R.string.error))
                                .setCancelable(false)
                                .setMessage(response.exception.message.toString())
                                .setNegativeButton(getString(R.string.close)) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }

                        is ApiResponse.Loading ->{
                            binding.loginPb.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}