package ru.papino.restaurant.presentation.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.databinding.FragmentProfileBinding
import ru.papino.restaurant.presentation.profile.viewmodels.ProfileViewModel
import ru.papino.uikit.extensions.setText

internal class ProfileFragment : Fragment() {

    private val viewModel by lazy { ProfileViewModel() }

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentProfileBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()

        viewModel.loadUser()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.user.collect(::updateUI)
        }
    }

    private fun updateUI(user: User) {
        with(binding) {
            inputFirstName.setText(user.firstName)
            inputSecondName.setText(user.secondName)
            inputPhone.setText(user.phone)
            inputAddress.setText(user.address)
        }
    }
}