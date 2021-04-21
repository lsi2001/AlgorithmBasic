import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

// DFS, 깊이 우선 탐색
// [연습A-0030] 겨울난방 
// 필요한 온도만큼 문제가 주어짐, 특정 가구의 온도를 올리면 인접한 가구의 온도만 같이 올라감. 최소 난방 시간을 출력하는 문제
// 자식노드들의 온도를 만족시킨다. 현재노드는 부모노드에서 처리한다.
public class 연습A_0030 {
    //public class Solution {

    static int N, M;

    // DFS
    static boolean[] visited;
    static List<Integer>[] adjList;

    static int[] w;  // 올려야 하는 온도
    static int answer;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for(int tc = 1; tc <= T; tc++) {
            answer = 0;

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            visited = new boolean[N + 1];

            st = new StringTokenizer(br.readLine());
            w = new int[N + 1];
            adjList = new ArrayList[N + 1];

            for(int i = 1; i <= N; i++) {
                w[i] = Integer.parseInt(st.nextToken());
                adjList[i] = new ArrayList<Integer>();
            }

            for(int i = 1; i <= M; i++) { // 방향없음
                st = new StringTokenizer(br.readLine());

                String from = st.nextToken();
                String to = st.nextToken();
                adjList[Integer.parseInt(from)].add(new Integer(to));
                adjList[Integer.parseInt(to)].add(new Integer(to));
            }

            dfs(1, 0);

            if(w[1] > 0) answer += w[1]; // 마지막 노드 (DFS 시작)에서 온도가 부족한 경우 확인
            System.out.println("#" + tc + " " + answer);
        }
        br.close();
    }

    private static int dfs(int current, int parentIndex) {
        visited[current] = true;

        if(adjList[current].isEmpty()) return w[current];

        int maxReqValue = 0; // 자식 노드를의 값 중 최대값을 구한다. (현재 노드는 부모노드에서 처리)

        for(Integer next : adjList[current]) {
            if(!visited[next.intValue()]) {
                int nextReqValue = dfs(next.intValue(), current);
                maxReqValue = Math.max(maxReqValue, nextReqValue);
            }
        }

        // 온도를 내리고 시간을 누적 함 (자식노드의 경우는 다시 사용을 하지 않으므로 방문 체크만 하고 w 값은 변경 없어도 됨)
        w[parentIndex] -= maxReqValue;
        w[current] -= maxReqValue;
        answer += maxReqValue;

        // 0보다 작으면 의미 없음
        if(w[current] > 0) return w[current];
        else return 0;
    }
}