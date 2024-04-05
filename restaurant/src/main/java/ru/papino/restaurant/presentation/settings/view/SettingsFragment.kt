package ru.papino.restaurant.presentation.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.ScreenManager
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.databinding.FragmentSettingsBinding
import ru.papino.restaurant.domain.repository.models.UserResponse
import ru.papino.restaurant.domain.usecases.UpdateUserUseCase
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.settings.viewmodels.SettingsViewModel
import ru.papino.uikit.extensions.getText
import ru.papino.uikit.extensions.setText
import ru.papino.uikit.extensions.showAlert

class SettingsFragment : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        SettingsViewModel(
            updateUserUseCase = UpdateUserUseCase(userRepository = UserRepositoryImpl.getInstance()),
            userMapper = UserMapper()
        )
    }

    private lateinit var binding: FragmentSettingsBinding
    private val screenManager = ScreenManager.getManager()

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        viewModel.loadUser()
    }

    private fun initObserver() {
        viewModel.user.bind(::updateUI)
        viewModel.onSuccess.bind { switchFragment(screenManager.profileFragment) }
        viewModel.onFailure.bind(::showError)
    }

    private fun updateUI(user: User) {
        with(binding) {
            inputFirstName.setText(user.firstName)
            inputSecondName.setText(user.secondName)
            inputAddress.setText(user.address)

            buttonUpdate.isEnabled = true
            buttonUpdate.setOnClickListener {
                val secondName = inputSecondName.getText()
                val firstName = inputFirstName.getText()
                val address = inputAddress.getText()

                viewModel.updateUser(
                    secondName = secondName,
                    firstName = firstName,
                    address = address
                )
            }
        }
    }

    private fun showError(error: UserResponse.Error) {
        context?.showAlert(
            title = "Ошибка",
            message = error.message,
            onClick = {})
    }

    companion object {

        fun getInstance() = SettingsFragment()
    }
}