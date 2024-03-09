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
    },
    salad {
        override fun getResourceId() = R.string.tab_menu_salad
        override fun getFasetFoodName(): String = "salad"
    },
    shashlik {
        override fun getResourceId() = R.string.tab_menu_meat
        override fun getFasetFoodName(): String = "shashlik"
    },
    sendwich {
        override fun getResourceId() = R.string.tab_menu_sandvich
        override fun getFasetFoodName(): String = "sendwich"
    },
    snack {
        override fun getResourceId() = R.string.tab_menu_snacks
        override fun getFasetFoodName(): String = "snack"
    },
    drink {
        override fun getResourceId() = R.string.tab_menu_drink
        override fun getFasetFoodName(): String = "drink"
    },
    bread {
        override fun getResourceId() = R.string.tab_menu_bread
        override fun getFasetFoodName(): String = "bread"
    },
    soup {
        override fun getResourceId() = R.string.tab_menu_soup
        override fun getFasetFoodName(): String = "soup"
    };

    abstract fun getResourceId(): Int
    abstract fun getFasetFoodName(): String
}