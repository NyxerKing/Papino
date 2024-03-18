package ru.papino.restaurant.data.repository

import android.content.res.Resources
import ru.papino.restaurant.R
import ru.papino.restaurant.domain.repository.ProductTypesRepository
import ru.papino.restaurant.domain.repository.models.ProductTypeModel
import ru.papino.restaurant.domain.repository.models.ProductTypesResponse

internal class ProductTypesRepositoryImpl(private val resources: Resources) :
    ProductTypesRepository {
    override suspend fun request(): ProductTypesResponse =
        ProductTypesResponse.Success(
            products = listOf(
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_pizza),
                    typeValue = "pizza"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_burger),
                    typeValue = "burger"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_salad),
                    typeValue = "salad"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_meat),
                    typeValue = "shashlik"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_sandwich),
                    typeValue = "sendwich"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_snacks),
                    typeValue = "snack"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_drink),
                    typeValue = "drink"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_bread),
                    typeValue = "bread"
                ),
                ProductTypeModel(
                    name = resources.getString(R.string.tab_menu_soup),
                    typeValue = "soup"
                ),
            )
        )
}