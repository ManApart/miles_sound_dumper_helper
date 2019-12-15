import java.util.concurrent.*


class TimedExecutor(private val tasks: List<TimedProcess>, private val timeoutInSeconds: Long) {
    private val executor = Executors.newCachedThreadPool()

    fun execute() {
        val ids = tasks.map { it.id }
        try {
            val futures = executor.invokeAll(tasks, timeoutInSeconds, TimeUnit.SECONDS)
            println("Exported $ids")
        } catch (ex: TimeoutException) {
            println("Stopping $ids")
        } catch (e: InterruptedException) {
            println("$ids Interrupted $e")
        } catch (e: ExecutionException) {
            println("$ids experienced an execution exception $e")
        } finally {
            executor.shutdown()
        }
    }

}