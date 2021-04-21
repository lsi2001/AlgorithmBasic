import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// BFS, 간선의 가중치가 같을 때 최단 경로, 격자이동
// [기출P-0063] Pac Man 
// pac man 과 ghost가 모두 최단 거리로 목적지 까지 이동
// pac man 과 ghost가 중간 어딘가에서 만나거나 최종 목적지에서 만나는 경우 -> 최단 시간이 같음
// pac man 이 먼저 도착하는 경우
// ghost 가 먼저 도착하는 경우 -> ghost 가 계속 움직이므로 차이를 확인하여 안만나는 경우 체크
public class 기출P_0063 {
	//public class Solution {

	static int N, M; // row, column

	// bfs    
	static int[][] map;
	static boolean[][] visited;
	static int[][] direction = {{-1, 0},{1, 0},{0, -1},{0, 1}}; // 4방향 격자 이동
	static Queue<Node> queue = new ArrayDeque<Node>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());

			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N + 1][M + 1];
			visited = new boolean[N + 1][M + 1];

			Node startNode = null, endNode = null;
			List<Node> ghostList = new ArrayList<Node>();

			for(int i = 1; i <= N; i++) {
				String path = br.readLine();
				for(int j = 1; j <= M; j++) {
					char c = path.charAt(j - 1);
					map[i][j] = 1;
					switch(c) {
					case '#':
						map[i][j] = 0;
						break;
					case 'S': 
						startNode = new Node(i, j);
						break;
					case 'E':
						endNode = new Node(i, j);
						break;
					case 'C':
						ghostList.add(new Node(i, j));
						break;
					}
				}
			}

			int answer = bfs(startNode, endNode); // pac man 의 최단 경로 길이 
			// ghost 의 최단 경로 확인
			if (answer > -1) {
				int temp;
				for(Node ghost : ghostList) {
					visited = new boolean[N + 1][M + 1];
					temp = bfs(ghost, endNode);
					if(temp == -1) continue;
					if(temp == answer) answer = -1; // 최단거리가 같으면 경로 어디선가 또는 최종 목적지에서 만난다.
					// ghost 가 먼저 도착해도 계속 움직여야 하므로 이동거리 차이가 짝수이면 만날 수 있다.
					else if((temp < answer) && ((answer - temp) % 2 == 0)) {
						answer = -1;
					}
				}
			}
			System.out.println("#" + tc + " " + answer);
		}
		br.close();
	}

	private static int bfs(Node startNode, Node endNode) {
		int[][] distance = new int[N+1][M+1];
		distance[startNode.x][startNode.y] = 0;
		queue.clear();
		queue.add(startNode);
		visited[startNode.x][startNode.y] = true;

		Node currentNode;
		int nextX, nextY;
		while(!queue.isEmpty()) {
			currentNode = queue.poll();
			if(currentNode.x != endNode.x || currentNode.y != endNode.y) {
				for(int d = 0; d < direction.length; d++) {
					nextX = currentNode.x + direction[d][0];
					nextY = currentNode.y + direction[d][1];

					if(0 < nextX && nextX <= N && 0 < nextY && nextY <= M // 격자의 범위 내
							&& map[nextX][nextY] > 0 // 갈수 있는 노드
							&& !visited[nextX][nextY] // 미방문
							) {
						queue.add(new Node(nextX, nextY));
						visited[nextX][nextY] = true; // BFS의 경우 이동경로가 발견 되면 방문 체크
						distance[nextX][nextY] = distance[currentNode.x][currentNode.y] + 1; // 다음 노드로 이동하면 이전값을 알 수 없기 때문에 queue 추가 후 이동 거리가 될 값을 미리 저장함. 
					}
				}
			} else {
				// 종점에 동착했을 경우 최단 경로 반환
				return distance[endNode.x][endNode.y];
			}
		}
		// 도착을 못하면 -1
		return -1;
	}

	// 격자 위치
	static class Node {
		int x;
		int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}