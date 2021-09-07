package mezzari.torres.lucas.dietbox_challenge.model;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;

/**
 * @author Lucas T. Mezzari
 * @since 07/09/2021
 */
public class FlowTest extends TestCase {

    // ------------------------------------ Thread

    public void test_flow_instance_thread_execution() {
        Flow<String> flow = new Flow<>((f) -> {
            try {
                Thread.sleep(1000);
                fail("Main thread was in hold");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
        assertNotNull(flow);
        assertNull(flow.getValue());
    }

    public void test_flow_instance_thread_safety_execution() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String value = "Testing";
        Flow<String> flow = new Flow<>(value, (f) -> {
            try {
                Thread.sleep(1000);
                countDownLatch.countDown();
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });

        countDownLatch.await();

        assertNotNull(flow);
        assertNotNull(flow.getValue());
        assertEquals(flow.getValue(), value);
    }

    // ------------------------------------ Collect

    public void _test_flow_collect_emit_with_initial_null() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final String value = "Test";

        Flow<String> flow = new Flow<>((f) -> {
            try {
                Thread.sleep(1000);
                System.out.println("Hello");
                f.emit(value);
            } catch (Exception e) {
                fail(e.getMessage());
                countDownLatch.countDown();
            }
        });

        flow.collect((f, v) -> {
            System.out.println("Now");
            assertEquals(v, value);
            countDownLatch.countDown();
        });

        System.out.println("Awaiting");
        countDownLatch.await();

        assertNotNull(flow);
        assertNotNull(flow.getValue());
    }

    // ------------------------------------ Flow execute Thread

    public void test_flow_static_thread_execution() {
        Flow.execute(() -> {
            try {
                Thread.sleep(1000);
                fail("Main thread was in hold");
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }
}