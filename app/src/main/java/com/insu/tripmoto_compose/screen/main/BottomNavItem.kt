package com.insu.tripmoto_compose.screen.main

import com.insu.tripmoto_compose.R.drawable as AppIcon

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object WishList: BottomNavItem("WishList", AppIcon.ic_wishlist, "wishList")
    object Direction: BottomNavItem("Direction", AppIcon.ic_direction, "direction")
    object Map: BottomNavItem("Map", AppIcon.ic_map, "map")
    object Chat: BottomNavItem("Chat", AppIcon.ic_chat, "chat")
    object Menu: BottomNavItem("Menu", AppIcon.ic_menu, "menu")
}