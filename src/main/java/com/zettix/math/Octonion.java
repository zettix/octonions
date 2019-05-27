package com.zettix.math;

/**
 * In this implementation of Octonions, the elements are 32-bit integers in a primitive 8-length array.
 * The design attempts to make the Octonions immutable by returning new Octonions for all binary operations.
 * However the elements themselves are multable and can be modified with set() and shift().
 */

public class Octonion  {
    public final static int EIGHT = 8;
    private final int[] e;
    private static final int[] MulMatrix = {
      1, 2, 3, 4, 5, 6, 7, 8,
      2,-1, 4,-3, 6,-5,-8, 7,
      3,-4,-1, 2, 7, 8,-5,-6,
      4, 3,-2,-1, 8,-7, 6,-5,
      5,-6,-7,-8,-1, 2, 3, 4,
      6, 5,-8, 7,-2,-1,-4, 3,
      7, 8, 5,-6,-3, 4,-1,-2,
      8,-7, 6, 5,-4,-3, 2,-1};

    private Octonion(int[] inp) {
        e = inp;
    }

    private Octonion() {
        this(new int[8]);
    }

    /**
     *  Create a new zero-vector octionion: 0, 0, 0, 0, 0, 0, 0, 0.
     */
    public static Octonion GetZero() {
        return new Octonion();
    }

    /**
     *  Create a new octionion with supplied vector.
     *
     * @param elems Length 8 integer array.
     */
    public static Octonion GetFromVector(int[] elems) {
        if (elems.length < 8) {
            throw new UnsupportedOperationException("Octonions are length 8 vectors.");
        }
        int[] sac = new int[EIGHT];
        System.arraycopy(elems,0, sac, 0, 8);
        return new Octonion(sac);
    }

    /**
     * Set element in octonion vector at given position.
     *
     * @param position element position to insert (0-7)
     * @param elem element value
     * @return <b>this</b>
     */
    public Octonion set(int position, int elem) {
        e[position] = elem;
        return this;
    }

    /**
     * Get element in octonion vector at given position.
     *
     * @param position element position to insert (0-7)
     */
    public int get(int position) {
        return e[position];
    }

    /**
     * Return copy of element vector of <b>this</b>.
     * @return Length 8 integer array copy of vector elements.
     */
    public int[] getArray() {
        int[] sac = new int[EIGHT];
        System.arraycopy(e,0, sac, 0, 8);
        return sac;
    }

    /**
     * Copy <b>this</b>
     * @return Octonion copy of <b>this</b>
     */
    Octonion copy() {
      return new Octonion(getArray());
    }

    /**
     * Shift element into octonion vector.
     * <p>
     * Insert new element into position 0 and
     * shift all elements up one position.
     * Position 7 is lost.
     * </p>
     *
     * @param elem new element at position 0.
     * @return <b>this</b>
     */
    public Octonion shift(int elem) {
        e[7] = e[6];
        e[6] = e[5];
        e[5] = e[4];
        e[4] = e[3];
        e[3] = e[2];
        e[2] = e[1];
        e[1] = e[0];
        e[0] = elem;
        return this;
    }

    /**
     * Scalar Multiplication of <b>this</b> and given integer.
     *
     * @param s integer to multiply each element of <b>this</b> by.
     * @return Octionion z = s * <b>this</b>
     */
    public Octonion scalarMul(int s) {
        int[] accum = new int[EIGHT];
        for (int i = 0; i < EIGHT; i++) {
            accum[i] = e[i] * s;
        }
        return new Octonion(accum);
    }

    /**
     * Modulo of <b>this</b> and given integer.
     *
     * @param m moldulo base for <b>this</b>
     * @return Octionion z = <b>this</b> % y
     */
    public Octonion mod(int m) {
        int[] accum = new int[EIGHT];
        for (int i = 0; i < EIGHT; i++) {
            accum[i] = e[i] % m;
        }
        return new Octonion(accum);
    }

    /**
     * Addition of two Octonions.
     *
     * @param y Octionion to add to <b>this</b>
     * @return Octionion z = <b>this</b> + y
     */
    public Octonion add(Octonion y) {
        int[] accum = new int[EIGHT];
        for (int i = 0; i < EIGHT; i++) {
            accum[i] = e[i] + y.e[i];
        }
        return new Octonion(accum);
    }

    /**
     * Innner (dot) product of two Octonions.
     *
     * @param y Octionion to dot to <b>this</b>
     * @return Octionion z = <b>this</b> . y
     */
    public int dot(Octonion y) {
        int accum = 0;
        for (int i = 0; i < EIGHT; i++) {
            accum += e[i] * y.e[i];
        }
        return accum;
    }

    /**
     * Rotate positions of the Octonion up positions, with position 7 becoming position 0.
     *
     * @param cellcount Number of positions to rotate <b>this</b>
     */
    public Octonion rot(int cellcount) {
        if (cellcount < 0 || cellcount > 8) {
            throw new UnsupportedOperationException("Cellcount must be 0 or greater but less than 8:" + cellcount);
        }
        int[] rotated = new int[8];
     //   System.err.println("Before:" + this);
        System.arraycopy(e, cellcount, rotated, 0, 8 - cellcount);
        System.arraycopy(e, 0,rotated, 8 - cellcount, cellcount);
        System.arraycopy(rotated, 0, e, 0, 8);
     //   System.err.println("After:" + this);
        return this;
    }

    /**
     * Product of two octonions.
     * <p>
     *     The multiplication table used was given in the
     *     2019-05-25 version of https://en.wikipidia.org
     *     article <em>octionion</em>
     * </p>
     * @param y Octionion to post multiply <b>this</b>
     * @return Octionion z = <b>this</b> * y
     */
    public Octonion mul(Octonion y) {
        int[] accum = new int[EIGHT];
        for (int i = 0; i < EIGHT; i++) {
            for (int j = 0; j < EIGHT; j++) {
                Integer prod = e[i] * y.e[j];
                int idx = MulMatrix[j * EIGHT + i];
                if (idx < 0) {
                    prod = -prod;
                    idx = -idx;
                }
                accum[idx - 1] += prod;
            }
        }
        return new Octonion(accum);
    }

    

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(e[0]);
        for (int i = 1; i < EIGHT; i++) {
            sb.append(" ").append(e[i]);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof Octonion)) {
            return false;
        }
        Octonion oo = (Octonion) o;
        for (int i = 0; i < EIGHT; i++) {
            if (oo.e[i] != e[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int acc = e[0];
        acc = 7 * acc +  e[1];
        acc = 7 * acc +  e[2];
        acc = 7 * acc +  e[3];
        acc = 7 * acc +  e[4];
        acc = 7 * acc +  e[5];
        acc = 7 * acc +  e[6];
        acc = 7 * acc +  e[7];
        return acc;
    }
}