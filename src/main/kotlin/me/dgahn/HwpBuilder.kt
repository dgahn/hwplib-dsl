package me.dgahn

import kr.dogfoot.hwplib.reader.HWPReader
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker

fun createHwp() = BlankFileMaker.make()

fun readHwp(path: String) = HWPReader.fromFile(path)