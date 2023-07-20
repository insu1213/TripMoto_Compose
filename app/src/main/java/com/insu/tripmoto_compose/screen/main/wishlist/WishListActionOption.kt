package com.insu.tripmoto_compose.screen.main.wishlist

enum class WishListActionOption(val title: String) {
    EditWishList("Edit wishList"),
    ToggleFlag("Toggle flag"),
    DeleteWishList("Delete wishList");

    companion object {
        fun getByTitle(title: String): WishListActionOption {
            values().forEach { action -> if(title == action.title) return action }

            return EditWishList

        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            values().forEach { wishListAction ->
                if(hasEditOption || wishListAction != EditWishList) {
                    options.add(wishListAction.title)
                }
            }
            return options
        }
    }
}