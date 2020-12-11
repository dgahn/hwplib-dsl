package me.dgahn

import kr.dogfoot.hwplib.`object`.HWPFile

interface HwpTagBuilder : TagBuilder {

    val hwpFile: HWPFile
}
