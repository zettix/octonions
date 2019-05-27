package com.zettix.math;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class OctonionTest {

    @Test
    public void testIdentity() {
        Octonion identity = Octonion.GetZero().shift(1);
        for (int i = 0; i < Octonion.EIGHT; i++) {
            Octonion left = Octonion.GetZero().set(i, 1);
            Octonion tester = left.mul(identity);
            assertTrue(tester.equals(left));
            tester = identity.mul(left);
            assertTrue(tester.equals(left));
        }
    }
}
