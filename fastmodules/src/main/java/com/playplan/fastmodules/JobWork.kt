package com.playplan.fastmodules

import com.playplan.fastmodules.node.FastNode
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executors

/**
 * author : jyt
 * time   : 2021/03/31
 * desc   :
 */
object JobWork {

    private val dispatcher = Executors.newFixedThreadPool(6).asCoroutineDispatcher()
    private var priority = 0
    private var max = 10

    private var job: Job? = null
    private var isEnd = false

    fun toJob(list: ArrayList<FastNode>) {

        runBlocking {
            val listJob = mutableListOf<Job>()
            for (fastNode: FastNode in list) {
                println("jyt-priority" + "=====" + priority + "===" + fastNode.priority + "===" + Thread.currentThread())
                if (priority > fastNode.priority) {
                    continue
                }
                if (priority < fastNode.priority) {
                    break
                }
                listJob.add(if (fastNode.isMainThread) {
                    launch {
                        fastNode.use()
                    }
                } else {
                    launch(dispatcher) {
                        fastNode.use() //  dely  5
                    }
                })
            }
            listJob.forEach {
                it.join()

            }
            withContext(Dispatchers.IO) {

            }
        }

        if (priority < max) {
            priority++
            toJob(list)
        }

    }

}

