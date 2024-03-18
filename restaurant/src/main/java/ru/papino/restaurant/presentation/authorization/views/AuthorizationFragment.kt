package ru.papino.restaurant.presentation.authorization.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.papino.restaurant.databinding.FragmentAuthorizationBinding
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.authorization.viewmodels.AuthorizationViewModel
import ru.papino.restaurant.presentation.registration.view.RegistrationFragment

internal class AuthorizationFragment : Fragment() {

    private val viewModel by lazy {
        AuthorizationViewModel()
    }

    private lateinit var binding: FragmentAuthorizationBinding

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
    }

    private fun registration() {
        binding.buttonRegistration.setOnClickListener {
            switchFragment(RegistrationFragment())
        }
    }
}