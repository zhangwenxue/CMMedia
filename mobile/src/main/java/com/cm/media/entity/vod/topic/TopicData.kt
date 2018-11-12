package com.cm.media.entity.vod.topic

data class TopicData(
    val id: Int,
    val createUserId: Int,
    val topicId: Int,
    val isHome: Int,
    val ordersHome: Int,
    val isDiscover: Int,
    val orderDiscover: Int,
    val showType: Int,
    val beginTime: Long,
    val endTime: Long,
    val img: String,
    val title: String,
    val info: String,
    val summary: String,
    val isDelete: String,
    val appCheckStatus: String,
    val attention: String,
    val videoList: List<TopicVod>

)