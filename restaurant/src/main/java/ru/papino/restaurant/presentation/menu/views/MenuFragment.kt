package ru.papino.restaurant.presentation.menu.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.R
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.core.imageLoader.ImageLoaderImpl
import ru.papino.restaurant.core.recycler.decorations.CoreDividerItemDecoration
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.data.di.RepositoryManager
import ru.papino.restaurant.data.repository.ProductTypesRepositoryImpl
import ru.papino.restaurant.databinding.FragmentMenuBinding
import ru.papino.restaurant.domain.usecases.GetMenuUseCase
import ru.papino.restaurant.domain.usecases.GetProductTypesUseCase
import ru.papino.restaurant.presentation.menu.adapters.MenuAdapter
import ru.papino.restaurant.presentation.menu.mappers.MenuMapper
import ru.papino.restaurant.presentation.menu.models.ProductTypeUIModel
import ru.papino.restaurant.presentation.menu.models.ProductUIModel
import ru.papino.restaurant.presentation.menu.viewmodels.MenuViewModel
import ru.papino.uikit.dialogs.AlertDialog

internal class MenuFragment : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        MenuViewModel(
            getProductTypesUseCase = GetProductTypesUseCase(
                repository = ProductTypesRepositoryImpl.getInstance()
            ),
            getMenuUseCase = GetMenuUseCase(repository = RepositoryManager().getMenuInstance()),
            mapper = MenuMapper(),
            basketRepository = RoomDependencies.basketRepository
        )
    }

    private var binding: FragmentMenuBinding? = null
    private val menuAdapter = MenuAdapter(::onClickProduct, imageLoader = ImageLoaderImpl())

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentMenuBinding.inflate(inflater, container, false)
            .apply { binding = this }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initUI() {
        binding?.run {
            menuRecycler.adapter = menuAdapter
            menuRecycler.addItemDecoration(CoreDividerItemDecoration(this.root.context))
        }
    }

    private fun initObserver() {
        UserDI.onInitUser.bind(::updateUser)
        viewModel.loader.bind(::showLoader)
        viewModel.productTypes.bind(::initMenu)
        viewModel.menu.bind(::updateUI)
        viewModel.error.bind(::showError)
    }

    private fun updateUser(user: User?) {
        binding?.run {
            user?.let {
                headerContainer.set(
                    userName = it.toString(),
                    bonus = resources.getString(R.string.title_bonus, it.bonus.toString())
                )
            } ?: run {
                headerContainer.set(
                    userName = "",
                    bonus = ""
                )
            }
        }
    }

    private fun initMenu(productTypes: List<ProductTypeUIModel>?) {
        binding?.run {
            productTypes?.forEachIndexed { index, model ->
                val chip =
                    ru.papino.uikit.components.Chip<ProductTypeUIModel>(chipGroupMenu.context)
                chip.setModel(model)
                chip.text = model.title
                chip.isChecked = model == viewModel.getSelectedProductType()
                if (chip.isChecked) titleMenu.text = chip.text
                chipGroupMenu.addView(chip)
            }

            chipGroupMenu.setOnCheckedStateChangeListener { chipGroup, ints ->
                if (ints.isNotEmpty()) {
                    val index = ints.first()
                    val item =
                        chipGroup.findViewById<ru.papino.uikit.components.Chip<ProductTypeUIModel>>(
                            index
                        )
                    titleMenu.text = item.getModel()?.title
                    menuRecycler.scrollToPosition(0)
                    item.getModel()?.let { viewModel.filterProducts(it) }
                }
            }
        }
    }

    private fun updateUI(products: List<ProductUIModel>?) {
        menuAdapter.set(products)
    }

    private fun showError(ex: Throwable) {
        binding?.run {
            AlertDialog(
                context = root.context,
                title = resources.getString(R.string.error_request_title),
                message = "${resources.getString(R.string.error_request_message)}\n${ex.message}",
                onClick = {}
            ).show()
        }
    }

    private fun onClickProduct(product: ProductUIModel) {
        viewModel.addBasket(product = product)
    }

    private fun showLoader(isShow: Boolean) {
        binding?.run {
            if (isShow) {
                frameProgressIndicator.visibility = View.VISIBLE
                containerMenu.visibility = View.GONE
            } else {
                frameProgressIndicator.visibility = View.GONE
                containerMenu.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        fun getInstance() = MenuFragment()
    }
}