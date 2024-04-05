package ru.papino.restaurant.presentation.authorization.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.ScreenManager
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.databinding.FragmentAuthorizationBinding
import ru.papino.restaurant.domain.usecases.GetUserByPasswordUseCase
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.authorization.viewmodels.AuthorizationViewModel
import ru.papino.uikit.extensions.showAlert

internal class AuthorizationFragment : Fragment() {

    private val viewModel by lazy {
        AuthorizationViewModel(
            getUserByPasswordUseCase = GetUserByPasswordUseCase(UserRepositoryImpl.getInstance()),
            userMapper = UserMapper()
        )
    }

    private lateinit var binding: FragmentAuthorizationBinding
    private val screenManager = ScreenManager.getManager()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAuthorizationBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registration()

        binding.buttonLogin.setOnClickListener { onClickAuthorization() }
    }

    private fun onClickAuthorization() {
        with(binding) {
            viewModel.loginUser(
                login = editTextPhone.text.toString(),
                password = editTextPassword.text.toString(),
                onSuccess = {
                    switchFragment(screenManager.profileFragment)
                },
                onFailure = {
                    lifecycleScope.launch(Dispatchers.Main) {
                        context?.showAlert(
                            title = "Ошибка",
                            message = "Произошла ошибка",
                            onClick = {})
                    }
                }
            )
        }
    }

    private fun registration() {
        binding.buttonRegistration.setOnClickListener {
            switchFragment(screenManager.registrationManager)
        }
    }
}