package me.dgahn.fixture

import me.dgahn.example.overview.OverviewData
import me.dgahn.example.summary.Summary
import me.dgahn.example.summary.SummaryData
import me.dgahn.example.title.TitleData
import java.time.ZonedDateTime
import javax.imageio.ImageIO
import kotlin.random.Random

val titleData = TitleData(title = "오늘의 사진", createdTime = ZonedDateTime.now())

val overviewData = OverviewData(
    src = ImageIO.read(imgFile)
)

val summaryData = SummaryData(
    summary = listOf(
        Summary(
            index = 1,
            name = "사진1",
            triple = listOf(
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100)),
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100)),
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100)),
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100)),
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100)),
                Triple(first = ZonedDateTime.now(), second = "이름1", third = Random.nextInt(100))
            ),
            description = "설명이다1. 설명1"
        ),
        Summary(
            index = 2,
            name = "사진2",
            triple = listOf(
                Triple(first = ZonedDateTime.now(), second = "이름2", third = Random.nextInt(200)),
                Triple(first = ZonedDateTime.now(), second = "이름2", third = Random.nextInt(200)),
                Triple(first = ZonedDateTime.now(), second = "이름2", third = Random.nextInt(200)),
                Triple(first = ZonedDateTime.now(), second = "이름2", third = Random.nextInt(200))
            ),
            description = "설명이다2. 설명2"
        ),
        Summary(
            index = 3,
            name = "사진3",
            triple = listOf(
                Triple(first = ZonedDateTime.now(), second = "이름3", third = Random.nextInt(200)),
                Triple(first = ZonedDateTime.now(), second = "이름3", third = Random.nextInt(200)),
                Triple(first = ZonedDateTime.now(), second = "이름3", third = Random.nextInt(200))
            ),
            description = "설명이다3. 설명3"
        )
    )
)