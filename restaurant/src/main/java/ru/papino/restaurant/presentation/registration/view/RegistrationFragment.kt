package ru.papino.restaurant.presentation.registration.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.papino.restaurant.data.mappers.UserMapper
import ru.papino.restaurant.data.repository.UserRepositoryImpl
import ru.papino.restaurant.databinding.FragmentRegistrationBinding
import ru.papino.restaurant.domain.repository.models.UserModel
import ru.papino.restaurant.domain.usecases.CreateUserUseCase
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.profile.views.ProfileFragment
import ru.papino.restaurant.presentation.registration.viewmodels.RegistrationViewModel
import ru.papino.uikit.extensions.showAlert

internal class RegistrationFragment : Fragment() {

    private val viewModel by lazy {
        RegistrationViewModel(
            createUserUseCase = CreateUserUseCase(UserRepositoryImpl.getInstance()),
            userMapper = UserMapper()
        )
    }

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRegistrationBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRegistration.setOnClickListener { onClickRegistration() }
    }

    private fun onClickRegistration() {
        with(binding) {
            viewModel.createUser(
                user = UserModel(
                    firstName = editFirstName.text.toString(),
                    secondName = editSecondName.text.toString(),
                    phone = editPhone.text.toString(),
                    address = editAddress.text.toString(),
                    password = editPassword.text.toString()
                ),
                onSuccess = {
                    switchFragment(ProfileFragment())
                },
                onFailure = {
                    context?.showAlert(title = "Ошибка", message = "Произошла ошибка", onClick = {})
                }
            )
        }
    }
}