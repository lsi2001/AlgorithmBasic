import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 인덱스 트리, indexed tree
// [연습P-0019] 구간합
// 주어진 수열을 변경하면서 구간합을 구하고 그 구간 합들의 합을 출력한다.
public class 연습P_0019 {

	static int N, Q; // 수열의 길이, 질의의 갯수
	static long[] tree; // 생성할 트리
	static int leafCount, pointer; // 트리의 리프 갯수, 가장 마지막에 위치한 내부노드(자식이 있는 노드)의 마지막 인덱스

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;

		for(int tc = 1; tc <= T; tc++) {
			int answer = 0;

			N = Integer.parseInt(br.readLine());
			Q = Integer.parseInt(br.readLine());

			leafCount = 1;
			pointer = 0;
			long total = 0L;

			initTree(); // tree[] 를 생성
			int a, x, y;
			for(int i = 0; i < Q; i++) {
				st = new StringTokenizer(br.readLine());
				a = Integer.parseInt(st.nextToken());  // 질의 형태
				x = Integer.parseInt(st.nextToken());
				y = Integer.parseInt(st.nextToken());

				if(a == 0) { // 0 이면 x를 y로 변경
					updateTree(x, y);
				} else { // 0 이 아니면(1이면) 구간 합구하기
					total += sum(x, y);
				}
			}
			answer = (int)(total % 1000000007); //질의에서 구한 구간 합의 합을 출력
			System.out.println("#" + tc + " " + answer);
		}

	}

	// 인덱스 트리를 나타낼 배열 생성
	private static void initTree() {
		while (N > leafCount) {  // 리프노드의 갯수를 결정 한다. 트리구성을 해야하므로 입력된 N보다 큰 2제곱 수
			leafCount <<= 1;
		}

		tree = new long[leafCount * 2]; // 리프노드의 갯수의 두배로 트리를 생성
		pointer = leafCount - 1; // 마지막 내부 노드의 인덱스 값

		// 트리를 체우기
		for(int i = 1; i <= N; i++) { // 배열의 [0]은 사용 안함
			tree[pointer + i] = i;  // 문제에서 제공된 값으로 리프노드에 해당하는 배열값 채움
		}
		for(int i = pointer; i > 0; i--) {
			// 자식 노드인 리프노드의 왼쪽 오른쪽 내부 노드를 뒤에서 부터 채움
			tree[i] = tree[i * 2] + tree[(i * 2) + 1]; // 왼쪽 자식노드 값 + 오른쪽 자식노드 값
		}
	}

	// 수열의 index 값을 value로 변경하여 트리 배열을 update
	private static void updateTree(int index, int value) {
		int i = pointer + index;
		tree[i] = value;

		int parentIndex = i/2; // 부모노드의 인덱스

		while(parentIndex > 0) { // 부모노드의 값을 순차적으로 구하면서 최상단 까지 업데이트 한다
			tree[parentIndex] = tree[parentIndex * 2] + tree[(parentIndex * 2) + 1];
			parentIndex /= 2;
		}
	}

	// 수열에서 두개의 인덱스 구간의 합을 구한다.
	private static long sum(int start, int end) {
		start += pointer;
		end += pointer;
		long sum = 0L;

		while(start <= end) {
			// 시작 인덱스가 오른쪽 노드자식에 해당하면 그 합을 계산하고 인덱스를 오른쪽으로 이동
			if(start % 2 == 1) {
				sum += tree[start];
				start++;
			}
			// 종료 인덱스가 오른쪽 노드자식에 해당하면 그 합을 계산하고 인덱스를 왼쪽으로 이동
			if(end % 2 == 0) {
				sum += tree[end];
				end--;
			}

			// 시작이 왼쪽, 종료가 오른쪽이면 부모 노드로 위치 이동
			// 부모노드로 올라갈수록 해당 노드는 오른쪽 자식노드, 왼쪽 자식 노드가 반복되므로 두 인덱스가 맞닿을때 까지 반복하면 합이 구해짐
			start /= 2;
			end /= 2;
		}
		return sum;
	}

}