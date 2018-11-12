package com.cm.media.entity.vod.topic

data class TopicVod(
    val id: Int,
    val videoTopicId: Int,
    val type: Int,
    val source: Int,
    val name: String,
    val info: String,
    val img: String,
    val area: String,
    val year: String,
    val sourceScore: String,
    val sourceIsVip: Int,
    val sourcePlaySum: Int,
    val sourcePlaySumText: String,
    val isVip: Int,
    val updateText: String,
    val playSum: Int,
    val isTrailer: Int,
    val sourceOrderHot: Int,
    val sourceUrl: String,
    val isHas: Int,
    val season: String,
    val isOver: Int,
    val orders: Int,
    val sourceSort: Int

)