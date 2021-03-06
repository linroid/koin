package org.koin.perfs

import org.junit.Test
import org.koin.core.time.measureDurationForResult
import org.koin.dsl.koinApplication
import org.koin.test.assertDefinitionsCount

class PerfsTest {

    @Test
    fun `empty module perfs`() {
        val (app, duration) = measureDurationForResult {
            koinApplication {
            }
        }
        println("started in $duration ms")

        app.assertDefinitionsCount(0)
        app.close()
    }

    /*
    Perfs on MBP 2018
        started in 157.0883 ms
        measured executed in 1.104501 ms
        started in 1.007768 ms
        measured executed in 0.039683 ms
     */
    @Test
    fun `perfModule400 module perfs`() {
        runPerfs()
        runPerfs()
    }

    private fun runPerfs() {
        val (app, duration) = measureDurationForResult {
            koinApplication {
                modules(perfModule400)
            }
        }
        println("started in $duration ms")

        app.assertDefinitionsCount(400)

        val koin = app.koin

        val (_, executionDuration) = measureDurationForResult {
            koin.get<Perfs.A27>()
            koin.get<Perfs.B31>()
            koin.get<Perfs.C12>()
            koin.get<Perfs.D42>()
        }
        println("measured executed in $executionDuration ms")

        app.close()
    }
}