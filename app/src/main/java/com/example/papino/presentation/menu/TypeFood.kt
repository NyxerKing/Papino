package com.example.papino.presentation.menu

import com.example.papino.R

enum class TypeFood {
    pizza {
        override fun getResourceId() = R.string.tab_menu_pizza
        override fun getFasetFoodName(): String = "pizza"
    },
    burger {
        override fun getResourceId() = R.string.tab_menu_burger
        override fun getFasetFoodName(): String = "burger"
    }
    ,
    salad {
        override fun getResourceId() = R.string.tab_menu_salad
        override fun getFasetFoodName(): String = "salad"
    };

    abstract fun getResourceId(): Int
    abstract fun getFasetFoodName(): String
}