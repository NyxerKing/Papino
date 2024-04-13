package ru.papino.restaurant.presentation.authorization.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.R
import ru.papino.restaurant.ScreenManager
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.databinding.FragmentAuthorizationBinding
import ru.papino.restaurant.domain.response.UserResponse
import ru.papino.restaurant.domain.usecases.GetUserByPasswordUseCase
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.authorization.viewmodels.AuthorizationViewModel
import ru.papino.uikit.extensions.showAlert

internal class AuthorizationFragment : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        AuthorizationViewModel(
            getUserByPasswordUseCase = GetUserByPasswordUseCase(UserRepositoryImpl.getInstance()),
            userMapper = UserMapper()
        )
    }

    private lateinit var binding: FragmentAuthorizationBinding
    private val screenManager = ScreenManager.getManager()

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAuthorizationBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initClicks()
        initObserver()
    }

    private fun initViews() {
        binding.editTextPhone.setMask(
            mask = resources.getString(R.string.input_phone_mask),
            pattern = resources.getString(R.string.input_phone_pattern_check)
        )
    }

    private fun initObserver() {
        viewModel.onSuccess.bind { switchFragment(screenManager.profileFragment) }
        viewModel.onFailure.bind(::showError)
    }

    private fun onClickAuthorization() {
        with(binding) {
            viewModel.loginUser(
                login = editTextPhone.text.toString(),
                password = editTextPassword.text.toString()
            )
        }
    }

    private fun showError(response: UserResponse.Error) {
        context?.showAlert(
            title = "Ошибка",
            message = response.message,
            onClick = {})
    }

    private fun initClicks() {
        binding.buttonRegistration.setOnClickListener {
            switchFragment(screenManager.registrationManager)
        }

        binding.buttonLogin.setOnClickListener { onClickAuthorization() }
    }

    companion object {

        fun getInstance() = AuthorizationFragment()
    }
}