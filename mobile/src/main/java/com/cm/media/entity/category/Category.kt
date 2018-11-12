package com.cm.media.entity.category

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val orders: Int,
    val name: String,
    var des: String?,
    var icon: String?,
    var categories: List<CategoryData>?,
    val appCheck: Boolean,
    val usedQuery: Boolean
) : Parcelable {
    companion object {
        fun HOME_CATEGORY(): Category {
            return Category(0, 0, "首页", null, null, null, false, false)
        }
    }
}