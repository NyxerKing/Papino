package ru.papino.restaurant.presentation.orders.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.core.recycler.decorations.CoreDividerItemDecoration
import ru.papino.restaurant.data.di.RepositoryManager
import ru.papino.restaurant.databinding.FragmentOrdersBinding
import ru.papino.restaurant.domain.usecases.GetOrdersUseCase
import ru.papino.restaurant.presentation.orders.adapters.OrdersAdapter
import ru.papino.restaurant.presentation.orders.mappers.OrdersMapper
import ru.papino.restaurant.presentation.orders.models.OrderUIModel
import ru.papino.restaurant.presentation.orders.viewmodels.OrdersViewModel

internal class OrdersFragment : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        OrdersViewModel(
            getOrdersUseCase = GetOrdersUseCase(repository = RepositoryManager(resources = resources).getOrdersInstance()),
            mapper = OrdersMapper()
        )
    }

    private val ordersAdapter = OrdersAdapter(mapper = OrdersMapper())

    private lateinit var binding: FragmentOrdersBinding

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentOrdersBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObserver()

        viewModel.loadOrders()
    }

    private fun initUI() {
        with(binding) {
            ordersRecycler.adapter = ordersAdapter
            ordersRecycler.addItemDecoration(CoreDividerItemDecoration(this.root.context))

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.loadOrders()
            }

            swipeRefreshLayout.setColorSchemeResources(ru.papino.uikit.R.color.backgroundButtonColor)
        }
    }

    private fun initObserver() {
        viewModel.orders.bind(::initOrders)
        viewModel.error.bind(::showError)
        viewModel.loader.bind(::showLoader)
    }

    private fun initOrders(orders: List<OrderUIModel>) {
        showNoOrders(orders.isEmpty())
        ordersAdapter.set(orders)
    }

    private fun showError(ex: Throwable) {
        showNoOrders(true)
    }

    private fun showNoOrders(isVisible: Boolean) {
        binding.run {
            if (isVisible) {
                imageViewErrorNoOrders.visibility = View.VISIBLE
            } else {
                imageViewErrorNoOrders.visibility = View.GONE
            }
        }
    }

    private fun showLoader(isShow: Boolean) {
        binding.run {
            if (isShow) {
                swipeRefreshLayout.isRefreshing = true
                ordersRecycler.visibility = View.GONE
            } else {
                ordersRecycler.visibility = View.VISIBLE
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    companion object {

        fun getInstance() = OrdersFragment()
    }
}