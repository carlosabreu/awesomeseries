package com.fishtudo.awesomeseries.util

import org.junit.Assert.assertEquals
import org.junit.Test

const val URL_FROM_MEDIUM_IMAGE = "URL MEDIUM"
const val URL_FROM_ORIGINAL_IMAGE = "URL ORIGINAL"

class ImageUtilTest {

    private val imageUtil = ImageUtil()

    @Test
    fun should_ReturnMediumImage_WhenItExists() {
        val values = mapOf(
            MEDIUM_IMAGE to URL_FROM_MEDIUM_IMAGE,
            ORIGINAL_IMAGE to URL_FROM_ORIGINAL_IMAGE
        )

        val result = imageUtil.findProperImageUrl(values)
        assertEquals(URL_FROM_MEDIUM_IMAGE, result)
    }

    @Test
    fun should_ReturnOriginalImage_WhenItExistsAndMediumDoesNotExists() {
        val values = mapOf(
            ORIGINAL_IMAGE to URL_FROM_ORIGINAL_IMAGE
        )

        val result = imageUtil.findProperImageUrl(values)
        assertEquals(URL_FROM_ORIGINAL_IMAGE, result)
    }

    @Test
    fun should_ReturnNull_WhenNoneOfThemExists() {
        val values = mapOf("" to "")
        val result = imageUtil.findProperImageUrl(values)
        assertEquals(null, result)
    }
}