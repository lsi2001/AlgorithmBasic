import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
// ���μ� ����(Disjoint Set, Union-Find)
// [����P-0008] ����
// ������ �ϴ� �������� �ƴ��� �Էµ� ó�� �Ǵ� ������ ���� ���� �����Ͽ� ���
// ��� ������ �����ϴ� ������ ó���Ͽ� �Ųٷ� �������θ� üũ�ϸ鼭 ���ܵ� ������ �ٽ� �����ϴ� ������� �Ųٷ� üũ�ϸ鼭 ����� ������� �����Ѵ�.
public class ����P_0008 {
	//public class Solution { 
	static int[] U;  // Union Find
	static int [][] conn; // ���� ������ ���� ������ ��� ������ ����
	static int[][] q;  // ���� ���� ó�� �Ǵ� ���� ���� ���� ������ ��� �����س���
	static boolean[] cutOff; // ������ �����ϴ� ó���� ���° ���� �������� ������ ���� -> ���� ������ �̿��� U[]�� ���� �Է��� �� ���� ó��

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++ ) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // ������ ��
			int M = Integer.parseInt(st.nextToken()); // ���� ������ ��

			U = new int[N+1]; // Union Find ����� ���� �迭
			conn = new int[M+1][2]; // ����Ǵ� �� ���� ��ȣ�� ����
			cutOff = new boolean[M+1]; // ������ �������� ó���� �ؾ��ϴ� ������ �ε����� ����
			for(int i = 1; i <= N; i++) {
				U[i] = i;
			}
			for(int i = 1; i <= M; i++) {  // ������ ���Ŀ� �����Ƿ� ���� ������ �̸� �迭�� ������ �д�.
				st = new StringTokenizer(br.readLine());
				int nation1 = Integer.parseInt(st.nextToken());
				int nation2 = Integer.parseInt(st.nextToken());
				conn[i][0] = nation1;
				conn[i][1] = nation2;
			}

			int Q = Integer.parseInt(br.readLine()); // ó�� + ������ ��
			q = new int[Q][3]; // ���� ���ϱ� ���Ͽ� q �� ó�� �Ǵ� ������ ������ �д�.
			for(int i = Q - 1; i >= 0 ; i--) { // �迭�� �Ųٷ� ä���ְ� ���� ��µ� �Ųٷ� ���� �Ѵ�.
				st = new StringTokenizer(br.readLine());
				if(1 == Integer.parseInt(st.nextToken())) { // ������ ���� ������ �����ϴ� ó���� ���  
					int k = Integer.parseInt(st.nextToken()); // �Էµ� ���� ������ ���° ���� ������ �������� üũ
					cutOff[k] = true;
					q[i][0] = 1;
					q[i][1] = k;
				} else { // ���� �������� �����ϴ� ���
					q[i][0] = 2;
					q[i][1] = Integer.parseInt(st.nextToken());
					q[i][2] = Integer.parseInt(st.nextToken());
				}
			}

			for(int i = 1; i <= M; i++) { // ������ �Է��� ���� ������ Union Find ������ ���� �迭�� �����Ѵ�.
				if(!cutOff[i]) U[conn[i][1]] = conn[i][0]; // ���� ������ �����ϴ� ����� �ε����� �ش��ϴ� ������ ó������ �ʴ´�.
			}

			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < Q; i++) {
				if(1 == q[i][0]) { // ���� ���� ���� ó���� ��� �� �����̹Ƿ� �Ųٷ� �Ž��� �ö󰡸鼭 ��� �� ���� �ϵ��� �����Ѵ�.(Union)
					// Union
					merge(conn[q[i][1]][0], conn[q[i][1]][1]);
				} else {
					// Find
					if(find(q[i][1]) == find(q[i][2])) sb.append("1");
					else sb.append("0");
				}
			}
			bw.write("#" + tc + " " + sb.reverse().toString() + "\n");
		}

		bw.flush();
		bw.close();
		br.close();
	}

	// Find
	private static int find(int a) {
		if(U[a] == a) return a;
		else return U[a] = find(U[a]);
	}

	// Union
	private static void merge(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);

		U[aRoot] = bRoot;
	}
}