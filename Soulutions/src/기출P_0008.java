import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
// 서로소 집합(Disjoint Set, Union-Find)
// [기출P-0008] 연방
// 도시의 교류를 차단하고 서로 교류를 직접 또는 간접으로 하는 도시인지 물을때는 그 결과르 생성하여 출력
// 모든 교류를 차단하는 처리를 미리 처리하기 위해 Union Find 를 위한 배열생성 시 간선은 무시하고 거꾸로 질문 또는 처리를 확인하며 답을 생성 한다. 교류 제거 처리가 먼저 이루어졌으므로 거꾸로 진행할 때는 교류 정보를 추가한다.
public class 기출P_0008 {
	//public class Solution { 
	static int[] U;  // Union Find
	static int [][] conn; // 교류 도시의 간선 정보를 모두 저장해 놓음
	static int[][] q;  // 교류 제거 처리 또는 연합 여부 질문 정보를 모두 저장해놓음
	static boolean[] cutOff; // 교류를 제거하는 처리가 몇번째 간선 정보인지 저장해 놓음 -> 간선 정보를 이용해 U[]의 값을 입력할 때 제외 처리

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++ ) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 나라의 수
			int M = Integer.parseInt(st.nextToken()); // 간선 정보의 수

			U = new int[N+1]; // Union Find 사용을 위한 배열
			conn = new int[M+1][2]; // 연결되는 두 나라 번호를 저장
			cutOff = new boolean[M+1]; // 교류가 끊어지는 처리를 해야하는 간선의 인덱스를 저장
			for(int i = 1; i <= N; i++) {
				U[i] = i;
			}
			for(int i = 1; i <= M; i++) {  // 질문이 이후에 나오므로 연결 정보를 미리 배열에 저장해 둔다.
				st = new StringTokenizer(br.readLine());
				int nation1 = Integer.parseInt(st.nextToken());
				int nation2 = Integer.parseInt(st.nextToken());
				conn[i][0] = nation1;
				conn[i][1] = nation2;
			}

			int Q = Integer.parseInt(br.readLine()); // 처리 + 질문의 수
			q = new int[Q][3]; // 답을 구하기 위하여 q 에 처리 또는 질문을 저장해 둔다.
			for(int i = Q - 1; i >= 0 ; i--) { // 배열을 거꾸로 채워넣고 이후 출력도 거꾸로 생성 한다.
				st = new StringTokenizer(br.readLine());
				if(1 == Integer.parseInt(st.nextToken())) { // 간선의 교류 정보를 제거하는 처리의 경우  
					int k = Integer.parseInt(st.nextToken()); 
					cutOff[k] = true; // 입력된 간선 정보중 몇번째 교류 정보를 제거할지 체크
					q[i][0] = 1;
					q[i][1] = k;
				} else { // 교류 상태인지 문의하는 경우
					q[i][0] = 2;
					q[i][1] = Integer.parseInt(st.nextToken());
					q[i][2] = Integer.parseInt(st.nextToken());
				}
			}

			for(int i = 1; i <= M; i++) { // 위에서 입력한 연결 정보로 Union Find 수행을 위한 배열을 생성한다.
				if(!cutOff[i]) U[conn[i][1]] = conn[i][0]; // 교류 정보를 제거하는 경우의 인덱스에 해당하는 정보는 처리하지 않는다.
			}

			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < Q; i++) {
				if(1 == q[i][0]) { // 교류 정보 제거 처리가 모두 된 상태이므로 거꾸로 거슬러 올라가면서 계산 시 교류 하도록 적용한다.(Union)
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
