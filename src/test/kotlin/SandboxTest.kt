import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import sandbox.Sandbox

class SandboxTest {
    @Test
    fun testSandbox() {
        val sandbox = Sandbox()
        val number = sandbox.findNumber()
        Assertions.assertEquals(201, number)
    }
}
