package me.dgahn.example.overview

import me.dgahn.hwpdsl.ImgStyle
import me.dgahn.hwpdsl.ShapeComponentRectangleStyle

internal const val imgWidth = 200
internal const val imgHeight = 190

internal val imgStyle = ImgStyle(
    shapeComponentRectangleStyle = ShapeComponentRectangleStyle(
        x2 = imgWidth,
        x3 = imgWidth,
        y3 = imgHeight,
        y4 = imgHeight
    )
)