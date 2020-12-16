package me.dgahn.example.overview

import me.dgahn.hwpdsl.CtrlHeaderGsoStyle
import me.dgahn.hwpdsl.ImgStyle
import me.dgahn.hwpdsl.ShapeComponentRectangleStyle

internal const val pageWidth = 200
internal const val imgWidth = 190
internal const val imgHeight = 190
internal const val imgMargin = (pageWidth - imgWidth) / 2

internal val imgStyle = ImgStyle(
    shapeComponentRectangleStyle = ShapeComponentRectangleStyle(
        x2 = imgWidth,
        x3 = imgWidth,
        y3 = imgHeight,
        y4 = imgHeight
    ),
    ctrlHeaderGsoStyle = CtrlHeaderGsoStyle(
        outterMarginTop = imgMargin,
        outterMarginRight = imgMargin,
        outterMarginBottom = imgMargin,
        outterMarginLeft = imgMargin
    )
)