package com.sagar.tendable.ui.register

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.sagar.tendable.R
import com.sagar.tendable.databinding.FragmentRegisterBinding
import com.sagar.tendable.model.LoginRegister
import com.sagar.tendable.util.ApiResponse
import com.sagar.tendable.util.Constants
import com.sagar.tendable.util.showToast
import kotlinx.coroutines.flow.collectLatest


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel : RegisterFragmentViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        registerObservers()

        binding.registerPb.visibility = View.GONE
        binding.registerRegisterBtn.setOnClickListener {

            val mEmail = binding.registerEmailET.text.toString()
            if (mEmail == ""){
                binding.registerEmailET.error = getString(R.string.please_enter_your_email)
                binding.registerEmailET.isFocusable = true
                return@setOnClickListener
            }

            if (Constants.isValidEmail(mEmail)) {
                binding.registerEmailET.error = null
            } else {
                binding.registerEmailET.error = getString(R.string.invalid_email_address)
                requireActivity().showToast(getString(R.string.invalid_email_address))
                return@setOnClickListener
            }

            val mNewPassword = binding.registerNewPasswordET.text.toString()
            if (mNewPassword == ""){
                binding.registerNewPasswordET.error = getString(R.string.please_enter_new_password)
                binding.registerNewPasswordET.isFocusable = true
                return@setOnClickListener
            }

            val mConfirmPassword = binding.registerConfirmPasswordET.text.toString()
            if (mConfirmPassword == ""){
                binding.registerConfirmPasswordET.error = getString(R.string.please_enter_confirm_password)
                binding.registerConfirmPasswordET.isFocusable = true
                return@setOnClickListener
            }

            if (mNewPassword != mConfirmPassword){
                requireActivity().showToast(getString(R.string.password_does_not_match))
                return@setOnClickListener
            }

            val loginRegister = LoginRegister().apply {
                email = mEmail
                password = mConfirmPassword
            }

            val strRegisterReq = Gson().toJson(loginRegister)
            Log.e("Register","RegisterReq: $strRegisterReq")

            viewModel.saveRegisterData(strRegisterReq)
        }

    }

    private fun registerObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.stateFlowRegisterRepo.collectLatest { response ->
                run {
                    when(response){

                        is ApiResponse.Success -> {
                            binding.registerPb.visibility = View.GONE
                            requireActivity().showToast(getString(R.string.register_successfully))
                        }

                        is ApiResponse.Error -> {
                            binding.registerPb.visibility = View.GONE
                            MaterialAlertDialogBuilder(requireActivity())
                                .setTitle(getString(R.string.error))
                                .setCancelable(false)
                                .setMessage(response.exception.message.toString())
                                .setNegativeButton(getString(R.string.close)) { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }

                        is ApiResponse.Loading -> {
                            binding.registerPb.visibility = View.VISIBLE
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}