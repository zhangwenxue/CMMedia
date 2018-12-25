package com.cm.media.entity.vod

import androidx.core.util.Pair
import java.util.*

data class RealPlayUrl(val urls: Array<Pair<String, String>>, val episodeCount: Int, val episode: Int) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RealPlayUrl

        if (!Arrays.equals(urls, other.urls)) return false
        if (episodeCount != other.episodeCount) return false
        if (episode != other.episode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(urls)
        result = 31 * result + episodeCount
        result = 31 * result + episode
        return result
    }
}