package ru.papino.restaurant.presentation.profile.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.ScreenManager
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.databinding.FragmentProfileBinding
import ru.papino.restaurant.extensions.switchFragment
import ru.papino.restaurant.presentation.profile.viewmodels.ProfileViewModel
import ru.papino.uikit.extensions.setText

internal class ProfileFragment : Fragment(), CoroutineProperty {

    private val screenManager = ScreenManager.getManager()

    private val viewModel by lazy { ProfileViewModel() }

    private lateinit var binding: FragmentProfileBinding

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

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
        viewModel.user.bind(::updateUI)
    }

    private fun updateUI(user: User) {
        with(binding) {
            inputFirstName.setText(user.firstName)
            inputSecondName.setText(user.secondName)
            inputPhone.setText(user.phone)
            inputAddress.setText(user.address)

            imageSettings.visibility = View.VISIBLE
            imageSettings.setOnClickListener { switchFragment(screenManager.settingsFragment) }
        }
    }

    companion object {

        fun getInstance() = ProfileFragment()
    }
}