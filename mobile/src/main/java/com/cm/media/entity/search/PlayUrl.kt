package com.cm.media.entity.search

data class PlayUrl(
    val videoId: Int,
    val source: Int,
    val type: Int,
    var name: String,
    val episode: Int,
    var playUrl: String,
    val sourceIsVip: Int
)