package me.dgahn.example

import kr.dogfoot.hwplib.`object`.HWPFile
import me.dgahn.example.detail.DetailData
import me.dgahn.example.detail.detail
import me.dgahn.example.overview.OverviewData
import me.dgahn.example.overview.overview
import me.dgahn.example.summary.SummaryData
import me.dgahn.example.summary.summary
import me.dgahn.example.title.TitleData
import me.dgahn.example.title.title
import me.dgahn.hwpdsl.PaperSize
import me.dgahn.hwpdsl.body
import me.dgahn.hwpdsl.build
import me.dgahn.hwpdsl.createHwp
import me.dgahn.hwpdsl.hwp
import me.dgahn.hwpdsl.paperSize
import me.dgahn.hwpdsl.section

class HwpExampleBuilder {
    private val paperSize: PaperSize = PaperSize.A4
    lateinit var hwpFile: HWPFile
    lateinit var titleData: TitleData
    lateinit var overviewData: OverviewData
    lateinit var summaryData: SummaryData
    lateinit var detailData: DetailData
    lateinit var path: String

    fun hwpFile(hwpFile: HWPFile): HwpExampleBuilder {
        this.hwpFile = hwpFile
        return this
    }

    fun titleData(titleData: TitleData): HwpExampleBuilder {
        this.titleData = titleData
        return this
    }

    fun overviewData(overviewData: OverviewData): HwpExampleBuilder {
        this.overviewData = overviewData
        return this
    }

    fun summaryData(summaryData: SummaryData): HwpExampleBuilder {
        this.summaryData = summaryData
        return this
    }

    fun detailData(detailData: DetailData): HwpExampleBuilder {
        this.detailData = detailData
        return this
    }

    fun path(path: String): HwpExampleBuilder {
        this.path = path
        return this
    }

    fun build() {
        hwpFile.createHwp().hwp {
            body {
                section {
                    title(titleData)
                    overview(overviewData)
                    summary(summaryData)
                }
                section(isNewPage = true) {
                    detail(detailData)
                }
            }
        }.paperSize(paperSize)
            .build(path)
    }

}