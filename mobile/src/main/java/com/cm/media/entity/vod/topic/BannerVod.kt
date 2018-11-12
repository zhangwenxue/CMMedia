package com.cm.media.entity.vod.topic

data class BannerVod(
    val id: Int,
    val isDelete: Int,
    val orders: Int,
    val img: String,
    val title: String,
    val type: Int,
    val url: String,
    val adPositionId: Int
)