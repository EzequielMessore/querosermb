package br.com.messore.tech.exchanges.details

import br.com.messore.tech.exchanges.view.model.DispatchersProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestCoroutineRule : TestRule {

    val testCoroutineDispatcher = StandardTestDispatcher()
    val providers = DispatchersProvider(
        io = testCoroutineDispatcher,
        main = testCoroutineDispatcher,
        default = testCoroutineDispatcher,
        unconfined = testCoroutineDispatcher
    )

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)

            base.evaluate()

            Dispatchers.resetMain()
        }
    }
}
