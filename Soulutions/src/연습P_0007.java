import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// MST(최소 신장 트리, 크루스칼(Kruskal)/유니온-파인드(Union-Find))
// [연습P-0007] 고속도로 건설
// 도시와 도로 후보의 목록이 주어질 때, 최소비용으로 모든 도시간을 이동할 수 있는 고속도로를 잇는 건설. 도시 간을 잇는 고속도로를 건설하는 최소 비용을 알아내어 출력
public class 연습P_0007 {
	//public class Solution {

	static int N, M;

	// 크루스칼(Kruskal)/유니온-파인드(Union-Find)
	static int[] parent;
	static PriorityQueue<Edge> queue;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine()); // 도시의 수
			M = Integer.parseInt(br.readLine()); // 도로 후보의 수

			int answer = 0;

			// 크루스칼(Kruskal)/유니온-파인드(Union-Find) 사용 - 두 트리를 연결하는 간선 중 가장 작은 값을 찾아 MST의 부분 집합에 추가 하는 방법
			parent = new int[N + 1];
			for(int i = 1; i <= N; i++) {
				parent[i] = i;
			}
			//Edge[] edgeArray = new Edge[M]; // sort 필요
			queue = new PriorityQueue<Edge>(); // 가장 적은 비용이 드는 도로정보 확인

			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken()); // s,e 도로 후보가 있는 도시의 번호
				int e = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken()); // 도로를 건설하는데 드는 비용

				//edgeArray[i] = new Edge(s, e, c);
				queue.add(new Edge(s, e, c)); // 우선 순위 큐에 삽입, 가중치 크기로 정렬하므로 방향 무시

			}

			/*Arrays.sort(edgeArray);
            int count = 0;
            for(int i = 0; i < M; i++) {
                Edge e = edgeArray[i];

                if(find(e.start) != find(e.end)) { // 연결이 안된 도시면 연결하고 출력할 비용의 합을 계산한다.
                  merge(e.start, e.end);
                  answer += e.cost;
                  if(count == (N - 1)) break;
              }
            }*/


			int count = 0;
			while(count < N && !queue.isEmpty()) { // N 개의 노드에 N-1개 간선
				Edge e = queue.poll(); // 큐에서 하나씩 꺼낸다.

				if(find(e.start) != find(e.end)) { // 연결이 안된 도시면 연결하고 출력할 비용의 합을 계산한다.
					merge(e.start, e.end);
					answer += e.cost;
					count++;
				}
			}

			System.out.println("#" + tc + " " + answer);
		}

		br.close();
	}

	// 간선(도로 후보)정보
	static class Edge implements Comparable<Edge> {
		int start, end, cost; // 도시 시작, 끝, 비용

		public Edge(int start, int end, int cost) {
			super();
			this.start = start;
			this.end = end;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			if(this.cost > o.cost) return 1;
			else if(this.cost < o.cost) return -1;
			else return 0;
		}
	}

	// union
	private static void merge(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);

		parent[aRoot] = bRoot;
	}

	// find
	private static int find(int a) {
		if(parent[a] == a) return a;
		else return parent[a] = find(parent[a]);
	}
}