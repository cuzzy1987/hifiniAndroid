package com.me.hifimusic

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        println("你好 测试者")

        // 冒泡排序
        // [1,4,2,5,2,5,7]
        // i下标 max当前最大值 temp替换中间值
        bubbleSort()
    }

    private fun bubbleSort() {
        val arr = arrayOf(1,5,3,0)
        var temp = 0
        var max = 0
        loop@ for(i in arr.indices){
            println(arr[i])
            // kotlin 无三目运算

            // 取出最大值
//            if (i < arr.size-1)temp = if (arr[i] > arr[i+1]) arr[i] else arr[i+1]
            temp = if (i < arr.size - 1){
                if (arr[i] > arr[i+1]) arr[i] else arr[i+1]

            }else{
                if (temp > arr[i]) temp else arr[i]
            }
            // 交换值 大的在前小的在后
        }
        println("max is$temp")
    }
}
