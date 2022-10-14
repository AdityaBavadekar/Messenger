/*
 *
 *    Copyright 2022 Aditya Bavadekar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.adityaamolbavadekar.messenger.utils.shake

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger

/**
 * Detects phone shaking. If more than 75% of the samples taken in the past 0.5s are
 * accelerating, the device is a) shaking, or b) free falling 1.84m (h =
 * 1/2*g*t^2*3/4).
 *
 * @author Bob Lee (bob@squareup.com)
 * @author Eric Burke (eric@squareup.com)
 */
class ShakeDetector(private val listener: Listener) : SensorEventListener {
    /** Listens for shakes.  */
    interface Listener {
        /** Called on the main thread when the device is shaken.  */
        fun onShakeDetected()
    }

    private val queue = SampleQueue()
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null

    /**
     * Starts listening for shakes on devices with appropriate hardware.
     *
     * @return true if the device supports shake detection.
     */
    fun start(sensorManager: SensorManager): Boolean {
        if (accelerometer != null) {
            return true
        }
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            this.sensorManager = sensorManager
            if (sensorManager.registerListener(
                    this,
                    accelerometer,
                    SensorManager.SENSOR_DELAY_FASTEST
                )
            ) {
                InternalLogger.d(TAG, "Registered successfully.")
            }
        }
        return accelerometer != null
    }

    /**
     * Starts listening for shakes on devices with appropriate hardware.
     *
     * @return true if the device supports shake detection.
     */
    fun start(context: Context): Boolean {
        return start(context.getSystemService(Context.SENSOR_SERVICE) as SensorManager)
    }

    /**
     * Stops listening. Safe to call when already stopped. Ignored on devices without appropriate
     * hardware.
     */
    fun stop() {
        if (accelerometer != null) {
            queue.clear()
            sensorManager!!.unregisterListener(this, accelerometer)
            sensorManager = null
            accelerometer = null
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val accelerating = isAccelerating(event)
        val timestamp = event.timestamp
        queue.add(timestamp, accelerating)
        if (queue.isShaking) {
            queue.clear()
            InternalLogger.logW(TAG, "Shake was detected.")
            InternalLogger.logW(TAG, "Notifying listener.")
            listener.onShakeDetected()
        }
    }

    /** Returns true if the device is currently accelerating.  */
    private fun isAccelerating(event: SensorEvent): Boolean {
        val ax = event.values[0]
        val ay = event.values[1]
        val az = event.values[2]

        // Instead of comparing magnitude to ACCELERATION_THRESHOLD,
        // compare their squares. This is equivalent and doesn't need the
        // actual magnitude, which would be computed using (expensive) Math.sqrt().
        val magnitudeSquared = (ax * ax + ay * ay + az * az).toDouble()
        return magnitudeSquared > SHAKE_THRESHOLD * SHAKE_THRESHOLD
    }

    /** Queue of samples. Keeps a running average.  */
    internal class SampleQueue {
        private val pool = SamplePool()
        private var oldest: Sample? = null
        private var newest: Sample? = null
        private var sampleCount = 0
        private var acceleratingCount = 0

        /**
         * Adds a sample.
         *
         * @param timestamp    in nanoseconds of sample
         * @param accelerating true if > [.SHAKE_THRESHOLD].
         */
        fun add(timestamp: Long, accelerating: Boolean) {
            purge(timestamp - MAX_WINDOW_SIZE)
            val added = pool.acquire()
            added.timestamp = timestamp
            added.accelerating = accelerating
            added.next = null
            if (newest != null) {
                newest!!.next = added
            }
            newest = added
            if (oldest == null) {
                oldest = added
            }
            sampleCount++
            if (accelerating) {
                acceleratingCount++
            }
        }

        /** Removes all samples from this queue.  */
        fun clear() {
            while (oldest != null) {
                val removed: Sample = oldest as Sample
                oldest = removed.next
                pool.release(removed)
            }
            newest = null
            sampleCount = 0
            acceleratingCount = 0
        }

        /** Purges samples with timestamps older than cutoff.  */
        private fun purge(cutoff: Long) {
            while (sampleCount >= MIN_QUEUE_SIZE && oldest != null && cutoff - oldest!!.timestamp > 0) {
                val removed: Sample = oldest as Sample
                if (removed.accelerating) {
                    acceleratingCount--
                }
                sampleCount--
                oldest = removed.next
                if (oldest == null) {
                    newest = null
                }
                pool.release(removed)
            }
        }

        /**
         * Returns true if we have enough samples and more than 3/4 of those samples
         * are accelerating.
         */
        val isShaking: Boolean
            get() = newest != null && oldest != null && newest!!.timestamp - oldest!!.timestamp >= MIN_WINDOW_SIZE && acceleratingCount >= (sampleCount shr 1) + (sampleCount shr 2)

        companion object {
            /** Window size in ns. Used to compute the average.  */
            private const val MAX_WINDOW_SIZE: Long = 500000000 // 0.5s
            private const val MIN_WINDOW_SIZE = MAX_WINDOW_SIZE shr 1 // 0.25s

            /**
             * Ensure the queue size never falls below this size, even if the device
             * fails to deliver this many events during the time window. The LG Ally
             * is one such device.
             */
            private const val MIN_QUEUE_SIZE = 4
        }
    }

    /** An accelerometer sample.  */
    internal class Sample {
        /** Time sample was taken.  */
        var timestamp: Long = 0

        /** If acceleration > [.SHAKE_THRESHOLD].  */
        var accelerating = false

        /** Next sample in the queue or pool.  */
        var next: Sample? = null
    }

    /** Pools samples. Avoids garbage collection.  */
    internal class SamplePool {
        private var head: Sample? = null

        /** Acquires a sample from the pool.  */
        fun acquire(): Sample {
            var acquired = head
            if (acquired == null) {
                acquired = Sample()
            } else {
                head = acquired.next
            }
            return acquired
        }

        /** Returns a sample to the pool.  */
        fun release(sample: Sample) {
            sample.next = head
            head = sample
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    companion object {
        private val TAG = ShakeDetector::class.java.simpleName
        private const val SHAKE_THRESHOLD = 13
    }
}

