

import java.util.Arrays;

public class Sort {

	public static void main(String[] args) {

		int array[] = {32,4,21,1,75,50,85,19,0,6,100,10};  
		System.out.println( "input : " + System.lineSeparator() +  Arrays.toString(array) + System.lineSeparator());

//		selectionSort(array);
//		bubbleSort(array);
//		insertionSort(array);
//		quickSort(array,0,array.length-1);
		mergeSort(array,0,array.length-1);

	}

	/**
	 * 선택 정렬
	 * 전체 수중 가장 작은 수를 찾아 앞에서 부터 위치 시킴
	 */
	public static void selectionSort(int[] array) {
		int minIndex = 0;
		int temp = 0;

		// i번째를 기준으로 i+1 부터 마지막까지 중 가장 작은수를 찾아 i와 위치를 교환 한다
		// i를 1씩 올리면서 반복, 왼쪽의 숫자는 가장 작은숫자 부터 정렬이 된다.
		for(int i = 0; i < array.length; i++) {
			minIndex = i;

			for(int j = i+1; j < array.length; j++) { 
				if(array[j] < array[minIndex]) {
					minIndex = j;
				}
			}
			temp = array[minIndex]; 
			array[minIndex] = array[i];
			array[i] = temp;

			// 출력 //
			System.out.println( Arrays.toString(array) );
			// 출력 끝 //
		}

	}

	/**
	 * 거품 정렬
	 * 왼족에서 오른쪽까지 이동하며 인접한 두 숫자의 크기 비교하며 위치교환
	 */
	public static void  bubbleSort(int[] array) {

		int size = array.length;
		int temp = 0;

		for(int i = 0; i < size - 1; i++) {
			for(int j = 1; j < size - i; j++) { // size - i 의 이후는 앞에서 부터 큰 숫자가 옮겨갔기 때문에 큰 숫자로 정렬되어있음

				if(array[j-1] > array[j]) {
					temp = array[j-1];
					array[j-1] = array[j];
					array[j] = temp;
				}
			}

			// 출력 //
			System.out.println( Arrays.toString(array) );
			// 출력 끝 //
		}
	}

	/**
	 * 삽입 정렬
	 * 선택한 숫자보다 큰 숫자면 오른쪽으로 이동시킴
	 */
	public static void  insertionSort(int[] array) {

		for(int i = 1; i < array.length; i++){  // 두번째 숫자부터 비교함

			int currentNum = array[i];

			int j = i - 1;

			// 비교할 숫자를 제외 한 왼쪽의 숫자들은 이미 정렬이 되어있음
			// 비교할 숫자보다 큰 숫자가 있을때만 오른쪽으로 밀어냄
			while (j >= 0 && array[j] > currentNum){
				array[j+1] = array[j--]; // j 의 값을 j+1 로 밀어냄
			}
			array[j+1] = currentNum; // 큰 숫자들만 있는 구역에서 나온 후 마지막에 비교한 숫자를 넣음

			// 출력 //
			System.out.println( Arrays.toString(array) );
			// 출력 끝 //

		}
	}


	/**
	 * 퀵 정렬
	 * 임의의 가운데 숫자를 기준으로 작은수는 왼쪽 큰수는 오른쪽의 경우가 아닐경우 위치 교환, 기준 양쪽에 대한 부분 배열은 재귀로 처리
	 */
	public static void quickSort(int[] array, int l, int r) { //재귀호출

		int left = l;
		int right = r;

		int pivot = array[(left + right) / 2]; // 임의의 중간값 선정
		int temp = 0;

		while (left <= right) {
			// 임의의 값을 기준으로 
			while (array[left] < pivot) left++; // 왼쪽끝에서 기준보다 작으면 오른쪽으로 이동 (기준보다 큰 수가 나올때 까지)
			while (array[right] > pivot) right--; // 오른쪽끝에서 기준보다 크면 왼쪽으로 이동 (기준보다 작은 수가 나올 때 까지)

			// 왼쪽에서 기준보다 큰 수가 찾아진 상태, 오른쪽에서 기준보다 작은 수가 찾아진 상태
			if(left <= right) { // 기준보다 작은 수가 오른쪽에 있고 기준보다 큰수가 왼쪽에 있다면 자리 교환
				temp = array[right];
				array[right] = array[left];
				array[left] = temp;

				// 자리 바꾸고 나서 왼쪽 오른쪽 이동
				left++;
				right--;
			}

		}

		// 출력 //
		System.out.println( Arrays.toString(array) );
		// 출력 끝 //

		// 중간값으로 오른쪽, 왼쪽 각각 재귀호출하면 동작함
		if(l < right) quickSort(array, l, right);
		if(r > left) quickSort(array, left, r);

	}

	/**
	 * 병합 정렬
	 * 전체를 분할 후 병합하며 정렬, 재귀함수 사용
	 */
	public static void mergeSort(int[] array, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			mergeSort(array, l, mid);
			mergeSort(array, mid + 1, r);
			
			merge(array, l, mid, r);
			
			// 출력 //
			System.out.println( Arrays.toString(array) );
			// 출력 끝 //
			
		}
	}
	
	
	/**
	 * 병합 정렬 - merge
	 * 정렬된 두 배열을 병합하여 하나의 배열로 만든다 ([l..mid], [mid+1..r] => [l..r])
	 */
	public static void merge(int[] array, int l, int mid, int r) {
		int i = l;
		int j = mid + 1;
		int k = l;
		
		int temp[] = new int[array.length];
		while(i<=mid && j<=r) {
			if(array[i] < array[j]) {
				temp[k++] = array[i++];  // 더 작은수를 temp 에 정렬시킴
			} else {
				temp[k++] = array[j++];
			}
		}
		
		while (i<=mid) {
			temp[k++] = array[i++];
		}
		
		while (j<=r) {
			temp[k++] = array[j++];
		}
		
		for(int index = l; index <= r; index++) {
			array[index] = temp[index];
		}
		
	}
	
	
	
	
	
	










}
