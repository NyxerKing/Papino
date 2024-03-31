package ru.papino.restaurant.presentation.basket.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.papino.restaurant.R
import ru.papino.restaurant.core.recycler.decorations.CoreDividerItemDecoration
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.databinding.FragmentBasketBinding
import ru.papino.restaurant.extensions.toPrice
import ru.papino.restaurant.presentation.basket.adapters.BasketAdapter
import ru.papino.restaurant.presentation.basket.models.BasketUIModel
import ru.papino.restaurant.presentation.basket.viewmodels.BasketViewModel
import ru.papino.uikit.extensions.setText

internal class BasketFragment : Fragment() {

    private val viewModel by lazy {
        BasketViewModel(
            basketRepository = RoomDependencies.basketRepository
        )
    }

    private lateinit var binding: FragmentBasketBinding
    private val basketAdapter = BasketAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentBasketBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()

        viewModel.loadBasket()
    }

    private fun initUI() {
        with(binding) {
            basketRecyclerView.adapter = basketAdapter
            basketRecyclerView.addItemDecoration(CoreDividerItemDecoration(this.root.context))

            if (UserDI.isUserInitializer()) {
                inputAddress.setText(UserDI.user.address)
            }
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            UserDI.onInitUser.collect(::updateUser)
        }

        lifecycleScope.launch {
            RoomDependencies.basketRepository.changeBasket.collect(::basketChange)
        }

        lifecycleScope.launch {
            viewModel.basket.collect(::updateUI)
        }
    }

    private fun updateUser(user: User?) {
        binding.run {
            user?.let {
                textViewUserName.text = it.toString()
                textViewBonus.text = resources.getString(R.string.title_bonus, it.bonus.toString())
            } ?: run {
                textViewUserName.text = ""
                textViewBonus.text = ""

            }
        }
    }

    private fun updateUI(basket: List<BasketUIModel>) {
        basketAdapter.set(
            list = basket,
            viewModel::deleteProduct,
            viewModel::minusProduct,
            viewModel::plusProduct
        )

        with(binding) {
            val sum = basket.sumOf { product -> product.price.toPrice() * product.count }.toString()

            titleOrderFoodSum.text = sum
            toPaySum.text = sum

            orderForm.visibility = VISIBLE
        }
    }

    private suspend fun basketChange(id: Int) {
        val count = RoomDependencies.basketRepository.getCountAll()
        binding.titleOrderFood.text =
            resources.getString(ru.papino.uikit.R.string.insert_products, count.toString())
    }
}