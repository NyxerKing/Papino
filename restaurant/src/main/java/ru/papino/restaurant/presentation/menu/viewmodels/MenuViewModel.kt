package ru.papino.restaurant.presentation.menu.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.papino.restaurant.core.room.RoomDependencies
import ru.papino.restaurant.core.statuses.ActionStatus
import ru.papino.restaurant.domain.repository.BasketRepository
import ru.papino.restaurant.domain.repository.models.MenuResponse
import ru.papino.restaurant.domain.repository.models.status.BasketActionStatus
import ru.papino.restaurant.domain.usecases.GetMenuUseCase
import ru.papino.restaurant.domain.usecases.GetProductTypesUseCase
import ru.papino.restaurant.presentation.menu.mappers.MenuMapper
import ru.papino.restaurant.presentation.menu.models.ProductTypeUIModel
import ru.papino.restaurant.presentation.menu.models.ProductUIModel

internal class MenuViewModel(
    private val getProductTypesUseCase: GetProductTypesUseCase,
    private val getMenuUseCase: GetMenuUseCase,
    private val mapper: MenuMapper,
    private val basketRepository: BasketRepository
) : ViewModel() {

    private val _productTypes = MutableStateFlow<List<ProductTypeUIModel>?>(null)
    val productTypes = _productTypes.asStateFlow()

    private val _menu = MutableSharedFlow<List<ProductUIModel>?>()
    val menu = _menu.asSharedFlow()

    private val _error = MutableSharedFlow<Throwable>()
    val error = _error.asSharedFlow()

    private val _loader = MutableStateFlow(false)
    val loader = _loader.asStateFlow()

    private var products: List<ProductUIModel>? = null
    private var productTypeSelected: ProductTypeUIModel? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _loader.emit(true)
            loadProductTypes()
            loadMenu()
        }

        initObservers()
    }

    private fun initObservers() {
        basketChangeObserver()
    }

    fun filterProducts(selectType: ProductTypeUIModel) {
        productTypeSelected = selectType
        viewModelScope.launch {
            _menu.emit(getFilteredProducts(selectType.typeProduct))
        }
    }

    fun addBasket(product: ProductUIModel) {
        viewModelScope.launch {
            val basketProducts = basketRepository.getAll()
            val currentProduct = mapper.toProductDomain(product)

            val selectProduct = basketProducts.firstOrNull { obj -> obj.id == currentProduct.id }
            selectProduct?.id?.let { id ->
                val count = basketRepository.getCount(id)
                basketRepository.update(id, count + 1)
            } ?: run {
                basketRepository.add(currentProduct)
            }
        }
    }

    fun getSelectedProductType() = productTypeSelected

    private suspend fun getFilteredProducts(typeProductName: String): List<ProductUIModel>? {
        val basketProducts = basketRepository.getAll()
        val filterProducts = products?.filter { product -> product.typeProduct == typeProductName }
        filterProducts?.forEach { product ->
            product.isBasket = basketProducts.any { basket -> basket.id == product.id }
        }
        return filterProducts
    }

    private suspend fun loadProductTypes() {
        runCatching {
            getProductTypesUseCase()
        }.onSuccess { response ->
            mapper.toProductTypesUI(response)?.let { productTypes ->
                productTypeSelected = productTypes.first()
                _productTypes.emit(productTypes)
            }
        }.onFailure { ex: Throwable? ->
            _productTypes.emit(null)
        }
    }

    private suspend fun loadMenu() {
        runCatching {
            getMenuUseCase()
        }.onSuccess { response ->
            when (response) {
                is MenuResponse.Success -> {
                    products = mapper.toUI(response)

                    productTypeSelected?.let {
                        filterProducts(it)
                    } ?: run {
                        _menu.emit(products)
                    }
                }

                is MenuResponse.Error -> {
                    _error.emit(response.error)
                    products = mapper.toUI(response)

                    productTypeSelected?.let {
                        filterProducts(it)
                    } ?: run {
                        _menu.emit(products)
                    }
                }
            }
            _loader.emit(false)
        }.onFailure { ex: Throwable? ->
            _error.emit(ex ?: Throwable("no data"))
            _loader.emit(false)
        }
    }

    private fun basketChangeObserver() {
        viewModelScope.launch {
            RoomDependencies.basketRepository.changeBasket.collect(::changeProductStatus)
        }
    }

    private fun changeProductStatus(basketActionStatus: BasketActionStatus) {
        productTypeSelected?.let { productType ->
            when (basketActionStatus.status) {
                ActionStatus.DELETE -> filterProducts(productType)
                ActionStatus.CLEAR -> filterProducts(productType)
                else -> {}
            }
        }

    }
}