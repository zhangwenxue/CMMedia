package com.cm.media.entity.discover.banner

data class BannerItem(
    val adPositionId: Int,
    val id: Int,
    var img: String,
    val isDelete: Int,
    val orders: Int,
    var title: String,
    val type: Int,
    val url: String
)