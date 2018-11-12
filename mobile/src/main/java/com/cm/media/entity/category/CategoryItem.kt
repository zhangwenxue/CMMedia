package com.cm.media.entity.category

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItem(
    val id: Int,
    val typeId: Int,
    val categoryId: Int,
    var name: String,
    val value: Long,
    val type: Int,
    val field: String,
    val isDesc: Int
): Parcelable