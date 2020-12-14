package me.dgahn.hwpdsl

import kr.dogfoot.hwplib.`object`.HWPFile

interface HwpTagBuilder : TagBuilder {

    val hwpFile: HWPFile
}
