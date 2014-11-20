import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		ArrayList<Integer> carmichaels = new ArrayList<Integer>();
		for (int i = 561; i < 1000000; i++) {
			if (isCarmichael(i)) {
				carmichaels.add(i);
				System.out.println(i);
			}
		}
	}

	private boolean isPrime(int N) {
		if (N < 10000) {
			// trial division
			int sqrt = (int) Math.sqrt(N);
			for (int i = 2; i < sqrt; i++) {
				if (N % i == 0) {
					return false;
				}
			}
			return true;
		} else {
			return millerRabin(N, 30);
		}
	}

	private boolean isCarmichael(int N) {
		// trial division
		if (fermats(N)) {
			if (!isPrime(N)) {
				return true;
			}
		}
		return false;
	}

	private boolean fermats(int N) {

		Random rnd = new Random();
		int fermatsMod;
		int rndInt;
		for (int a = 0; a < (int) Math.sqrt(N); a++) {
			rndInt = rnd.nextInt(N - 1) + 1;
			if (gcd(rndInt, N) == 1) {

				fermatsMod = fermatsCalc(rndInt, N);
				if (fermatsMod != 1) {
					return false;
				}
			}
		}

		return true; // TODO
	}

	private int fermatsCalc(int a, int N) {
		BigInteger bigA = new BigInteger("" + a);
		BigInteger bigN = new BigInteger("" + N);
		BigInteger bigNminusOne = bigN.subtract(BigInteger.ONE);
		BigInteger result = BigInteger.ONE;

		return bigA.modPow(bigNminusOne, bigN).intValue();
	}

	// euclidian algorithm
	private int gcd(int A, int B) {
		if (A < B) {
			int tmp = A;
			A = B;
			B = tmp;
		}

		int divisor;
		int remainder;
		int tmp;
		while (B != 0) {
			tmp = A % B;

			A = B;
			B = tmp;
		}

		return A;
	}

	private boolean millerRabin(int n, int certainty) {
		BigInteger N = new BigInteger("" + n);
		if (N.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
			return false;
		}
		Random rnd = new Random();
		for (int i = 0; i < certainty; i++) {
			BigInteger a;
			BigInteger N_1 = N.subtract(BigInteger.ONE);
			do {
				a = new BigInteger(N.bitLength(), rnd);
			} while (a.equals(BigInteger.ZERO) || a.equals(BigInteger.ONE) || a.compareTo(N_1) >= 0);

			boolean pass = false;
			BigInteger d = N_1;
			int s = d.getLowestSetBit();
			d = d.shiftRight(s);
			BigInteger x = a.modPow(d, N);
			if (x.equals(BigInteger.ONE)) {
				pass = true;
			}
			for (int j = 0; j < s - 1; j++) {
				if (x.equals(N_1)) {
					pass = true;
				}
				x = x.multiply(x).mod(N);
			}
			if (x.equals(N_1)) {
				pass = true;
			}
			if (!pass) {
				return false;
			}
		}
		return true;
	}

}
