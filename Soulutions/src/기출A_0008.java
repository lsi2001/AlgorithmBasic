import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


// backtracking, 백트렉킹
// [기출A-0008] 미세먼지 속의 소풍
public class 기출A_0008 {
	//public class Solution {

	static int N, M, S, E, F; // 노드 수, 간선 수, 출발, 도착, 최소 농도 값

	// backtracking
	static List<Integer>[] adjList;
	static boolean visited[];

	// 노드별 미세먼지
	static int[] dust;
	static int answer;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); 
			M = Integer.parseInt(st.nextToken());
			S = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			F = Integer.parseInt(st.nextToken());

			visited = new boolean[N + 1];
			dust = new int[N + 1]; // 노드별 미세먼지
			adjList = new ArrayList[N + 1];

			st = new StringTokenizer(br.readLine());
			for(int i = 1; i <= N; i++) {
				dust[i] = Integer.parseInt(st.nextToken()); 
				adjList[i] = new ArrayList<Integer>();
			}

			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				String from = st.nextToken();
				String to = st.nextToken();
				adjList[Integer.parseInt(from)].add(new Integer(to));
				adjList[Integer.parseInt(to)].add(new Integer(from));
			}

			answer = Integer.MAX_VALUE;
			back(S, dust[S]);

			if(answer == Integer.MAX_VALUE) answer = -1;

			System.out.println("#" + tc + " " + answer);

		}
		br.close();
	}


	private static void back(int current, int sum) { // 먼지의 누적을 파라미터로 전달
		if(current == E) {
			answer = Math.min(answer, sum);
			return;
		}
		for(Integer next : adjList[current]) {
			if(!visited[next.intValue()] && dust[next.intValue()] < F) {
				visited[next.intValue()] = true;
				back(next.intValue(), sum + dust[next.intValue()]);
				visited[next.intValue()] = false;
			}
		}
	}


}
