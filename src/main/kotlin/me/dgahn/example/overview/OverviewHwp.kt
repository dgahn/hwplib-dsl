package me.dgahn.example.overview

import me.dgahn.hwpdsl.SECTION
import me.dgahn.hwpdsl.img

fun SECTION.overview(data: OverviewData) {
    img(src = data.src, width = imgWidth, height = imgHeight, imgStyle = imgStyle)
}