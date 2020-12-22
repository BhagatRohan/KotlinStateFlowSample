package com.rohan.kotlinstateflowsample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.rohan.kotlinstateflowsample.databinding.ActivityMainBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etUsername.text.toString(), binding.etPassword.text.toString())
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loginUiState.collect {
                when (it) {
                    is MainViewModel.LoginUIState.Success -> {
                        binding.root.let { it1 ->
                            Snackbar.make(it1, "Successfully Logged in", Snackbar.LENGTH_LONG)
                                .show()
                        }
                        binding.progressBar.isVisible = false
                    }

                    is MainViewModel.LoginUIState.Error -> {
                        binding.root.let { it1 ->
                            Snackbar.make(it1, it.message, Snackbar.LENGTH_LONG)
                                .show()
                        }
                        binding.progressBar.isVisible = false
                    }

                    is MainViewModel.LoginUIState.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is MainViewModel.LoginUIState.Empty -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}