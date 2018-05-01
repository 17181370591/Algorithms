<h3>思路：x=dfa[s[i]][j]表示s[i]和pat[j]比较后，用pat[x]和s[i+1]比较，即dfa用来存储索引。dfa的本质是，第i行第j列的值表示pat[:j]+R[i]这个字符串的最长公共前后缀（下面简称zcz），第j列的初始值都是这个，除了R[i]=pat[j]时的值设为j+1（下面简称特殊值）。这样可以保证dfa的正确性。</h3>
<h2>dfa的构造：</h2>
<h3>（1）第一列都是0，除了特殊值是1。然后可以证明：令X=pat[:i+1]的zcz，那么第i+1列的值等于第X列的值，然后修改特殊值即可。对于X列的任意字符a，值为A，如果a是特殊值，说明pat[X]=a且A=X+1，那么i+1列的a的值等于X+1，等于A。如果a不是特殊值，那么A等于pat[a]的zcz，且pat[A]!=a，然后容易证明i+1列的a的值等于A，证明完毕。</h3>
<h3>（2）现在只需证明如果Y=pat[:j]的zcz，那么X=dfa[pat.charAt(j)][Y]等于pat[:j+1]的zc（相当于用归纳法证明X = dfa[pat.charAt(j)][X]）。令pat[j]=p，如果X是特殊值，则pat[Y]=p且X=dfa[p][Y]=Y+1，由于pat[j]=pat[Y]，所以pat[:j]的zcz等于Y+1，得证；如果X不是特殊值，说明pat[Y]！=p且X=dfa[p][Y]表示pat[:Y]+p的zcz，所以Z=dfa[p][Y]=X，证明完毕</h3>

package com.jimmysun.algorithms.chapter5_3;

import edu.princeton.cs.algs4.StdOut;

public class KMP {
	private String pat;
	private int[][] dfa;

	public KMP(String pat) {
		this.pat = pat;
		int M = pat.length();
		int R = 256;
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		for (int X = 0, j = 1; j < M; j++) {
			for (int c = 0; c < R; c++) {
				dfa[c][j] = dfa[c][X];
			}
			dfa[pat.charAt(j)][j] = j + 1;
			X = dfa[pat.charAt(j)][X];
		}
	}

	public int search(String txt) {
		int i, j, N = txt.length(), M = pat.length();
		for (i = 0, j = 0; i < N && j < M; i++) {
			j = dfa[txt.charAt(i)][j];
		}
		if (j == M) {
			return i - M;
		} else {
			return N;
		}
	}

	public static void main(String[] args) {
		String pat = args[0];
		String txt = args[1];
		KMP kmp = new KMP(pat);
		StdOut.println("text:    " + txt);
		int offset = kmp.search(txt);
		StdOut.print("pattern: ");
		for (int i = 0; i < offset; i++) {
			StdOut.print(" ");
		}
		StdOut.println(pat);
	}
}
