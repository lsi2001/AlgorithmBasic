import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 다익스트라(Dijkstra) (한 지점에서 각각의 최단거리 구하기, 간선의 비용은 양수)
// [연습A-0011] Cow Party
// 각 농장에서 X 농장을 최단거리로 갔다가 다시 돌아오는 값을 구하는 문제
//
// 갈때와 올때의 두 값을 합쳐 최대 인 값을 구하여 출력
// X로 갈때는 방향을 바꾼 인접리스트를 만들어 X 에서 출발하여 각 농장으로의 최소값을 구하는 방법으로 값을 구함 (모든 시작 지점에 대해서 각각의 최단거리 중 도착지점이 X의 값을 찾지 않음)
// X에서 돌아올때는 X에서 각 농장으로의 최소값 을 구함 (방향을 바꾸지 않은 인접리스트 그대로 사용)
public class 연습A_0011 {
	//public class Solution {

	static int N, M, X;
	static final int inf = 10000000;
	static ArrayList<Edge>[] go_adjList; // X 로 가는 최단거리를 구하는 인접 리스트
	static ArrayList<Edge>[] back_adjList; // X 에서 돌아오는 최단 거리를 구하는 인접 리스트
	static int[] dest, back; // X로 가는 최단거리, X에서 각 농장으로 돌아오는 최단 거리

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 농장의 수
			M = Integer.parseInt(st.nextToken()); // 도로의 수
			X = Integer.parseInt(st.nextToken()); // 파티가 열리는 농장 (도착지)

			go_adjList = new ArrayList[N + 1];
			back_adjList = new ArrayList[N + 1];
			for(int i = 1; i <= N; i++) {
				go_adjList[i] = new ArrayList<Edge>();
				back_adjList[i] = new ArrayList<Edge>();
			}

			// 인접 리스트
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());

				int from = Integer.parseInt(st.nextToken()); // 시작
				int to = Integer.parseInt(st.nextToken()); // 종료
				int time = Integer.parseInt(st.nextToken()); // 시간

				go_adjList[to].add(new Edge(from, time)); // 각 농장 -> X : 역방향으로 인접리스트를 생성하여 X에서 각 노드의 최단 거리를 구하는 방법 해도 동일한 최단거리 값이 구해짐
				back_adjList[from].add(new Edge(to, time)); // X 에서 농장으로 되돌아 갈때
			}

			// dijkstra
			Edge start = new Edge(X, 0); //X로 부터 각 노드의 최소값 구하기

			dest = dijkstra(go_adjList, start); // 각 농장에서 X 까지 가는 최단 거리
			back = dijkstra(back_adjList, start); // X 에서 각 농장까지 가는 최단 거리

			int answer = 0;
			for(int i = 1; i <= N; i++) {
				if(answer < dest[i] + back[i]) answer = dest[i] + back[i];
			}

			System.out.println("#" + tc + " " + answer);

		}
		br.close();
	}

	// dijkstra
	static int[] dijkstra(ArrayList<Edge>[] adjList, Edge start) {
		// 각 지점의 최단 거리값을 저장할 배열 생성
		int[] result = new int[N + 1];
		Arrays.fill(result, inf);

		// PriorityQueue 사용
		PriorityQueue<Edge> queue= new PriorityQueue<Edge>();

		// Queue에 시작지점 삽입
		queue.add(start);
		result[start.dest] = 0;

		while(!queue.isEmpty()) {
			Edge current = queue.poll(); // Queue에서 하나씩 꺼내서 확인 시작

			for(Edge next : adjList[current.dest]) { // 인접 리스트에서 다음 노드를 위한 Edge정보를 하나씩 꺼냄
				if(result[next.dest] > result[current.dest] + next.time) { // 현재까지 걸린 시간 + 걸릴 시간이 저장된 시간보다 작으면 최소값 갱신을 해야한다.
					result[next.dest] = result[current.dest] + next.time; // 값을 변경하고 다음으로 이동할 간선 정보를 Queue에 넣는다.
					queue.add(next);
				}
			}
		}
		return result;
	}

	static class Edge implements Comparable<Edge> {
		int dest; // 다음 이동 농장
		int time; // 걸리는 시간

		public Edge(int dest, int time) {
			super();
			this.dest = dest;
			this.time = time;
		}

		@Override
		public int compareTo(Edge o) {
			if(this.time > o.time) {
				return 1;
			} else if (this.time < o.time) {
				return -1;
			}
			return 0;
		}
	}
}
