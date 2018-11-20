package com.cm.media.entity.vod

data class VodPlayUrl(
    val id: Int,
    val videoId: Int,
    var name: String?,
    var title: String?,
    var imgText: String?,
    var img: String?,
    var season: String?,
    val episode: Int,
    var playUrl: String?,
    val clarity: Int,
    val isPrevue: Int,
    val sourceIsVip: Int,
    val sourcePlaySum: Int,
    var sourcePlaySumText: String?
)