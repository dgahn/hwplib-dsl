package me.dgahn.example.detail

import java.awt.image.BufferedImage

data class DetailData(
    val detailList: List<Detail>
)

data class Detail(
    val imgSrc: BufferedImage,
    val firstValue: String,
    val secondValue: String,
    val thirdValue: String
)

