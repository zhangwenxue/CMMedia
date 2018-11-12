package com.cm.media.entity

data class Entity<T>(val code: Int, var message: String, val data: T)