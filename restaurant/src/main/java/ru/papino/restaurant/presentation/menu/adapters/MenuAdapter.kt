package ru.papino.restaurant.presentation.menu.adapters

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.papino.restaurant.core.imageLoader.ImageLoader
import ru.papino.restaurant.presentation.menu.models.ProductUIModel
import ru.papino.uikit.components.cards.CardMenuBase
import ru.papino.uikit.components.cards.MenuButtonType

internal class MenuAdapter(
    private val onClickItem: (ProductUIModel) -> Unit,
    private val imageLoader: ImageLoader
) :
    RecyclerView.Adapter<MenuAdapter.MenuAdapterHolder>() {

    private var products: List<ProductUIModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapterHolder {
        return MenuAdapterHolder(CardMenuBase(parent.context))
    }

    override fun getItemCount(): Int = products?.size ?: 0

    override fun onBindViewHolder(holder: MenuAdapterHolder, position: Int) {
        val item = products?.get(position)
        item?.let { holder.set(it) }
    }

    fun set(list: List<ProductUIModel>?) {
        products = list
        notifyDataSetChanged()
    }

    inner class MenuAdapterHolder(private val cardMenu: CardMenuBase) :
        RecyclerView.ViewHolder(cardMenu) {
        fun set(product: ProductUIModel) {
            initView(product = product, drawable = null)

            product.linkCover?.let { url ->
                imageLoader.getDrawable(context = cardMenu.context, url) { drawable ->
                    drawable?.let { cover ->
                        initView(product = product, drawable = cover)
                    }
                }
            }
        }

        private fun initView(product: ProductUIModel, drawable: Drawable? = null) {
            with(cardMenu) {
                var buttonText = resources.getString(ru.papino.uikit.R.string.add_to_cart)
                var buttonColor = ru.papino.uikit.R.color.backgroundButtonColor

                if (product.isBasket) {
                    buttonText = resources.getString(ru.papino.uikit.R.string.to_cart)
                    buttonColor = ru.papino.uikit.R.color.backgroundButtonGreyMedium
                }

                set(imageDrawable = drawable,
                    title = "${product.name} ${product.size}",
                    subtitle = product.details,
                    price = product.price,
                    priceCount = cardMenu.resources.getString(ru.papino.uikit.R.string.count_text),
                    buttonText = buttonText,
                    buttonColor = buttonColor,
                    buttonOnClick = {
                        product.isBasket = true
                        onClickItem(product)
                        setType(MenuButtonType.BASKET)
                    }
                )

                if (drawable == null) showShimmer() else hideShimmer()
            }
        }
    }
}