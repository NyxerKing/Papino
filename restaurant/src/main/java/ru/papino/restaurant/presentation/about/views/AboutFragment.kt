package ru.papino.restaurant.presentation.about.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.R
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.data.di.RepositoryManager
import ru.papino.restaurant.databinding.FragmentAboutBinding
import ru.papino.restaurant.domain.repository.models.AboutModel
import ru.papino.restaurant.domain.usecases.GetAboutUseCase
import ru.papino.restaurant.presentation.about.viewmodels.AboutViewModel
import ru.papino.uikit.dialogs.AlertDialog

internal class AboutFragment() : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        AboutViewModel(
            getAboutUseCase = GetAboutUseCase(aboutRepository = RepositoryManager().getAboutRepository())
        )
    }

    private lateinit var binding: FragmentAboutBinding

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentAboutBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }

    private fun initObserver() {
        viewModel.about.bind(::updateAbout)
        viewModel.error.bind(::showError)
    }

    private fun updateAbout(aboutModel: AboutModel?) {
        binding.run {
            textViewName.text = aboutModel?.name
            textViewAbout.text = aboutModel?.aboutOrganization
        }
    }

    private fun showError(error: String) {
        binding.run {
            AlertDialog(
                context = root.context,
                title = resources.getString(R.string.error_request_title),
                message = "${resources.getString(R.string.error_request_message)}\n${error}",
                onClick = {}
            ).show()
        }
    }

    companion object {

        fun getInstance() = AboutFragment()
    }
}