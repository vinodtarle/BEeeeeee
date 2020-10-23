package com.beershop.adgaon.base.utility.constant

class Path {
    companion object {

        const val comments = "comments"
        const val likes = "likes"
        const val ratings = "ratings"
        const val schools = "schools"
        const val school_name = "school_name"
        const val users = "users"

        const val shop = "shop"
        const val shop_id = "lekNOUUHKyZP4pLg4li0"

        private const val base_path = "$shop/$shop_id/"

        const val unit = base_path.plus("unit")
        const val item = base_path.plus("item")
        const val stock = base_path.plus("stock")
        const val sale = base_path.plus("sale")
        const val supplier = base_path.plus("supplier")
    }
}
        
    
