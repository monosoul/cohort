package com.sksamuel.cohort.system

import com.sksamuel.cohort.HealthCheck
import com.sksamuel.cohort.HealthCheckResult

/**
 * A Cohort [HealthCheck] that checks free memory in the system.
 *
 * The check is considered healthy if the amount of free memory is above [minFreeBytes].
 */
class MemoryHealthCheck(private val minFreeBytes: Int) : HealthCheck {

  companion object {
    fun mb(mb: Int) = MemoryHealthCheck(mb * 1024 * 1024)
    fun gb(gb: Int) = MemoryHealthCheck(gb * 1024 * 1024 * 1024)
  }

  override suspend fun check(): HealthCheckResult {
    val free = Runtime.getRuntime().freeMemory()
    return if (free < minFreeBytes) {
      HealthCheckResult.Unhealthy("Freemem is below threshold [$free < $minFreeBytes]", null)
    } else {
      HealthCheckResult.Healthy("Freemem is above threshold [$free >= $minFreeBytes]")
    }
  }

}
