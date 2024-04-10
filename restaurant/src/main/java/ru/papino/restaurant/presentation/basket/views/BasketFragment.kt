package ru.papino.restaurant.presentation.basket.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.papino.restaurant.R
import ru.papino.restaurant.core.CoroutineProperty
import ru.papino.restaurant.core.recycler.decorations.CoreDividerItemDecoration
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.core.user.di.UserDI
import ru.papino.restaurant.core.user.models.User
import ru.papino.restaurant.data.repository.OrdersRepositoryImpl
import ru.papino.restaurant.databinding.FragmentBasketBinding
import ru.papino.restaurant.domain.repository.models.status.BasketActionStatus
import ru.papino.restaurant.domain.usecases.CreateOrderUseCase
import ru.papino.restaurant.extensions.toPrice
import ru.papino.restaurant.presentation.basket.adapters.BasketAdapter
import ru.papino.restaurant.presentation.basket.mappers.BasketMapper
import ru.papino.restaurant.presentation.basket.models.BasketUIModel
import ru.papino.restaurant.presentation.basket.viewmodels.BasketViewModel
import ru.papino.uikit.dialogs.AlertDialog
import ru.papino.uikit.extensions.setText
import ru.papino.uikit.extensions.showAlert

internal class BasketFragment : Fragment(), CoroutineProperty {

    private val viewModel by lazy {
        BasketViewModel(
            basketRepository = RoomDependencies.basketRepository,
            createOrderUseCase = CreateOrderUseCase(ordersRepository = OrdersRepositoryImpl.getInstance()),
            basketMapper = BasketMapper()
        )
    }

    private lateinit var binding: FragmentBasketBinding

    //private lateinit var activityLauncher: ActivityResultLauncher<String>
    private val basketAdapter = BasketAdapter()

    private var sumProducts = 0.0
    private var sumBonus = 0L
    private var sumToPay = 0.0

    override val parentLifecycle: LifecycleOwner
        get() = viewLifecycleOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*activityLauncher =
            registerForActivityResult(AddressActivityContract()) { result ->
                result?.let {
                    binding.inputAddress.setText(it)
                }
            }*/
    }

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

            switchBonus.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if (UserDI.isUserInitializer()) {
                        updateDiscount(UserDI.user.bonus)
                    } else {
                        updateDiscount()
                    }
                } else {
                    updateDiscount()
                }
            }

            buttonCheckout.setOnClickListener {
                if (UserDI.isUserInitializer()) {
                    if (viewModel.checkOrderParameters(
                            userId = UserDI.user.id,
                            address = inputAddress.editText?.text?.toString().orEmpty(),
                            sum = sumToPay,
                            useBonusSum = sumBonus
                        )
                    ) {
                        frameProgressIndicator.visibility = VISIBLE
                        viewModel.createOrder(
                            userId = UserDI.user.id,
                            useBonus = sumBonus > 0,
                            address = inputAddress.editText?.text?.toString().orEmpty(),
                            sum = sumToPay,
                            onComplete = { error ->
                                lifecycleScope.launch(Dispatchers.Main) {
                                    error?.let { ex ->
                                        AlertDialog(
                                            context = binding.root.context,
                                            title = resources.getString(R.string.dialog_warning_title),
                                            message = ex.message.orEmpty(),
                                            onClick = {}
                                        )
                                    } ?: run {
                                        RoomDependencies.basketRepository.clear()
                                        viewModel.loadBasket()
                                    }
                                    frameProgressIndicator.visibility = GONE
                                }
                            }
                        )
                    } else {
                        context?.showAlert(
                            title = resources.getString(R.string.dialog_warning_title),
                            message = resources.getString(R.string.dialog_message_bascket_title),
                            onClick = {}
                        )
                    }
                }
            }

            if (UserDI.isUserInitializer()) {
                inputAddress.setText(UserDI.user.address)
            }

            inputAddress.setEndIconOnClickListener {
                context?.run {
                    // todo activityLauncher.launch("")
                }
            }
        }
    }

    private fun initObserver() {
        UserDI.onInitUser.bind(::updateUser)
        RoomDependencies.basketRepository.changeBasket.bind(::basketChange)
        viewModel.basket.bind(::updateUI)
    }

    private fun updateUser(user: User?) {
        binding.run {
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

    private fun updateSum() {
        with(binding) {
            sumToPay = if (sumProducts > sumBonus) sumProducts - sumBonus else 0.0

            toPaySum.text = getPrice(sumToPay)
            titleOrderFoodSum.text = getPrice(sumProducts)

            orderForm.visibility = VISIBLE
        }
    }

    private fun updateUI(basket: List<BasketUIModel>) {
        basketAdapter.set(
            list = basket,
            viewModel::deleteProduct,
            viewModel::minusProduct,
            viewModel::plusProduct
        )

        binding.buttonCheckout.isEnabled = basket.isNotEmpty() && UserDI.isUserInitializer()

        binding.titleOrderFood.text =
            resources.getString(
                ru.papino.uikit.R.string.insert_products,
                basket.sumOf { product -> product.count }.toString()
            )

        sumProducts = basket.sumOf { product -> product.price.toPrice() * product.count }
        updateSum()
    }

    private fun updateDiscount(value: Long = 0) {
        with(binding) {
            sumBonus = value
            titleOrderDiscountSum.text =
                getPrice((if (sumProducts < sumBonus) sumProducts else sumBonus).toLong())

            updateSum()
        }
    }

    private fun getPrice(value: Double) =
        resources.getString(ru.papino.uikit.R.string.insert_sum, value.toPrice())

    private fun getPrice(value: Long) =
        resources.getString(ru.papino.uikit.R.string.insert_sum, value.toString())

    private suspend fun basketChange(basketStatus: BasketActionStatus) {
        val count = RoomDependencies.basketRepository.getCountAll()
        binding.titleOrderFood.text =
            resources.getString(ru.papino.uikit.R.string.insert_products, count.toString())
    }

    companion object {

        fun getInstance() = BasketFragment()
    }
}