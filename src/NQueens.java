import java.util.*;

/**
 * @author session
 */
public class NQueens {
	int n;
	int[] queens;
	HashSet rows;
	Random rand;
	int[] posDiags;
	int[] negDiags;
	
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		NQueens nq = new NQueens(n);
		nq.solve();
	}
	
	private void solve() {
		int k = initialSearch();
		finalSearch(k);
		
		for(int i=0; i < n; i++) {
			System.out.print("(" + i + "," + queens[i] + ") ");
		}
		System.out.println("");
	}
	
	private NQueens(int n) {
		this.n = n;	
		queens = new int[n];
		rows = new HashSet();
		rand = new Random();
		posDiags = new int[2*n - 1];
		negDiags = new int[2*n - 1];
		
		for(int i=0; i < 2*n-1; i++) {
			posDiags[i] = 0;
			negDiags[i] = 0;
		}
	}
	
	private int initialSearch() {
		int i = 0;
		int j = 0;
		int m = 0;
		
		// Place the queens on the diagonal
		for(i=0; i < n; i++) {
			queens[i] = i;
		}
		
		// Now place the queens smartly a certain # of times, avoiding all collisions
		i = 0;
		while(i < (3.08 * n)) {
			Integer mInt;
			do {
				m = rand.nextInt(n - j) + j;
				mInt = new Integer(m);
				System.out.print("#");
				i++;
			} while (rows.contains(mInt) && i < (3.08 * n));
			System.out.println("woo");
			
			swap(j, m);
			if (collisions(j) == 0) {
				rows.add(new Integer(m));
				j++;
				System.out.println("initial: placed queen in col " + j + ", row " + m);
			} else {
				swap(j, m);
			}
		}
		
		// Randomy place the rest
		for (i = j; i < n; i++) {
			Integer mInt;
			do {
				m = rand.nextInt(n - j) + j;
				mInt = new Integer(m);
				System.out.print("%");
			} while (rows.contains(mInt) && rows.size() <= n);
			System.out.println("unf");
			swap(j, m);
			System.out.println("initial: placed queen in col " + j + ", row " + m);
			rows.add(new Integer(m));
		}
		
		return n - j;
	}
	
	private void finalSearch(int k) {
		int i = 0;
		int j = 0;
		boolean hasCollisions;
		
		for(i = n-k; i < n; i++) {
			if (collisions(i) > 0) {
				do {
					j = rand.nextInt(n);
					swap(i, j);
					hasCollisions = (collisions(i) > 0 || collisions(j) > 0);
					if (hasCollisions) {
						swap(i, j); 
					}
				} while (hasCollisions);
			}
		}
	}
	
	private int collisions(int col) {
		int pos = queens[col] - col + n-1;
		int neg = queens[col] + col;
		int collides = posDiags[pos] - 1 + negDiags[neg] - 1;
		
		System.out.println("collides(pos=" + pos + ", neg=" + neg + ": " + collides);
		
		if (collides > 0) {
			return collides;
		} else {
			return 0;
		} 
	}
	
	private void swap(int i, int j) {
		int tmp = queens[i];
		queens[i] = queens[j];
		queens[j] = tmp;
	}
}
