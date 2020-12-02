package me.dgahn.fixture

import me.dgahn.readHwp

val sample = "sample.hwp"
val samplePath = object {}.javaClass.classLoader.getResource(sample)!!.path
val hwpFile = readHwp(samplePath)
val imageName = "sample.png"
val imgFile = object {}.javaClass.classLoader.getResource(imageName)