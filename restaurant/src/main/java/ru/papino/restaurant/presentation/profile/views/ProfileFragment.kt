package ru.papino.restaurant.presentation.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.papino.restaurant.R
import ru.papino.restaurant.RestaurantActivity
import ru.papino.restaurant.databinding.FragmentProfileBinding
import ru.papino.restaurant.presentation.profile.viewmodels.ProfileViewModel
import ru.papino.restaurant.presentation.registration.view.RegistrationFragment

internal class ProfileFragment : Fragment() {

    private val viewModel by lazy {
        ProfileViewModel()
    }


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

        registration()
    }

    private fun registration() {
        binding.buttonRegistration.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer, RegistrationFragment(),
                RestaurantActivity.ACTIVE_FRAGMENT
            )
            fragmentTransaction.commit()
        }
    }
}