import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// [����A-0011] Cow Party
// ���ͽ�Ʈ��(Dijkstra) (�� �������� ������ �ִܰŸ� ���ϱ�, ������ ����� ���)
// �� ���忡�� X ������ �ִܰŸ��� ���ٰ� �ٽ� ���ƿ��� ���� ���ϴ� ����
//
// ������ �ö��� �� ���� ���� �ִ� �� ���� ���Ͽ� ���
// X�� ������ ������ �ٲ� ��������Ʈ�� ����� X ���� ����Ͽ� �� ���������� �ּҰ��� ���ϴ� ������� ���� ���� (��� ���� ������ ���ؼ� ������ �ִܰŸ� �� ���������� X�� ���� ã�� ����)
// X���� ���ƿö��� X���� �� ���������� �ּҰ� �� ���� (������ �ٲ��� ���� ��������Ʈ �״�� ���)
public class ����A_0011 {
	//public class Solution {

	static int N, M, X;
	static final int inf = 10000000;
	static ArrayList<Edge>[] go_adjList; // X �� ���� �ִܰŸ��� ���ϴ� ���� ����Ʈ
	static ArrayList<Edge>[] back_adjList; // X ���� ���ƿ��� �ִ� �Ÿ��� ���ϴ� ���� ����Ʈ
	static int[] dest, back; // X�� ���� �ִܰŸ�, X���� �� �������� ���ƿ��� �ִ� �Ÿ�

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;

		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // ������ ��
			M = Integer.parseInt(st.nextToken()); // ������ ��
			X = Integer.parseInt(st.nextToken()); // ��Ƽ�� ������ ���� (������)

			go_adjList = new ArrayList[N + 1];
			back_adjList = new ArrayList[N + 1];
			for(int i = 1; i <= N; i++) {
				go_adjList[i] = new ArrayList<Edge>();
				back_adjList[i] = new ArrayList<Edge>();
			}

			// ���� ����Ʈ
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());

				int from = Integer.parseInt(st.nextToken()); // ����
				int to = Integer.parseInt(st.nextToken()); // ����
				int time = Integer.parseInt(st.nextToken()); // �ð�

				go_adjList[to].add(new Edge(from, time)); // �� ���� -> X : ���������� ��������Ʈ�� �����Ͽ� X���� �� ����� �ִ� �Ÿ��� ���ϴ� ��� �ص� ������ �ִܰŸ� ���� ������
				back_adjList[from].add(new Edge(to, time)); // X ���� �������� �ǵ��� ����
			}

			// dijkstra
			Edge start = new Edge(X, 0); //X�� ���� �� ����� �ּҰ� ���ϱ�

			dest = dijkstra(go_adjList, start); // �� ���忡�� X ���� ���� �ִ� �Ÿ�
			back = dijkstra(back_adjList, start); // X ���� �� ������� ���� �ִ� �Ÿ�

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
		// �� ������ �ִ� �Ÿ����� ������ �迭 ����
		int[] result = new int[N + 1];
		Arrays.fill(result, inf);

		// PriorityQueue ���
		PriorityQueue<Edge> queue= new PriorityQueue<Edge>();

		// Queue�� �������� ����
		queue.add(start);
		result[start.dest] = 0;

		while(!queue.isEmpty()) {
			Edge current = queue.poll(); // Queue���� �ϳ��� ������ Ȯ�� ����

			for(Edge next : adjList[current.dest]) { // ���� ����Ʈ���� ���� ��带 ���� Edge������ �ϳ��� ����
				if(result[next.dest] > result[current.dest] + next.time) { // ������� �ɸ� �ð� + �ɸ� �ð��� ����� �ð����� ������ �ּҰ� ������ �ؾ��Ѵ�.
					result[next.dest] = result[current.dest] + next.time; // ���� �����ϰ� �������� �̵��� ���� ������ Queue�� �ִ´�.
					queue.add(next);
				}
			}
		}
		return result;
	}

	static class Edge implements Comparable<Edge> {
		int dest; // ���� �̵� ����
		int time; // �ɸ��� �ð�

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
