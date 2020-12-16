package me.dgahn.fixture

import me.dgahn.example.detail.Detail
import me.dgahn.example.detail.DetailData
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
    summaryList = listOf(
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

val detailData = DetailData(
    detailList = listOf(
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값1",
            secondValue = "두 번째 값1",
            thirdValue = "세 번째 값1"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값2",
            secondValue = "두 번째 값2",
            thirdValue = "세 번째 값2"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값3",
            secondValue = "두 번째 값3",
            thirdValue = "세 번째 값3"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값4",
            secondValue = "두 번째 값4",
            thirdValue = "세 번째 값4"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값5",
            secondValue = "두 번째 값5",
            thirdValue = "세 번째 값5"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값6",
            secondValue = "두 번째 값6",
            thirdValue = "세 번째 값6"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값7",
            secondValue = "두 번째 값7",
            thirdValue = "세 번째 값7"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값8",
            secondValue = "두 번째 값8",
            thirdValue = "세 번째 값8"
        ),
        Detail(
            imgSrc = ImageIO.read(imgFile),
            firstValue = "첫 번째 값9",
            secondValue = "두 번째 값9",
            thirdValue = "세 번째 값9"
        )
    )
)