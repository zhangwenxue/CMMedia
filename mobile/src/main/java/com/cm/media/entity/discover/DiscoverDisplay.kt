package com.cm.media.entity.discover

data class DiscoverDisplay(
    val id: Int,
    val createUserId: Int,
    var title: String,
    var info: String,
    val videoDTOList: List<DiscoverDisplayItem>,
    val heartCount: Int,
    val isHeart: Int,
    val attention: Int
)