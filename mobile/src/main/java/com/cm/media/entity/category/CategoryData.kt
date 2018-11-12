package com.cm.media.entity.category
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryData(
    val id: Int,
    val orders: Int,
    val typeId: Int,
    val name: String,
    val des: String,
    val icon: String,
    val field: String,
    val values: List<CategoryItem>
): Parcelable