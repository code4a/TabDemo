package com.jiangyt.lottery.data

/**
 * Desc: com.jiangyt.lottery.data
 * <p>
 * @author Create by sinochem on 2020-01-13
 * <p>
 * Version: 1.0.0
 */
class Randoms {

    companion object {
        /**
         * 获取20个数据
         */
        fun get45RandomData(): ArrayList<RandomData> {
            val randoms = arrayListOf<RandomData>()
            for (i in 0 until 20) {
                randoms.add(RandomData())
            }
            return randoms
        }

        /**
         * 获取64个数据
         */
        fun get88RandomData(): ArrayList<RandomData> {
            val randoms = arrayListOf<RandomData>()
            for (i in 0 until 64) {
                randoms.add(RandomData())
            }
            return randoms
        }
    }
}